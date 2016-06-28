package com.cipto.doa;

import java.util.HashMap;
import java.util.List;


import com.cipto.doa.R.id;

import android.os.Bundle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import android.widget.SimpleAdapter;

import android.widget.Toast;


public class ActivityDisplaySurah extends Activity{
	DBAdapter dbAdapter;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_surah);
		Intent intent = getIntent();
		final String item=intent.getStringExtra("id");   
	    dbAdapter = new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
		String nama_surah=dbAdapter.getSurah(Integer.valueOf(item)).getString(1);
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle(nama_surah);
	 
			
	     
		lv = (ListView)findViewById(id.lvAyat);
			
			List<HashMap<String,String>> listAyat = dbAdapter.getAllAyat(item);
			
	 		String[] from = new String[] { "nomor_ayat","teks_arab","teks_indo" };
			int[] to = new int[] { R.id.tvNomor, R.id.tvArab, R.id.tvIndo};
			SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), listAyat, R.layout.list_row_ayat, from, to);
		
			lv.setAdapter(adapter);
			lv.setTextFilterEnabled(true);
			registerForContextMenu(lv);
			
			//lv.setOnItemClickListener(new ListClickHandler());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display_surah, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	  String id_surah= getIntent().getStringExtra("id");  
  	  switch (item.getItemId()) {
  	  	case R.id.prev:
  	  		prevPage(id_surah);
	      break;
  		case R.id.next:
  			nextPage(id_surah);
	      break;
  	    case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent listDoa = new Intent(ActivityDisplaySurah.this, ActivityQuran.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
          return true;
  	  }
  	  return true;
  	}
	
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
		if (v.getId()==R.id.lvAyat) {
		    MenuInflater inflater = getMenuInflater();
	       inflater.inflate(R.menu.menu_options, menu);
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int selectpos = info.position;
	  switch(item.getItemId()) {
      case R.id.addBookmark:
    	  addBookmark(String.valueOf(selectpos));
    	  Toast.makeText(this,"Ditambahkan ke bookmark",Toast.LENGTH_LONG).show();
         return true;
       case R.id.share:
         // edit stuff here
             return true;
    
       default:
             return super.onContextItemSelected(item);
	  }

	}
	
	public void addBookmark(String id){
		 ContentValues values = new ContentValues();
		 values.put("tipe","3");
 	     values.put("id_item",id);
 	     dbAdapter.insertBookmark(values);
 	     dbAdapter.close();
 	     Toast.makeText(ActivityDisplaySurah.this, "Doa telah ditambahkan ke bookmark", Toast.LENGTH_LONG).show();
	}
	
	public void nextPage(String id){
		int page=Integer.valueOf(id)+1;
		Intent intentNext;
		if(page==144){
			intentNext= new Intent(ActivityDisplaySurah.this, ActivityQuran.class);
		}else{
			intentNext = new Intent(ActivityDisplaySurah.this, ActivityDisplaySurah.class);
			intentNext.putExtra("id",String.valueOf(page));
	        
		//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
		}
		
		startActivity(intentNext);  
	}
	
	public void prevPage(String id){
		int page=Integer.valueOf(id)-1;
		Intent intentPrev;
		if(page==0){
			intentPrev= new Intent(ActivityDisplaySurah.this, ActivityQuran.class);
		}else{
			intentPrev = new Intent(ActivityDisplaySurah.this, ActivityDisplaySurah.class);
			intentPrev.putExtra("id",String.valueOf(page));
		}
		startActivity(intentPrev);  
	}

}

package com.cipto.doa;


import android.os.Bundle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ListView;

import android.widget.TextView;
import android.widget.Toast;


public class ActivityDisplaySunnah extends Activity{
	DBAdapter dbAdapter;
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_sunnah);
		Intent intent = getIntent();
		final String item=intent.getStringExtra("id");   
	    dbAdapter = new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
		Cursor sunnah=dbAdapter.getSunnah(Integer.valueOf(item));
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle(sunnah.getString(2));
	     
	     //baca textview
	   TextView judul = (TextView) findViewById(R.id.judul);
	   TextView arab = (TextView) findViewById(R.id.arab);
	   TextView indo = (TextView) findViewById(R.id.indo);
	   Typeface localTypeface1 = Typeface.createFromAsset(getAssets(), "fonts/me_quran.ttf");
	   arab.setTypeface(localTypeface1);
	   judul.setText(sunnah.getString(2));
	   arab.setText(sunnah.getString(3));
	   indo.setText(sunnah.getString(4));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	  String id_sunnah= getIntent().getStringExtra("id");  
      Cursor bookmark=dbAdapter.getBookmark(Integer.valueOf(id_sunnah));
  	  switch (item.getItemId()) {
  	  	case R.id.prev:
  	  		prevPage(id_sunnah);
	      break;
  		case R.id.next:
  			nextPage(id_sunnah);
	      break;
  		case R.id.bookmark:
  			if(bookmark.getCount() == 0){
  				addBookmark(id_sunnah);
  			}
  			 Toast.makeText(ActivityDisplaySunnah.this, "ditambahkan ke bookmark", Toast.LENGTH_LONG).show();
	      break;
  	    case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent listDoa = new Intent(ActivityDisplaySunnah.this, ActivitySunnah.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
          return true;
  	  }
  	  return true;
  	}
	
	public void addBookmark(String id){
		 ContentValues values = new ContentValues();
		 values.put("tipe","4");
 	     values.put("id_item",id);
 	     dbAdapter.insertBookmark(values);
 	     dbAdapter.close();
 	    
	}
	
	public void nextPage(String id){
		int page=Integer.valueOf(id)+1;
		Intent intentNext;
		if(Integer.valueOf(id)==144){
			intentNext= new Intent(ActivityDisplaySunnah.this, ActivitySunnah.class);
		}else{
			intentNext = new Intent(ActivityDisplaySunnah.this, ActivityDisplaySunnah.class);
			intentNext.putExtra("id",String.valueOf(page));
	        
		//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
		}
		
		startActivity(intentNext);  
	}
	
	public void prevPage(String id){
		int page=Integer.valueOf(id)-1;
		Intent intentPrev;
		if(Integer.valueOf(id)==1){
			intentPrev= new Intent(ActivityDisplaySunnah.this,ActivitySunnah.class);
		}else{
			intentPrev = new Intent(ActivityDisplaySunnah.this, ActivityDisplaySunnah.class);
			intentPrev.putExtra("id",String.valueOf(page));
		}
		startActivity(intentPrev);  
	}

}

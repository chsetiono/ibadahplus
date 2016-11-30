package com.cipto.doa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.cipto.doa.R.id;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


public class ActivityDisplaySurah extends Activity{
	DBAdapter dbAdapter;
	ListView lv;
	String id_surat;
	String nomor_ayat;
	Integer position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_surah);
		Intent intent = getIntent();
		id_surat=intent.getStringExtra("id");  
		nomor_ayat=intent.getStringExtra("nomorAyat");  
		position=1;
		if(nomor_ayat!=null){
			position=Integer.valueOf(nomor_ayat)-1;
		}
		
		//final int ayat=Integer.valueOf(intent.getStringExtra("ayat"))+1;  
	    dbAdapter = new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
		String nama_surah=dbAdapter.getSurah(Integer.valueOf(id_surat)).getString(1);
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle(nama_surah);
	    lv = (ListView)findViewById(id.lvAyat);
	    setData(lv, id_surat, position);
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
  		case R.id.cariayat:
  			dialogCariAyat();
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
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        int position = info.position;
        Integer id_ayat=Integer.valueOf(((TextView)info.targetView.findViewById(R.id.idAyat)).getText().toString());
        Cursor bookmark=dbAdapter.getBookmark(id_ayat);
        
		if (v.getId()==R.id.lvAyat) {
			MenuInflater inflater = getMenuInflater();
			if(bookmark.getCount() != 0){		
				inflater.inflate(R.menu.menu_options_bookmark, menu); 
			}else{
				inflater.inflate(R.menu.menu_options, menu);  
			}
				
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  String id_surat=getIntent().getStringExtra("id");
	  
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int selectpos = info.position+1;
	  String id_bookmark=((TextView)info.targetView.findViewById(R.id.idAyat)).getText().toString();
	  switch(item.getItemId()) {
      case R.id.addBookmark:
    	  addBookmark(id_bookmark,id_surat);
    	  //setData(lv, id_surat, position);
    	  Toast.makeText(this,"Ditambahkan ke bookmark",Toast.LENGTH_SHORT).show();
         return true;
      case R.id.deleteBookmark:
    	  dbAdapter.deleteBookmarkByItem(Integer.valueOf(id_bookmark));
    	  //setData(lv, id_surat, position);
    	  Toast.makeText(this,"Dihapus dari bookmark",Toast.LENGTH_SHORT).show();
         return true;   
      case R.id.bacaTerakhir:
    	  addTerakhir(id_bookmark);
    	  //setData(lv, id_surat, position);
    	  Toast.makeText(this,"Ditandai terakhir dibaca",Toast.LENGTH_SHORT).show();
         return true;
       default:
             return super.onContextItemSelected(item);
	  }

	}
	
	public void addBookmark(String id, String id_surat){
		 ContentValues values = new ContentValues();
		 values.put("tipe","3");
 	     values.put("id_item",id);
 	     values.put("id_surat",id_surat);
 	     dbAdapter.insertBookmark(values);
 	     dbAdapter.close();
	}
	
	public void addTerakhir(String id){
		ContentValues last_read= new ContentValues();
		last_read.put("value",id);
		dbAdapter.updateSetting(last_read, dbAdapter.SETTING_QURAN_TERAHIR);
	    dbAdapter.close();
	}
	
	public void nextPage(String id){
		int page=Integer.valueOf(id)+1;
		Intent intentNext;
		if(page==145){
			intentNext= new Intent(ActivityDisplaySurah.this, ActivityQuran.class);
		}else{
			intentNext = new Intent(ActivityDisplaySurah.this, ActivityDisplaySurah.class);
			intentNext.putExtra("id",String.valueOf(page));
			intentNext.putExtra("nomorAyat","1");
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
			intentPrev.putExtra("nomorAyat","1");	
		}
		startActivity(intentPrev);  
	}

	public void setData(ListView listview, String id_surat, Integer position){
		List<HashMap<String,String>> listAyat = dbAdapter.getAllAyat(id_surat);
	 	String[] from = new String[] { "teks_arab","teks_indo","id_ayat"};
		int[] to = new int[] {R.id.tvArab, R.id.tvIndo,R.id.idAyat};
		MySimpleAdapter adapter = new MySimpleAdapter(getApplicationContext(), listAyat, R.layout.list_row_ayat, from, to);
		
		listview.setAdapter(adapter);
			
		listview.setTextFilterEnabled(true);
		//lv.setSelection(ayat);
		listview.setSelection(position);
		registerForContextMenu(lv);
	}
	
	
	
	public class MySimpleAdapter extends SimpleAdapter {

	    private List<HashMap<String, String>> results;

	    public MySimpleAdapter(Context context, List<HashMap<String, String>> data, int resource, String[] from, int[] to) {
	        super(context, data, resource, from, to);
	        this.results = data;
	    }

	    public View getView(int position, View view, ViewGroup parent){


	        Typeface localTypeface1 = Typeface.createFromAsset(getAssets(), "fonts/me_quran.ttf");
	        View v = view;
	        if (v == null) {
	            LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.list_row_ayat, null,true);
	            
	        }
	        TextView tvArab = (TextView) v.findViewById(R.id.tvArab);
	       // TextView tvNo = (TextView) v.findViewById(R.id.tvNomor);
	        TextView tvArti = (TextView) v.findViewById(R.id.tvIndo);
	        TextView tvId = (TextView) v.findViewById(R.id.idAyat);
	        tvArab.setText(results.get(position).get("teks_arab"));
	        //tvNo.setText(results.get(position).get("nomor_ayat"));
	        tvArti.setText(results.get(position).get("teks_indo"));
	        tvId.setText(results.get(position).get("id_ayat"));
	        tvArab.setTypeface(localTypeface1);
	        return v;
	    }
	}
	
	private void dialogCariAyat(){
		 final Dialog dialog = new Dialog(this,R.style.DialogSetting);
	       dialog.setContentView(R.layout.dialog_cari_ayat);
	       dialog.setCancelable(true);
	       String defaultAyat="1";
	       final EditText etNomor=(EditText) dialog.findViewById(R.id.etAyat);
	       etNomor.setBackgroundResource(R.drawable.edittext);
	       Button btGo = (Button) dialog.findViewById(R.id.btGo);
	       
	       dialog.setTitle("Cari Ayat");
	       dialog.show();
	       btGo.setOnClickListener(new OnClickListener()
	       {

	           @Override
	           public void onClick(View v)
	           {
	        	 Integer max_number=lv.getAdapter().getCount();
	        	 
	        	 if(etNomor.getText().toString().trim().length() > 0){
		        	 Integer nomor=Integer.valueOf(etNomor.getText().toString())-1;
		        	 if(nomor!=0 && nomor <= max_number){
		        		 lv.setSelection(nomor);
		        		 dialog.dismiss();
		        	 }else{
		        		 Toast.makeText(ActivityDisplaySurah.this, "Ayat tidak ditemukan",Toast.LENGTH_SHORT).show();
		        	 }
	        	 }
		        	  
	        	 
	           }
	       });
	       
	       
	}
}

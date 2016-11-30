package com.cipto.doa;


import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi") public class DisplayDoa extends Activity {
	DBAdapter dbAdapter;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_doa);
		//tombol back
		dbAdapter = new DBAdapter(getApplicationContext());
        dbAdapter.openDataBase();     
        //baca textview
        TextView judulDoa = (TextView) findViewById(R.id.judulDoa);
		TextView arab = (TextView) findViewById(R.id.arab);
		Typeface localTypeface1 = Typeface.createFromAsset(getAssets(), "fonts/me_quran.ttf");
		arab.setTypeface(localTypeface1);
		TextView indo = (TextView) findViewById(R.id.indo);
		TextView arti= (TextView) findViewById(R.id.arti);
		
        // get the intent from which this activity is called.
        Intent intent = getIntent();
        
        // fetch value from key-value pair and make it visible on TextView.
        final int item=Integer.parseInt(intent.getStringExtra("id"));     
        
        Cursor doa=dbAdapter.getDoa(item);
  
        ActionBar ab = getActionBar(); 
        // ab.setDisplayHomeAsUpEnabled(true);
     	ab.setHomeButtonEnabled(true);
     	ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle(doa.getString(1));
	 
        judulDoa.setText(doa.getString(1));
        arab.setText(doa.getString(2));
        indo.setText(doa.getString(3));
        arti.setText(doa.getString(4));
        dbAdapter.close(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = getIntent();
        
        // fetch value from key-value pair and make it visible on TextView.
		int id=Integer.parseInt(intent.getStringExtra("id"));   
		Cursor bookmark=dbAdapter.getBookmark(id);
  	  switch (item.getItemId()) {
  	    case R.id.bookmark:
  	    	if(bookmark.getCount() == 0){
  	    		addBookmark(intent.getStringExtra("id"));
  	    	}
  	    	Toast.makeText(DisplayDoa.this, "Doa telah ditambahkan ke bookmark", Toast.LENGTH_LONG).show();
  	      break;
  	  case R.id.next:
  		int page=id+1;
  		if(page==64){
  			page=69;
  		}
  		
		if(id==100){
			Intent intentNext = new Intent(DisplayDoa.this, ListDoa.class);
	        startActivity(intentNext);  
		}else{
			Intent intentNext = new Intent(DisplayDoa.this, DisplayDoa.class);
			intentNext.putExtra("id",String.valueOf(page));
	        startActivity(intentNext);  
		//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
		}
  	      break;
  	 case R.id.prev:
  		int pagePrev=id-1;
  		if(pagePrev==68){
  			pagePrev=63;
  		}
		if(id==1){
			Intent intentPrev = new Intent(DisplayDoa.this, ListDoa.class);
	        startActivity(intentPrev);  
		}else{
		Intent intentPrev = new Intent(DisplayDoa.this, DisplayDoa.class);
		intentPrev.putExtra("id",String.valueOf(pagePrev)
				);
        startActivity(intentPrev);  
		//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
		}
 	   break;   
  	  case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent listDoa = new Intent(DisplayDoa.this, ListDoa.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
          return true;
  	  }
  	  return true;
  	}
	
	
	
	public void addBookmark(String id){
		 ContentValues values = new ContentValues();
		 values.put("tipe","1");
 	     values.put("id_item",id);
 	     
 	     dbAdapter.insertBookmark(values);
 	     dbAdapter.close();
 	     
	}
}

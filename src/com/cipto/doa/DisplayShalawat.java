package com.cipto.doa;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi") public class DisplayShalawat extends Activity {
	DBAdapter dbAdapter;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_shalawat);
		ActionBar ab = getActionBar(); 
        ab.setDisplayHomeAsUpEnabled(true);
        //baca textview
        TextView judul = (TextView) findViewById(R.id.judulShalawat);
		TextView arab = (TextView) findViewById(R.id.arabShalawat);
		TextView indo = (TextView) findViewById(R.id.latinShalawat);
		TextView arti= (TextView) findViewById(R.id.artiShalawat);
		Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        final int item=Integer.parseInt(intent.getStringExtra("id"));     
        dbAdapter = new DBAdapter(getApplicationContext());
        Cursor shalawat=dbAdapter.getShalawat(item);
        judul.setText(shalawat.getString(1));
        arab.setText(shalawat.getString(2));
        indo.setText(shalawat.getString(3));
        arti.setText(shalawat.getString(4));
        dbAdapter.close();
      
        //page navigation
        ImageButton btPrev=(ImageButton) findViewById(R.id.BtPrev);
        ImageButton btHome=(ImageButton) findViewById(R.id.BtHome);
        ImageButton btNext=(ImageButton) findViewById(R.id.BtNext);
        
        btPrev.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int page=item-1;
				if(page==0){
					Intent intentPrev = new Intent(DisplayShalawat.this, Shalawat.class);
			        startActivity(intentPrev);  
				}else{
				Intent intentPrev = new Intent(DisplayShalawat.this, DisplayShalawat.class);
				intentPrev.putExtra("id",String.valueOf(page));
		        startActivity(intentPrev);  
				//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
				}
			}
        	
        });
        
        
        btHome.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intentPrev = new Intent(DisplayShalawat.this, Main.class);
			    startActivity(intentPrev);  
			}
        	
        });
        
        btNext.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int page=item+1;
				if(page==100){
					Intent intentPrev = new Intent(DisplayShalawat.this, Shalawat.class);
			        startActivity(intentPrev);  
				}else{
				Intent intentPrev = new Intent(DisplayShalawat.this, DisplayShalawat.class);
				intentPrev.putExtra("id",String.valueOf(page));
		        startActivity(intentPrev);  
				//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
				}
			}
        	
        });
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
  	  switch (item.getItemId()) {
  	
  	    case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent listDoa = new Intent(DisplayShalawat.this, Shalawat.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
          return true;
  	  }
  	  return true;
  	}
	
}

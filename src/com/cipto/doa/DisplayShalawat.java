package com.cipto.doa;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

@SuppressLint("NewApi") public class DisplayShalawat extends Activity {
	DBAdapter dbAdapter;
	Integer id_item;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display_shalawat);
		ActionBar ab = getActionBar(); 
		ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Shalawat");
        //baca textview
        TextView judul = (TextView) findViewById(R.id.judulShalawat);
		TextView arab = (TextView) findViewById(R.id.arabShalawat);
		
		Typeface localTypeface1 = Typeface.createFromAsset(getAssets(), "fonts/me_quran.ttf");
		arab.setTypeface(localTypeface1);
		TextView indo = (TextView) findViewById(R.id.latinShalawat);
		TextView arti= (TextView) findViewById(R.id.artiShalawat);
		Intent intent = getIntent();
        // fetch value from key-value pair and make it visible on TextView.
        id_item=Integer.parseInt(intent.getStringExtra("id"));     
        dbAdapter = new DBAdapter(getApplicationContext());
        Cursor shalawat=dbAdapter.getShalawat(id_item);
        judul.setText(shalawat.getString(1));
        arab.setText(shalawat.getString(2));
        indo.setText(shalawat.getString(3));
        arti.setText(shalawat.getString(4));
        dbAdapter.close();
      
      
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display_shalawat, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
	  	  case R.id.next:
	  		int page=id_item+1;
			if(page==9){
				Intent intentNext = new Intent(DisplayShalawat.this, Shalawat.class);
		        startActivity(intentNext);  
			}else{
				Intent intentNext = new Intent(DisplayShalawat.this, DisplayShalawat.class);
				intentNext.putExtra("id",String.valueOf(page));
		        startActivity(intentNext);  
			//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
			}
	  	      break;
	  	 case R.id.prev:
	  		int pagePrev=id_item-1;
			if(pagePrev==0){
				Intent intentPrev = new Intent(DisplayShalawat.this, Shalawat.class);
		        startActivity(intentPrev);  
			}else{
			Intent intentPrev = new Intent(DisplayShalawat.this, DisplayShalawat.class);
			intentPrev.putExtra("id",String.valueOf(pagePrev)
					);
	        startActivity(intentPrev);  
			//Toast.makeText(Main.this, "test", Toast.LENGTH_SHORT).show();
			}
	 	   break;   
	  	  case android.R.id.home:
	          // app icon in action bar clicked; go home
	      	 Intent shalawat = new Intent(DisplayShalawat.this, Shalawat.class);
	      	 shalawat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			 startActivity(shalawat);  
	         return true;
	  	  }
  	  return true;
  	}
	
}

package com.cipto.doa;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Istigfar extends Activity {
	DBAdapter dbAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.istigfar);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		    ab.setHomeButtonEnabled(true);
		    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Istighfar");
	     
		dbAdapter = new DBAdapter(getApplicationContext());
		dbAdapter.openDataBase();
		final TextView textCount=(TextView) findViewById(R.id.countIstighfar);
		final TextView arab=(TextView) findViewById(R.id.arabIstighfar);
		Typeface myTypeface = Typeface.createFromAsset(getAssets(), "fonts/me_quran.ttf");
		arab.setTypeface(myTypeface);
		ImageButton btPlus=(ImageButton) findViewById(R.id.BtPlusIstighfar);
		ImageButton btReset=(ImageButton) findViewById(R.id.BtResetIstighfar);
		final ImageButton btSound=(ImageButton) findViewById(R.id.BtSound);
		final MediaPlayer mp = MediaPlayer.create(this, R.drawable.sound_tasbih);
		
		//set default sound status
		Integer defaultSound= Integer.valueOf(dbAdapter.getSettings(19).getString(2));
		if(defaultSound==0){
			btSound.setImageResource(R.drawable.sound_on);
		}else{
			btSound.setImageResource(R.drawable.sound_off);
		}
		
		btPlus.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int valueCount=Integer.valueOf(textCount.getText().toString())+1;
				textCount.setText(String.valueOf(valueCount));
				
				Integer soundStatus= Integer.valueOf(dbAdapter.getSettings(19).getString(2));
				if(soundStatus==0){
					mp.start();
				}
				
			}
		});
		
		btReset.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textCount.setText("0");
			}
		});
		btSound.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View arg0) {
				Integer soundSaved= Integer.valueOf(dbAdapter.getSettings(19).getString(2));
				// TODO Auto-generated method stub
				if(soundSaved==0){
					ContentValues setSound= new ContentValues();
					setSound.put("value","1");
					dbAdapter.updateSetting(setSound,19);
					btSound.setImageResource(R.drawable.sound_off);
				}else{
					ContentValues setSound= new ContentValues();
					setSound.put("value","0");	
					dbAdapter.updateSetting(setSound,19);
					btSound.setImageResource(R.drawable.sound_on);
				}
			
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display_dzikir, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) { 
  	  switch (item.getItemId()) {
  	  	case R.id.prev:
  	  		Intent intent= new Intent(Istigfar.this, DzikirMain.class);
  	  		startActivity(intent);
	      break;
  		case R.id.next:
  			Intent intentTahmid= new Intent(Istigfar.this, Tahmid.class);
  	  		startActivity(intentTahmid);
	      break;
  	    case android.R.id.home:
  	    	Intent intentHome= new Intent(Istigfar.this, DzikirMain.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}
}

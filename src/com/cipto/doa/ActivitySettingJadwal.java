package com.cipto.doa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("NewApi") public class ActivitySettingJadwal extends Activity{
	Spinner spMetode,spMetodeAshar;
	DBAdapter dbAdapter;
	private PendingIntent pendingIntent;
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_jadwal);
		 ActionBar ab = getActionBar(); 
		 ab.setDisplayHomeAsUpEnabled(true);
		 
		
		 
		//read data from database as default
		 dbAdapter = new DBAdapter(getApplicationContext());
		 dbAdapter.openDataBase();
		int defaultMetode= Integer.valueOf(dbAdapter.getSettings(4).getString(2));
		int defaultMetodeAshar=Integer.valueOf(dbAdapter.getSettings(5).getString(2));
		String defaultNotifikasiSubuh= dbAdapter.getSettings(6).getString(2);
		String defaultNotifikasiDzuhur= dbAdapter.getSettings(7).getString(2);
		String defaultNotifikasiAshar= dbAdapter.getSettings(8).getString(2);
		String defaultNotifikasiMaghrib= dbAdapter.getSettings(9).getString(2);
		String defaultNotifikasiIsya= dbAdapter.getSettings(10).getString(2);
		double defaultKoreksiSubuh= Double.valueOf(dbAdapter.getSettings(14).getString(2))+60;
		double defaultKoreksiDzuhur= Double.valueOf(dbAdapter.getSettings(15).getString(2))+60;
		double defaultKoreksiAshar=Double.valueOf( dbAdapter.getSettings(16).getString(2))+60;
		double defaultKoreksiMaghrib=Double.valueOf( dbAdapter.getSettings(17).getString(2))+60;
		double defaultKoreksiIsya=Double.valueOf( dbAdapter.getSettings(18).getString(2))+60;
		
		//Metode perhitungan
		spMetode=(Spinner) findViewById(R.id.spMetode);
	
		List<String> listMetode= new ArrayList<String>();
	    listMetode.add("Jafari");
	    listMetode.add("ISNA");
	    listMetode.add("MWL");
	    listMetode.add("Makkah");
	    listMetode.add("Egypt");
	    listMetode.add("Tehran");
	    listMetode.add("Indonesia");
	    
	   
	    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listMetode);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spMetode.setAdapter(dataAdapter);
	    spMetode.setSelection(defaultMetode);
	    
	    //Metode Ashar
	    spMetodeAshar=(Spinner) findViewById(R.id.spAshar);
	    List<String> listAshar= new ArrayList<String>();
	    listAshar.add("Syafii");
	    listAshar.add("Hanafi");
	    ArrayAdapter<String> ashardapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listAshar);
	    spMetodeAshar.setAdapter(ashardapter);
	    spMetodeAshar.setSelection(defaultMetodeAshar);
	    
	    //Notifikasi
	    CheckBox cbSubuh=(CheckBox) findViewById(R.id.cbSubuh);
		CheckBox  cbDzuhur=(CheckBox) findViewById(R.id.cbDzuhur);
	    CheckBox cbAshar=(CheckBox) findViewById(R.id.cbAshar);
	    CheckBox cbMaghrib=(CheckBox) findViewById(R.id.cbMaghrib);
	    CheckBox  cbIsya=(CheckBox) findViewById(R.id.cbIsya);
	    
	    if(defaultNotifikasiSubuh.equals("1")){
	    	cbSubuh.setChecked(true);
	    }
	    if(defaultNotifikasiDzuhur.equals("1")){
	    	cbDzuhur.setChecked(true);
	    }
	    if(defaultNotifikasiAshar.equals("1")){
	    	cbAshar.setChecked(true);
	    }
	    if(defaultNotifikasiMaghrib.equals("1")){
	    	cbMaghrib.setChecked(true);
	    }
	    if(defaultNotifikasiIsya.equals("1")){
	    	cbIsya.setChecked(true);
	    }
	    
  
	    //Koreksi Waktu
	    SeekBar sbSubuh=(SeekBar) findViewById(R.id.sbSubuh);
	    SeekBar sbDzuhur=(SeekBar) findViewById(R.id.sbDzuhur);
	    SeekBar sbAshar=(SeekBar) findViewById(R.id.sbAshar);
	    SeekBar sbMaghrib=(SeekBar) findViewById(R.id.sbMaghrib1);
	    SeekBar sbIsya=(SeekBar) findViewById(R.id.sbIsya);
	    sbSubuh.setMax(120);
	    sbDzuhur.setMax(120);
	    sbAshar.setMax(120);
	    sbMaghrib.setMax(120);
	    sbIsya.setMax(120);
	    //set default
	    sbSubuh.setProgress((int)defaultKoreksiSubuh);
	    sbDzuhur.setProgress((int)defaultKoreksiDzuhur);
	    sbAshar.setProgress((int)defaultKoreksiAshar);
	    sbMaghrib.setProgress((int)defaultKoreksiMaghrib);
	    sbIsya.setProgress((int)defaultKoreksiIsya);
	   
	    
	   
	    final TextView valueSubuh=(TextView) findViewById(R.id.valueSubuh);
	    final TextView valueDzuhur=(TextView) findViewById(R.id.valueDzuhur);
	    final TextView valueAshar=(TextView) findViewById(R.id.valueAshar);
	    final TextView valueMaghrib=(TextView) findViewById(R.id.valueMaghrib);
	    final TextView valueIsya=(TextView) findViewById(R.id. valueIsya);
	    valueSubuh.setText(String.valueOf(defaultKoreksiSubuh-60));
	    valueDzuhur.setText(String.valueOf(defaultKoreksiDzuhur-60)); 
	    valueAshar.setText(String.valueOf(defaultKoreksiAshar-60)); 
	    valueMaghrib.setText(String.valueOf(defaultKoreksiMaghrib-60)); 
	    valueIsya.setText(String.valueOf(defaultKoreksiIsya-60)); 
	    
	    sbSubuh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar, int progress, 
	    	     boolean fromUser) { 
	    		   double value=Double.valueOf(progress)-60.0;
	    	    // TODO Auto-generated method stub 
	    		   valueSubuh.setText(String.valueOf(value)); 
	    	   } 

	    	   @Override 
	    	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 

	    	   @Override 
	    	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 
	    }); 
	    
	    sbDzuhur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar, int progress, 
	    	     boolean fromUser) { 
	    		   double value=Double.valueOf(progress)-60.0;
	    	    // TODO Auto-generated method stub 
	    		   valueDzuhur.setText(String.valueOf(value)); 
	    	   } 

	    	   @Override 
	    	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 

	    	   @Override 
	    	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 
	    });
	    
	    sbAshar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar, int progress, 
	    	     boolean fromUser) { 
	    		   double value=Double.valueOf(progress)-60.0;
	    	    // TODO Auto-generated method stub 
	    		   valueAshar.setText(String.valueOf(value)); 
	    	   } 

	    	   @Override 
	    	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 

	    	   @Override 
	    	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 
	    });
	    
	    sbMaghrib.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar, int progress, 
	    	     boolean fromUser) { 
	    		   double value=Double.valueOf(progress)-60.0;
	    	    // TODO Auto-generated method stub 
	    		   valueMaghrib.setText(String.valueOf(value)); 
	    	   } 

	    	   @Override 
	    	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 

	    	   @Override 
	    	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 
	    }); 
	    
	    sbIsya.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){ 

	    	   @Override 
	    	   public void onProgressChanged(SeekBar seekBar, int progress, 
	    	     boolean fromUser) { 
	    		   double value=Double.valueOf(progress)-60.0;
	    	    // TODO Auto-generated method stub 
	    		   valueIsya.setText(String.valueOf(value)); 
	    	   } 

	    	   @Override 
	    	   public void onStartTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 

	    	   @Override 
	    	   public void onStopTrackingTouch(SeekBar seekBar) { 
	    	    // TODO Auto-generated method stub 
	    	   } 
	    }); 
	
	
	} 
	    
	    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting_jadwal_save, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) { 
	    switch (item.getItemId()) {
	    	case R.id.save:
	    		saveSetting();
	  	      break;
	    	
	        case android.R.id.home:
	            // app icon in action bar clicked; go home
	        	 Intent home = new Intent(ActivitySettingJadwal.this, Main.class);
		            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			        startActivity(home);  
	            return true;
	        
	            default:
	            return super.onOptionsItemSelected(item);     
	    }
	    return true;
	}
	
	public void saveSetting(){
		//Metode Perhitungan
		int metode=spMetode.getSelectedItemPosition();
		ContentValues metodeHitung = new ContentValues();
		metodeHitung.put("value",String.valueOf(metode));
		dbAdapter.updateSetting(metodeHitung,4);
		
		//MetodeAshar
		int metodeAshar=spMetodeAshar.getSelectedItemPosition();
		ContentValues hitungAshar= new ContentValues();
		hitungAshar.put("value",String.valueOf(metodeAshar));
		dbAdapter.updateSetting(hitungAshar,5);
		
		//Notifikasi
		CheckBox cbSubuh=(CheckBox) findViewById(R.id.cbSubuh);
		CheckBox  cbDzuhur=(CheckBox) findViewById(R.id.cbDzuhur);
	    CheckBox cbAshar=(CheckBox) findViewById(R.id.cbAshar);
	    CheckBox cbMaghrib=(CheckBox) findViewById(R.id.cbMaghrib);
	    CheckBox  cbIsya=(CheckBox) findViewById(R.id.cbIsya);
		String notifikasiSubuh="0";
		String notifikasiDzuhur="0";
		String notifikasiAshar="0";
		String notifikasiMaghrib="0";
		String notifikasiIsya="0";
		
		if(cbSubuh.isChecked()){
			notifikasiSubuh="1";
		}
		if(cbDzuhur.isChecked()){
			notifikasiDzuhur="1";
		}
		if(cbAshar.isChecked()){
			notifikasiAshar="1";
		}
		if(cbMaghrib.isChecked()){
			notifikasiMaghrib="1";
		}
		if(cbIsya.isChecked()){
			notifikasiIsya="1";
		}
		
		//save notifikasi subuh
		ContentValues notifSubuh= new ContentValues();
		notifSubuh.put("value",notifikasiSubuh);
		dbAdapter.updateSetting(notifSubuh,6);
		//save notifikasi Dzuhur
		ContentValues notifDzuhur= new ContentValues();
		notifDzuhur.put("value",notifikasiDzuhur);
		dbAdapter.updateSetting(notifDzuhur,7);
		//save notifikasi Ashar
		ContentValues notifAshar= new ContentValues();
		notifAshar.put("value",notifikasiAshar);
		dbAdapter.updateSetting(notifAshar,8);
		//save notifikasi Maghrib
		ContentValues notifMaghrib= new ContentValues();
		notifMaghrib.put("value",notifikasiMaghrib);
		dbAdapter.updateSetting(notifMaghrib,9);
		//save notifikasi Isya
		ContentValues notifIsya= new ContentValues();
		notifIsya.put("value",notifikasiIsya);
		dbAdapter.updateSetting(notifIsya,10);
		
		
		//Koreksi Waktu
		TextView valueSubuh=(TextView) findViewById(R.id.valueSubuh);
		TextView valueDzuhur=(TextView) findViewById(R.id.valueDzuhur);
		TextView valueAshar=(TextView) findViewById(R.id.valueAshar);
		TextView valueMaghrib=(TextView) findViewById(R.id.valueMaghrib);
		TextView valueIsya=(TextView) findViewById(R.id. valueIsya);
	
		//save koreksi subuh
		ContentValues koreksiSubuh= new ContentValues();
		koreksiSubuh.put("value",valueSubuh.getText().toString());
		dbAdapter.updateSetting(koreksiSubuh,14);
		//save koreksi Dzuhur
		ContentValues koreksiDzuhur= new ContentValues();
		koreksiDzuhur.put("value",valueDzuhur.getText().toString());
		dbAdapter.updateSetting(koreksiDzuhur,15);
		//save koreksi Ashar
		ContentValues koreksiAshar= new ContentValues();
		koreksiAshar.put("value",valueAshar.getText().toString());
		dbAdapter.updateSetting(koreksiAshar,16);
		//save koreksi Maghrib
		ContentValues koreksiMaghrib= new ContentValues();
		koreksiMaghrib.put("value",valueMaghrib.getText().toString());
		dbAdapter.updateSetting(koreksiMaghrib,17);
		
		//save koreksi Isya
		ContentValues koreksiIsya= new ContentValues();
		koreksiIsya.put("value",valueIsya.getText().toString());
		dbAdapter.updateSetting(koreksiIsya,18);
		
		Toast.makeText(this,"Pengaturan Jadwal Shalat Tersimpan", Toast.LENGTH_LONG).show();
		
	}
	

}

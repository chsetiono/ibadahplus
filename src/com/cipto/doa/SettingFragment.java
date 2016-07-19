package com.cipto.doa;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

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
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {
 
    public SettingFragment(){}
    Spinner spMetode,spMetodeAshar;
	DBAdapter dbAdapter;
	CheckBox cbSubuh,cbDzuhur,cbAshar,cbMaghrib,cbIsya;
	SeekBar sbSubuh,sbDzuhur,sbAshar,sbMaghrib,sbIsya;
	TextView valueSubuh,valueDzuhur,valueAshar,valueMaghrib,valueIsya;
	private PendingIntent pendingIntent;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        setHasOptionsMenu(true);
		//read data from database as default
		 dbAdapter = new DBAdapter(getActivity().getApplicationContext());
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
			spMetode=(Spinner) rootView.findViewById(R.id.spMetode);
		
			List<String> listMetode= new ArrayList<String>();
		    listMetode.add("Jafari");
		    listMetode.add("ISNA");
		    listMetode.add("MWL");
		    listMetode.add("Makkah");
		    listMetode.add("Egypt");
		    listMetode.add("Tehran");
		    listMetode.add("Indonesia");
		    listMetode.add("Custom");
		    
		   
		    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,listMetode);		   
		    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spMetode.setAdapter(dataAdapter);
		    spMetode.setSelection(defaultMetode,false);
		    spMetode.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		            // your code here
		        	ContentValues metodeHitung = new ContentValues();
		    		metodeHitung.put("value",String.valueOf(String.valueOf(position)));
		    		dbAdapter.updateSetting(metodeHitung,4);
		    		Toast.makeText(getActivity(),"Pengaturan metode perhitungan tersimpan.", Toast.LENGTH_LONG).show();
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }

		    });
		    
		    
		  //Metode Ashar
		    spMetodeAshar=(Spinner) rootView.findViewById(R.id.spAshar);
		    List<String> listAshar= new ArrayList<String>();
		    listAshar.add("SYAFI'I");
		    listAshar.add("HANAFI");
		    ArrayAdapter<String> ashardapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, listAshar);
		    spMetodeAshar.setAdapter(ashardapter);
		    spMetodeAshar.setSelection(defaultMetodeAshar,false);
		    
		    spMetodeAshar.setOnItemSelectedListener(new OnItemSelectedListener() {
		        @Override
		        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		            // your code here
		        	ContentValues hitungAshar= new ContentValues();
		    		hitungAshar.put("value",String.valueOf(position));
		    		dbAdapter.updateSetting(hitungAshar,5);
		    		Toast.makeText(getActivity(),"Pengaturan metode perhitungan ashar tersimpan.", Toast.LENGTH_LONG).show();
		        }

		        @Override
		        public void onNothingSelected(AdapterView<?> parentView) {
		            // your code here
		        }

		    });
		    
		    
		    //Notifikasi
		  cbSubuh=(CheckBox) rootView.findViewById(R.id.cbSubuh);
		  cbDzuhur=(CheckBox) rootView.findViewById(R.id.cbDzuhur);
		  cbAshar=(CheckBox) rootView.findViewById(R.id.cbAshar);
		  cbMaghrib=(CheckBox) rootView.findViewById(R.id.cbMaghrib);
		  cbIsya=(CheckBox) rootView.findViewById(R.id.cbIsya);
		    
		    
		    //set default nontifikasi
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
		    
		    //change notifikasi listener
		    /*
		    cbSubuh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiSubuh="0";
		        	String status="nonaktifkan";
		        	String time=waktuShalat(1);
	    		    int hour=Integer.valueOf(time.substring(0,2));
	    		    int minute=Integer.valueOf(time.substring(3,5));
		    		if(buttonView.isChecked()){
		    			notifikasiSubuh="1";
		    			status="aktifkan";
		    			startAlarm(1, hour, minute);
		    		}else{
		    			stopAlarm(1);
		    		}
		    		ContentValues notifSubuh= new ContentValues();
		    		notifSubuh.put("value",notifikasiSubuh);
		    		dbAdapter.updateSetting(notifSubuh,6);
		    		Toast.makeText(getActivity(),"Adzan Subuh di"+status, Toast.LENGTH_SHORT).show();
		        }
		    }
		    ); 
		    
		    cbDzuhur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiDzuhur="0";
		        	String status="nonaktifkan";
		        	String time=waktuShalat(2);
	    		    int hour=Integer.valueOf(time.substring(0,2));
	    		    int minute=Integer.valueOf(time.substring(3,5));
		    		if(buttonView.isChecked()){
		    			notifikasiDzuhur="1";
		    			status="aktifkan";
		    			startAlarm(2, hour, minute);
		    		}else{
		    			stopAlarm(2);
		    		}
		    		ContentValues notifDzuhur= new ContentValues();
		    		notifDzuhur.put("value",notifikasiDzuhur);
		    		dbAdapter.updateSetting(notifDzuhur,7);
		    		Toast.makeText(getActivity(),"Notifikasi Dzuhur di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    ); 
		    
		    cbAshar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiAshar="0";
		        	String status="nonaktifkan";
		        	String time=waktuShalat(3);
	    		    int hour=Integer.valueOf(time.substring(0,2));
	    		    int minute=Integer.valueOf(time.substring(3,5));
		    		if(buttonView.isChecked()){
		    			notifikasiAshar="1";
		    			status="aktifkan";
		    			startAlarm(3, hour, minute);
		    		}else{
		    			stopAlarm(3);
		    		}
		    		ContentValues notifAshar= new ContentValues();
		    		notifAshar.put("value",notifikasiAshar);
		    		dbAdapter.updateSetting(notifAshar,8);
		    		Toast.makeText(getActivity(),"Notifikasi Ashar di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    ); 
		    
		    cbMaghrib.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiMaghrib="0";
		        	String status="nonaktifkan";
		        	String time=waktuShalat(4);
	    		    int hour=Integer.valueOf(time.substring(0,2));
	    		    int minute=Integer.valueOf(time.substring(3,5));
		    		if(buttonView.isChecked()){
		    			notifikasiMaghrib="1";
		    			status="aktifkan";
		    			startAlarm(4, hour, minute);
		    		}else{
		    			stopAlarm(4);
		    		}
		    		ContentValues notifMaghrib= new ContentValues();
		    		notifMaghrib.put("value",notifikasiMaghrib);
		    		dbAdapter.updateSetting(notifMaghrib,9);
		    		Toast.makeText(getActivity(),"Notifikasi Maghrib di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    ); 
		    
		    cbIsya.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiIsya="0";
		        	String status="nonaktifkan";
		        	String time=waktuShalat(4);
	    		    int hour=Integer.valueOf(time.substring(0,2));
	    		    int minute=Integer.valueOf(time.substring(3,5));
		    		if(buttonView.isChecked()){
		    			notifikasiIsya="1";
		    			status="aktifkan";
		    			startAlarm(4, hour, minute);
		    		}else{
		    			stopAlarm(4);
		    		}
		    		ContentValues notifIsya= new ContentValues();
		    		notifIsya.put("value",notifikasiIsya);
		    		dbAdapter.updateSetting(notifIsya,10);
		    		Toast.makeText(getActivity(),"Notifikasi Isya di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    );  
	 		*/
		    //Koreksi Waktu
		    sbSubuh=(SeekBar) rootView.findViewById(R.id.sbSubuh);
		    sbDzuhur=(SeekBar) rootView.findViewById(R.id.sbDzuhur);
		    sbAshar=(SeekBar) rootView.findViewById(R.id.sbAshar);
		    sbMaghrib=(SeekBar) rootView.findViewById(R.id.sbMaghrib1);
		    sbIsya=(SeekBar) rootView.findViewById(R.id.sbIsya);
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
		   
		    
		   
		    valueSubuh=(TextView) rootView.findViewById(R.id.valueSubuh);
		    valueDzuhur=(TextView) rootView.findViewById(R.id.valueDzuhur);
		    valueAshar=(TextView) rootView.findViewById(R.id.valueAshar);
		    valueMaghrib=(TextView) rootView.findViewById(R.id.valueMaghrib);
		    valueIsya=(TextView) rootView.findViewById(R.id. valueIsya);
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
		    		   ContentValues koreksiSubuh= new ContentValues();
		    		   koreksiSubuh.put("value",valueSubuh.getText().toString());
		    		   //dbAdapter.updateSetting(koreksiSubuh,14);
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
		    		   ContentValues koreksiDzuhur= new ContentValues();
		    		   koreksiDzuhur.put("value",valueDzuhur.getText().toString());
		    		   //dbAdapter.updateSetting(koreksiDzuhur,15);
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
		    		   ContentValues koreksiAshar= new ContentValues();
		    		   koreksiAshar.put("value",valueAshar.getText().toString());
		    		   //dbAdapter.updateSetting(koreksiAshar,16);
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
		    		   ContentValues koreksiMaghrib= new ContentValues();
		    		   koreksiMaghrib.put("value",valueMaghrib.getText().toString());
		    		   //dbAdapter.updateSetting(koreksiMaghrib,17);
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
		    		   ContentValues koreksiIsya= new ContentValues();
		    		   koreksiIsya.put("value",valueMaghrib.getText().toString());
		    		   //dbAdapter.updateSetting(koreksiIsya,18);
		    	   } 
		    }); 
	    return rootView;
	
	} 
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
    	 //menu.clear();
    	inflater.inflate(R.menu.setting_jadwal_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
        
    }
    
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.save:
            // Not implemented here
        	saveSetting();
            return false;
        default:
            break;
        }

        return false;
    }
	    


	
	
	
	public void startAlarm(int id, int hour, int seconds){
		PendingIntent alarmIntent;
		Context context=getActivity().getApplicationContext();
		AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("code",id);
		alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		
		
		// Set the alarm to start at 8:30 a.m.
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, seconds);
		
		alarmMgr.set(alarmMgr.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
	}
	
	public void stopAlarm(int id){
		AlarmManager alarmMgr;
		PendingIntent alarmIntent;
		Context context=getActivity().getApplicationContext();
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, id, intent, 0);
		
		alarmMgr.cancel(alarmIntent);

	}
	
	public String waktuShalat(int jenis_shalat){
		 //get setting from database
		Context context=getActivity().getApplicationContext();
		DBAdapter dbAdapter = new DBAdapter(context);
	    dbAdapter.openDataBase();
	    double latitude=Double.parseDouble(dbAdapter.getSettings(dbAdapter.SETTING_LATITUDE).getString(2));
	    double longitude=Double.parseDouble(dbAdapter.getSettings(dbAdapter.SETTING_LONGITUDE).getString(2));
	    String kota=dbAdapter.getSettings(dbAdapter.SETTING_LOKASI).getString(2);
	    int metode=Integer.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_METODE).getString(2));
	    int metodeAshar=Integer.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_METODE_ASHAR).getString(2));
	    double koreksiSubuh=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
	    double koreksiDzuhur=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
	    double koreksiAshar=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
	    double koreksiMaghrib=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
	    double koreksiIsya=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));

	    
	    //GEt device timezone
	    TimeZone tz = TimeZone.getDefault();
	    String gmt = TimeZone.getTimeZone(tz.getID()).getDisplayName(false,
	            TimeZone.SHORT);
	    String z1 = gmt.substring(4);

	    String z = z1.replaceAll(":", ".");
	    double zo = Double.parseDouble(z);

	   // Hitung Jadwal
	     HitungWaktu prayers = new HitungWaktu();
	     //format 24 jam
	     prayers.setTimeFormat(prayers.Time24);
	     //metode hitung
	     prayers.setCalcMethod(metode);
	     //prayers.setAsrJuristic(metodeAshar);
	     prayers.setAdjustHighLats(prayers.AngleBased);
	     //koreksi manual
	     int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
	     
	     prayers.setKoreksiSubuh(koreksiSubuh);
	     prayers.setKoreksiDzuhur(koreksiDzuhur);
	     prayers.setKoreksiAshar(koreksiAshar);
	     prayers.setKoreksiMaghrib(koreksiMaghrib);
	     prayers.setKoreksiIsya(koreksiIsya);

	     prayers.tune(offsets);
	     Calendar cal = Calendar.getInstance();

	     
	 
	     ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
	                latitude, longitude,zo);
	     String time;
	   
	     if(jenis_shalat==1){
	    	 time=prayerTimes.get(0);
	     }else if(jenis_shalat==2){
	    	 time=prayerTimes.get(2);
	     }
	     else if(jenis_shalat==3){
	    	 time=prayerTimes.get(3);
	     }
	     else if(jenis_shalat==4){
	    	 time=prayerTimes.get(4);
	     }else{
	    	 time=prayerTimes.get(6);
	     }
	  
	   return time;
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
		
		
		
		//Notifikasi
		String notifikasiSubuh="0";
		String notifikasiDzuhur="0";
		String notifikasiAshar="0";
		String notifikasiMaghrib="0";
		String notifikasiIsya="0";
		
		
		//Set notifikasi subuh
		
		String timeSubuh=waktuShalat(1);
	    int hour=Integer.valueOf(timeSubuh.substring(0,2));
	    int minute=Integer.valueOf(timeSubuh.substring(3,5));
		
		
		if(cbSubuh.isChecked()){
			notifikasiSubuh="1";
			startAlarm(1, hour, minute);
		}else{
			stopAlarm(1);
		}
		
		
		
		//set dzuhur
		String timeDzuhur=waktuShalat(2);
	    int hourDzuhur=Integer.valueOf(timeDzuhur.substring(0,2));
	    int minuteDzuhur=Integer.valueOf(timeDzuhur.substring(3,5));
		if(cbDzuhur.isChecked()){
			notifikasiDzuhur="1";
			startAlarm(2, hourDzuhur, minuteDzuhur);
		}else{
			stopAlarm(2);
		}
		
		//set notifikasi ashar
		String timeAshar=waktuShalat(3);
	    int hourAshar=Integer.valueOf(timeAshar.substring(0,2));
	    int minuteAshar=Integer.valueOf(timeAshar.substring(3,5));
		if(cbAshar.isChecked()){
			notifikasiAshar="1";
			startAlarm(3, hourAshar,minuteAshar);
		}else{
			stopAlarm(3);
		}
		
		//set notifikasi Magrib
		String timeMaghrib=waktuShalat(4);
		int hourMaghrib=Integer.valueOf(timeMaghrib.substring(0,2));
		int minuteMaghrib=Integer.valueOf(timeMaghrib.substring(3,5));
		if(cbMaghrib.isChecked()){
			notifikasiMaghrib="1";
			startAlarm(4, hourMaghrib,minuteMaghrib);
		}else{
			stopAlarm(4);
		}
		//set notifikasi Isya
		String timeIsya=waktuShalat(5);
		int hourIsya=Integer.valueOf(timeIsya.substring(0,2));
		int minuteIsya=Integer.valueOf(timeIsya.substring(3,5));
		if(cbIsya.isChecked()){
			notifikasiIsya="1";
			startAlarm(5,  hourIsya,minuteIsya);
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
		
		
		
		Toast.makeText(getActivity(),"Pengaturan Jadwal Shalat Tersimpan", Toast.LENGTH_LONG).show();
		
	}
}
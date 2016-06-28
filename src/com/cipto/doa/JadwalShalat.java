package com.cipto.doa;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.cipto.doa.R.id;


import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings.System;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;


import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

@SuppressLint({ "NewApi", "SimpleDateFormat" }) public class JadwalShalat<nama_shalat> extends Activity implements LocationListener{
	Double lat,lng,timezone;
	Double ikhtiyat=(double)2;
	int metode,metodeAshar;
	Double waktuDzuhur;
	String lokasi;
	TextView tvLokasi;
	Calendar tanggal=Calendar.getInstance();
	DBAdapter dbAdapter;
	ListView lv;
	LocationManager locationManager;
	Context context;
	ProgressDialog dialog;
	private String provider;
	 // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jadwal_shalat);
		
		//set menubar
		 ActionBar ab = getActionBar(); 
		 ab.setDisplayHomeAsUpEnabled(true);
		 
		 //Baca dari layout
		 tvLokasi=(TextView) findViewById(R.id.tvLokasi);
		 TextView tvSubuh=(TextView) findViewById(R.id.tvSubuh);
		 TextView tvDzuhur=(TextView) findViewById(R.id.tvDzuhur);
		 TextView tvAshar=(TextView) findViewById(R.id.tvAshar);
		 TextView tvMaghrib=(TextView) findViewById(R.id.tvMaghrib);
		 TextView tvIsya=(TextView) findViewById(R.id.tvIsya);
		 TextView tvImsyak=(TextView) findViewById(R.id.tvImsyak);
		 
		 //get setting from database
	    dbAdapter = new DBAdapter(getApplicationContext());
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
	     /*
	     prayers.setKoreksiSubuh(koreksiSubuh);
	     prayers.setKoreksiDzuhur(koreksiDzuhur);
	     prayers.setKoreksiAshar(koreksiAshar);
	     prayers.setKoreksiMaghrib(koreksiMaghrib);
	     prayers.setKoreksiIsya(koreksiIsya);
	     */
	     int[] offsets = {0, 0, 0, 0, 0, 0, 0}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
	     prayers.tune(offsets);
	     Calendar cal = Calendar.getInstance();
	     ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
	                latitude, longitude,zo);
	     
	     // Write jadwal
	     tvLokasi.setText(String.valueOf(zo));
		 tvSubuh.setText(prayerTimes.get(0));
		 tvDzuhur.setText(prayerTimes.get(2));
		 tvAshar.setText(prayerTimes.get(3));
		 tvMaghrib.setText(prayerTimes.get(4));
		 tvIsya.setText(prayerTimes.get(6));
		 tvImsyak.setText(prayerTimes.get(7));
	}

		  @SuppressLint("NewApi") @Override
		    public boolean onCreateOptionsMenu(Menu menu) {
			  
		    	MenuInflater inflater = getMenuInflater();
		    	inflater.inflate(R.menu.menu_setting_jadwal, menu);
		    	//return super.onCreateOptionsMenu(menu);
		    	
		    	return true;
		    }
		  
		  @Override
			public boolean onOptionsItemSelected(MenuItem item) { 
			    switch (item.getItemId()) {
			    	case R.id.detectLocation:
			    	
			    		getLokasi();
			  	      break;
			    	case R.id.settingLocation:
			    		Intent setting=new Intent(JadwalShalat.this,ActivitySettingJadwal.class);
			    		startActivity(setting);
			  	      break;
			        case android.R.id.home:
			            // app icon in action bar clicked; go home
			        	 Intent home = new Intent(JadwalShalat.this, Main.class);
				            home.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					        startActivity(home);  
			            return true;
			        
			            default:
			            return super.onOptionsItemSelected(item);     
			    }
			    return true;
			}
	private void getLokasi(){
		dialog=new ProgressDialog(JadwalShalat.this);
		
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria=new Criteria();
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		provider = locationManager.getBestProvider(criteria, true); 
		
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
			showSettingsAlert();
		}else{
			dialog.show();
			dialog.setMessage("Please Wait. Finding Location...");
			locationManager.requestLocationUpdates(provider,MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this); 
			Location location= (locationManager.getLastKnownLocation(provider)); 
		}
		
		
	}

	public String getKota(double lat,double lng) throws IOException{
		Geocoder geocoder = new Geocoder(JadwalShalat.this, Locale.getDefault());
		String result = null;
		String cityName = null;
		try {
			
			 List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			 if (addresses.size() > 0)  
			 cityName = addresses.get(0).getLocality();
			 result=cityName;
		} catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
	        return "tidak dapat mengetahui nama kota";
	    }finally{
	    	return result;
	    }
		
	}

	public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
         // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getApplicationContext().startActivity(intent);
            }
        });
  
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
  
        // Showing Alert Message
        alertDialog.show();
    }
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		dialog.show();
		double latitude=location.getLatitude();
		double longitude=location.getLongitude();
		if(latitude!=0 && longitude!=0){
			ContentValues latvalue = new ContentValues();
		  	latvalue.put("value",String.valueOf(latitude)); //These Fields should be your String values of actual column names
		  	 dbAdapter.updateSetting(latvalue,1);
		  	 
		  	 //update longitude in database
		  	 ContentValues longvalue = new ContentValues();
		  	 longvalue.put("value",String.valueOf(longitude));
		  	 
		  	 //update lokasi di database
		  	 ContentValues kotavalue = new ContentValues();
		  	 try {
				kotavalue.put("value",getKota(latitude,longitude));
				dbAdapter.updateSetting(kotavalue,3);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  
		  	 /*
		  	String koordinat=String.valueOf(latitude).substring(0,12)+","+String.valueOf(longitude).substring(0,12);
		  	getTimeZone task = new  getTimeZone();
		  	 task.execute(new String[] { "https://maps.googleapis.com/maps/api/timezone/json?location=39.6034810,-119.6822510&timestamp=1331161200&key=AIzaSyAu24MS8S5gD95Mb1xsecp-t2kIm7ynlaE"});
		  	*/
		 	startAlarm();
		  	dialog.dismiss();
		  	
		  	Intent refresh = new Intent(this, JadwalShalat.class);
		  	startActivity(refresh);
		  	this.finish();
		 	
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void startAlarm(){
		 
		 String time=waktuShalat(getApplicationContext());
	     int hour=Integer.valueOf(time.substring(0,1));
	     int minute=Integer.valueOf(time.substring(3,4));
	     Calendar calNow = Calendar.getInstance();
	     Calendar calSet = (Calendar) calNow.clone();

	     calSet.set(Calendar.HOUR_OF_DAY, hour);
	     calSet.set(Calendar.MINUTE,minute);
	     calSet.set(Calendar.SECOND, 0);
	     calSet.set(Calendar.MILLISECOND, 0);

        if(calSet.compareTo(calNow) <= 0){
            //jika ternyata waktu lewat maka alarm akan di atur untuk besok
            calSet.add(Calendar.DATE, 1);
        }
		 Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
         AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
         alarmManager.set(AlarmManager.RTC_WAKEUP,  calSet.getTimeInMillis(), pendingIntent);
	}
	private void cancelAlarm(){
		 
     
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(),1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
  }
	
	public String waktuShalat(Context context){
		 //get setting from database
		DBAdapter dbAdapter = new DBAdapter(context.getApplicationContext());
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
	     prayers.tune(offsets);
	     Calendar cal = Calendar.getInstance();
	     int second = cal.get(Calendar.SECOND);
	     int minute = cal.get(Calendar.MINUTE);
	     int hourofday = cal.get(Calendar.HOUR_OF_DAY);
	     
	     double currentTime=Double.valueOf(hourofday)*60*60*1000+Double.valueOf(hourofday)*60*1000+Double.valueOf(minute)*60*1000+Double.valueOf(second)*1000;
	     ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
	                latitude, longitude,zo);
	     String time;
	     double subuhTime=Double.valueOf(prayerTimes.get(0).substring(0,1))*60*60*1000+Double.valueOf(prayerTimes.get(0).substring(3,4));
	     double dzuhurTime=Double.valueOf(prayerTimes.get(2).substring(0,1))*60*60*1000+Double.valueOf(prayerTimes.get(2).substring(3,4));
	     double asharTime=Double.valueOf(prayerTimes.get(3).substring(0,1))*60*60*1000+Double.valueOf(prayerTimes.get(3).substring(3,4));
	     double maghribTime=Double.valueOf(prayerTimes.get(4).substring(0,1))*60*60*1000+Double.valueOf(prayerTimes.get(4).substring(3,4));
	     double isyaTime=Double.valueOf(prayerTimes.get(6).substring(0,1))*60*60*1000+Double.valueOf(prayerTimes.get(6).substring(3,4));
	     if(currentTime >subuhTime && currentTime<dzuhurTime){
	    	 time=prayerTimes.get(1);
	     }else if(currentTime >dzuhurTime && currentTime<asharTime){
	    	 time=prayerTimes.get(2);
	     }
	     else if(currentTime >asharTime && currentTime<maghribTime){
	    	 time=prayerTimes.get(3);
	     }
	     else if(currentTime >maghribTime && currentTime<isyaTime){
	    	 time=prayerTimes.get(4);
	     }else{
	    	 time=prayerTimes.get(6);
	     }
	  
	   return time;
	}
	
	/*
	public class getTimeZone extends AsyncTask<String,Void,String>{

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
            //showDialog(progress_bar_type);
        }
		@Override
		protected String doInBackground(String... urls) {
			String response="";
			for (String url : urls) {
		        DefaultHttpClient client = new DefaultHttpClient();
		        HttpGet httpGet = new HttpGet(url);
		        try {
		          HttpResponse execute = client.execute(httpGet);
		          //InputStream content = execute.getEntity().getContent();
		          
		          String result = EntityUtils.toString(execute.getEntity());
		          JSONObject jo = new JSONObject(result);
		          String rawOffset=jo.getString("rawOffset");
		          double tz=Double.valueOf(rawOffset)/(60*60);
		          response+=String.valueOf(tz);
		        } catch (Exception e) {
		          e.printStackTrace();
		        }
		      }
		      return response;
		}
		 @Override
		 protected void onPostExecute(String result) {
			 // dismissDialog(progress_bar_type);
		      //text.setText(result);
			 ContentValues timezone = new ContentValues();
		  	 timezone.put("value",result);
		  	dbAdapter.updateSetting(timezone,dbAdapter.SETTING_TIMEZONE);
		  	tvLokasi.setText(result);
			dialog.dismiss();
		 }
		
	}
	
	*/
	
}

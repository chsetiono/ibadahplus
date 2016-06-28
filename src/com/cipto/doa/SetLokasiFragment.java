package com.cipto.doa;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
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
 

public class SetLokasiFragment extends Fragment  implements LocationListener{
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

    public SetLokasiFragment(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        dbAdapter = new DBAdapter(getActivity().getApplicationContext());
	    dbAdapter.openDataBase();
        getLokasi();
		 
         return rootView;
    }

    private void getLokasi(){
		dialog=new ProgressDialog(getActivity());
		locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		
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
		Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
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
	        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
	        return "tidak dapat mengetahui nama kota";
	    }finally{
	    	return result;
	    }
		
	}

	public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this.getActivity());
        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");
         // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
  
        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
               getActivity().startActivity(intent);
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
		  	 dbAdapter.updateSetting(longvalue,2);
		  	 
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
		 	
		  	Intent refresh = new Intent(getActivity(), Main.class);
		  	startAlarm();
		  	startActivity(refresh);
		  	getActivity().finish();
		  	
			Toast.makeText(getActivity(), "location has chANGES", Toast.LENGTH_LONG).show();
		 	
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
		 
		 String time=waktuShalat(getActivity().getApplicationContext());
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
		Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(), 1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,  calSet.getTimeInMillis(), pendingIntent);
	}
	private void cancelAlarm(){
		 
    
       Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
       PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity().getBaseContext(),1, intent, 0);
       AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
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
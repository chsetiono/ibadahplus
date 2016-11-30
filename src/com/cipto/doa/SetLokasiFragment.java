package com.cipto.doa;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.support.v4.app.Fragment;
//import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;




import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;

import android.content.ContentValues;

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
        View rootView = inflater.inflate(R.layout.fragment_lokasi, container, false);
        setHasOptionsMenu(true);
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
			dialog.setMessage("Mohon tunggu. Mencari lokasi...");
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
     
        final Dialog alertDialog= new Dialog(this.getActivity(),R.style.DialogSetting);
	    alertDialog.setContentView(R.layout.dialog_lokasi);
	    final TextView etMessage=(TextView)  alertDialog.findViewById(R.id.dialog_text);
	    Button btOk=(Button)  alertDialog.findViewById(R.id.btOK);
	    Button btCancel=(Button)  alertDialog.findViewById(R.id.btCancel);
        // Setting Dialog Title
        alertDialog.setTitle("GPS/Lokasi Non Aktif");
         // Setting Dialog Message
        etMessage.setText("GPS/Lokasi tidak aktif. Aktifkan ke menu setting ?");
        alertDialog.show();
        // On pressing Settings button
        btOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
            	Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                //getActivity().startActivity(intent);
                getActivity().startActivityForResult(intent, 1);
                alertDialog.dismiss();
            }
        });
        
        btCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
            	 alertDialog.dismiss();
            }
        });
       
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
		  
			String notifikasiSubuh= dbAdapter.getSettings(6).getString(2);
			String notifikasiDzuhur= dbAdapter.getSettings(7).getString(2);
			String notifikasiAshar= dbAdapter.getSettings(8).getString(2);
			String notifikasiMaghrib= dbAdapter.getSettings(9).getString(2);
			String notifikasiIsya= dbAdapter.getSettings(10).getString(2);
			
		  
		  	String timeSubuh=waktuShalat(1);
		  	int hour=Integer.valueOf(timeSubuh.substring(0,2));
		    int minute=Integer.valueOf(timeSubuh.substring(3,5));
		  	if(notifikasiSubuh.equals("1")){
		  		startAlarm(1,hour,minute);
		  	}
		  	else{
		  		stopAlarm(1);
		  	}
		  	String timeDzuhur=waktuShalat(2);
		  	hour=Integer.valueOf(timeDzuhur.substring(0,2));
		    minute=Integer.valueOf(timeDzuhur.substring(3,5));
		  	if(notifikasiDzuhur.equals("1")){
		  		startAlarm(2,hour,minute);
		  	}else{
		  		stopAlarm(2);
		  	}
		  	
		  	String timeAshar=waktuShalat(3);
		  	hour=Integer.valueOf(timeAshar.substring(0,2));
		    minute=Integer.valueOf(timeAshar.substring(3,5));
		  	if(notifikasiAshar.equals("1")){
		  		startAlarm(3,hour,minute);
		  	}
		  	
		  	String timeMaghrib=waktuShalat(4);
		  	hour=Integer.valueOf(timeMaghrib.substring(0,2));
		    minute=Integer.valueOf(timeMaghrib.substring(3,5));
		  	if(notifikasiMaghrib.equals("1")){
		  		startAlarm(4,hour,minute);
		  	}else{
		  		stopAlarm(4);
		  	}
		  	
		  	String timeIsya=waktuShalat(5);
		  	hour=Integer.valueOf(timeIsya.substring(0,2));
		    minute=Integer.valueOf(timeIsya.substring(3,5));
		  	if(notifikasiIsya.equals("1")){
		  		startAlarm(5,hour,minute);
		  	}else{
		  		stopAlarm(5);
		  	}

			Intent refresh = new Intent(getActivity(), Main.class);
			startActivity(refresh);
			Toast.makeText(getActivity(), "lokasi berhasil dideteksi", Toast.LENGTH_LONG).show();
		 	
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
	
	public void startAlarm(int id, int hour, int minutes){
		PendingIntent alarmIntent;
		Context context=getActivity().getApplicationContext();
		AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("code",id);
		alarmIntent = PendingIntent.getBroadcast(context, id, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
	//	calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 0); 
		//alarmMgr.set(alarmMgr.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
		//alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
			//	24 * 60 * 60 * 1000, alarmIntent);
		alarmMgr.setRepeating(alarmMgr.RTC, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, alarmIntent);
	}
	
	public void stopAlarm(int id){
		AlarmManager alarmMgr;
		PendingIntent alarmIntent;
		Context context=getActivity().getApplicationContext();
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, id, intent,PendingIntent.FLAG_UPDATE_CURRENT);
		alarmMgr.cancel(alarmIntent);

	}
	
	public String waktuShalat(int jenis_shalat){
		 //get setting from database
		Context context=getActivity().getApplicationContext();
		DBAdapter dbAdapter = new DBAdapter(context);
	    dbAdapter.openDataBase();
	    double latitude=Double.parseDouble(dbAdapter.getSettings(1).getString(2));
	    double longitude=Double.parseDouble(dbAdapter.getSettings(2).getString(2));
	    int metode=Integer.valueOf(dbAdapter.getSettings(4).getString(2));
	    int metodeAshar=Integer.valueOf(dbAdapter.getSettings(5).getString(2));
	    
	    double koreksiSubuh=Double.valueOf(dbAdapter.getSettings(14).getString(2));
	    double koreksiDzuhur=Double.valueOf(dbAdapter.getSettings(15).getString(2));
	    double koreksiAshar=Double.valueOf(dbAdapter.getSettings(16).getString(2));
	    double koreksiMaghrib=Double.valueOf(dbAdapter.getSettings(17).getString(2));
	    double koreksiIsya=Double.valueOf(dbAdapter.getSettings(18).getString(2));
	   
	    
	    //GEt device timezone
	    Calendar calendar = new GregorianCalendar();
	 	TimeZone timeZone = calendar.getTimeZone();
	 	int offset = timeZone.getRawOffset();
	 	long hours = TimeUnit.MILLISECONDS.toHours(offset);
	 	float minutes = (float)TimeUnit.MILLISECONDS.toMinutes(offset - TimeUnit.HOURS.toMillis(hours)) / 60;
	 	float gmt = hours + minutes;

	   // Hitung Jadwal
	     HitungWaktu prayers = new HitungWaktu();
	     //format 24 jam
	     prayers.setTimeFormat(prayers.Time24);
	     //metode hitung
	     prayers.setCalcMethod(metode);
	     prayers.setAsrJuristic(metodeAshar);
	     prayers.setAdjustHighLats(prayers.AngleBased);
	     //koreksi manual
	     double[] offsets = {koreksiSubuh, 0, koreksiDzuhur,koreksiAshar, 0, koreksiMaghrib, koreksiIsya}; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
 	     prayers.tune(offsets);
	     Calendar cal = Calendar.getInstance();
	     ArrayList<String> prayerTimes = prayers.getPrayerTimes(cal,
	                latitude, longitude,gmt);
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
	    	 time=prayerTimes.get(5);
	     }else{
	    	 time=prayerTimes.get(6);
	     }
	  
	   return time;
	}
	
	 @Override
	    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	        // Do something that differs the Activity's menu here
	    	 //menu.clear();
	    	inflater.inflate(R.menu.setting_lokasi, menu);
	        super.onCreateOptionsMenu(menu, inflater);
	        
	    }
	    
	    

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        switch (item.getItemId()) {
	        case R.id.refresh:
	            // Not implemented here
	        	getLokasi();
	            return false;
	        default:
	            break;
	        }

	        return false;
	    }
		    
}
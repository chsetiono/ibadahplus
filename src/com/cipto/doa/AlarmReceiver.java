package com.cipto.doa;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

public class AlarmReceiver extends  BroadcastReceiver{
	
	public void onReceive(Context context, Intent intent)
    {
		
        //stop last adzan
		Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
        
        //create new adzan untuk waktu shlat selanjutnya
        Intent intentNew = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingNew = PendingIntent.getBroadcast(context, 1, intent, 0);
        AlarmManager alarmManagerNew = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        
        //set new time
        String time=waktuShalat(context);
        int hour=Integer.valueOf(time.substring(0,1));
        int minute=Integer.valueOf(time.substring(3,4));
        Calendar calNow = Calendar.getInstance();
        Calendar calSet = (Calendar) calNow.clone();

        calSet.set(Calendar.HOUR_OF_DAY, hour);
        calSet.set(Calendar.MINUTE,minute);
        calSet.set(Calendar.SECOND, 0);
        calSet.set(Calendar.MILLISECOND, 0);
        alarmManagerNew.set(AlarmManager.RTC_WAKEUP,  calSet.getTimeInMillis(), pendingIntent);
        
        /* Setting the alarm here */

        showNotification(context);     
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
	
	private void showNotification(Context context){
		int NOTIFY_ME_ID=1337;
		NotificationManager mgr=
		            (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note=new Notification(R.drawable.icon_search,
		                       "Android Example Status message!",
		                       System.currentTimeMillis());
		         
       // This pending intent will open after notification click
		PendingIntent i=PendingIntent.getActivity(context, 0,
		                                                new Intent(context, JadwalShalat.class),
		                                                0);   
		note.setLatestEventInfo(context, "Android Example Notification Title",
		                                "This is the android example notification message", i);
		         
		//After uncomment this line you will see number of notification arrived
		//note.number=2;
		mgr.notify(NOTIFY_ME_ID, note);
	}
}

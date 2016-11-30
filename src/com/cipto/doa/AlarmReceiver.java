package com.cipto.doa;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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
import android.text.format.Time;
import android.widget.Toast;

public class AlarmReceiver extends  BroadcastReceiver{
	DBAdapter dbAdapter;
	public void onReceive(Context context, Intent intent)
    {
        //stop last adzan
		int code = intent.getIntExtra("code",0);
		if(code <1 ){
				int i=1;
				for(i=1;i<=5;i++){
					 AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				 	 
				        //create new adzan untuk waktu shlat selanjutnya
					   	
					    Intent intentNew = new Intent(context, AlarmReceiver.class);
				        intentNew.putExtra("code",i);
				        
				        PendingIntent pendingNew = PendingIntent.getBroadcast(context,i,intentNew,PendingIntent.FLAG_UPDATE_CURRENT);
				        //set hari selanjutnya
				        String time=waktuShalat(context,i,1);
				        int hour=Integer.valueOf(time.substring(0,2));
					    int minute=Integer.valueOf(time.substring(3,5));
					    
					    String setting= dbAdapter.getSettings(i+5).getString(2);
					    
					    
					    if(setting.equals("1")){
					    
					        Calendar calSet = Calendar.getInstance();
					        calSet.add(Calendar.DATE, 1);
					        calSet.setTimeInMillis(System.currentTimeMillis());
					        calSet.set(Calendar.HOUR_OF_DAY, hour);
					        calSet.set(Calendar.MINUTE,minute);
					        calSet.set(Calendar.SECOND, 0);
					        calSet.set(Calendar.MILLISECOND, 0);
					       // manager.set(manager.RTC_WAKEUP,  calSet.getTimeInMillis(), pendingNew); 
					        manager.setRepeating(manager.RTC, calSet.getTimeInMillis(), 24 * 60 * 60 * 1000, pendingNew);
					    }
				}
		}else{
	        String timeLast=waktuShalat(context,Integer.valueOf(code),0);
	        int hourLast=Integer.valueOf(timeLast.substring(0,2));
		    int minuteLast=Integer.valueOf(timeLast.substring(3,5));
	        Calendar calnow = Calendar.getInstance();
	        Calendar calset = Calendar.getInstance();
	        calset.setTimeInMillis(System.currentTimeMillis());
	        calnow.setTimeInMillis(System.currentTimeMillis());
	        calset.set(Calendar.HOUR_OF_DAY, hourLast);
	        calset.set(Calendar.MINUTE,minuteLast);
	        calset.set(Calendar.SECOND,0);
	        calset.set(Calendar.MILLISECOND, 0);
	        //matikan alarm
	 	  
		    //tampilkan notifikasi
	        if (calnow.get(Calendar.HOUR_OF_DAY)==hourLast){
		     //if (calnow.before(calset)){
				   showNotification(context,code,"");
			 }
		  
		   PendingIntent pendingIntent = PendingIntent.getBroadcast(context, code, intent,PendingIntent.FLAG_UPDATE_CURRENT);
	 	   AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
	 	  // manager.cancel(pendingIntent); 
	 	 
	        //create new adzan untuk waktu shlat selanjutnya
		    Intent intentNew = new Intent(context, AlarmReceiver.class);
	        intentNew.putExtra("code",code);
		   
	        PendingIntent pendingNew = PendingIntent.getBroadcast(context,code,intentNew,PendingIntent.FLAG_UPDATE_CURRENT);
	        //set hari selanjutnya
	        String time=waktuShalat(context,code,1);
	        int hour=Integer.valueOf(time.substring(0,2));
		    int minute=Integer.valueOf(time.substring(3,5));
	        Calendar calSet = Calendar.getInstance();
	        calSet.add(Calendar.DATE, 1);
	        calSet.setTimeInMillis(System.currentTimeMillis());
	        calSet.set(Calendar.HOUR_OF_DAY, hour);
	        calSet.set(Calendar.MINUTE,minute);
	        calSet.set(Calendar.SECOND, 0);
	        calSet.set(Calendar.MILLISECOND, 0);
	        
	        long startUpTime = calSet.getTimeInMillis()+(24 * 60 * 60 * 1000);
	        //long startUpTime = calSet.getTimeInMillis();

	       manager.setRepeating(manager.RTC, startUpTime, 24 * 60 * 60 * 1000, pendingNew);
	        //manager.set(manager.RTC_WAKEUP,  calSet.getTimeInMillis(), pendingNew);  
	       // manager.setInexactRepeating(manager.RTC,startUpTime, 24 * 60 * 60 * 1000,pendingNew);
		}
        
     }
	
	public String waktuShalat(Context context, int jenis_shalat, int day){
		 //get setting from database
		dbAdapter = new DBAdapter(context);
	    dbAdapter.openDataBase();
	    double latitude=Double.parseDouble(dbAdapter.getSettings(1).getString(2));
	    double longitude=Double.parseDouble(dbAdapter.getSettings(2).getString(2));
	    int metode=Integer.valueOf(dbAdapter.getSettings(4).getString(2));
	    int metodeAshar=Integer.valueOf(dbAdapter.getSettings(5).getString(2));
	    
	    double koreksiSubuh=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 		double koreksiDzuhur=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_DZUHUR).getString(2));
 	    double koreksiAshar=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_ASHAR).getString(2));
 	    double koreksiMaghrib=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_MAGHRIB).getString(2));
 	    double koreksiIsya=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_ISYA).getString(2));
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
	    
	     double[] offsets = {koreksiSubuh, 0, koreksiDzuhur,koreksiAshar, 0, koreksiMaghrib, koreksiIsya}; 
	     prayers.tune(offsets);
 	     
	     Calendar cal = Calendar.getInstance();
	     cal.add(Calendar.DATE, day);
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
	
	private void showNotification(Context context,  Integer jenis_shalat, String msg){
		int NOTIFY_ME_ID=1337+jenis_shalat;
		String text_shalat="";
		switch(jenis_shalat){
			case 1 :
				text_shalat="Subuh";
			break;
			case 2 :
				text_shalat="Dzuhur";
			break;
			case 3 :
				text_shalat="Ashar";
			break;
			case 4 :
				text_shalat="Maghrib";
			break;
			case 5 :
				text_shalat="Isya";
			break;
		}
		
		NotificationManager mgr=
		            (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification note=new Notification(R.drawable.logo,
		                       "Saatnya Shalat "+text_shalat+msg+"!",
		                       System.currentTimeMillis());
		note.flags|= Notification.FLAG_AUTO_CANCEL;    
       // This pending intent will open after notification click
		PendingIntent i=PendingIntent.getActivity(context, 0,
		                                                new Intent(context, Main.class),
		                                                0);   
		note.setLatestEventInfo(context, "Saatnya Shalat "+text_shalat,"",i);
		note.sound=(Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.adzan));
		mgr.notify(NOTIFY_ME_ID, note);
	
				
	}
	
	
}

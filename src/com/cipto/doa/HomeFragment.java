package com.cipto.doa;



import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.support.v4.app.Fragment;
//import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

 

public class HomeFragment extends Fragment implements View.OnClickListener{
    
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
    public HomeFragment(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        
        TextView tvLokasi=(TextView) rootView.findViewById(R.id.namaLokasi);
     	TextView tvSubuh=(TextView) rootView.findViewById(R.id.tvSubuh);
     	TextView tvDzuhur=(TextView) rootView.findViewById(R.id.tvDzuhur);
     	TextView tvAshar=(TextView) rootView.findViewById(R.id.ashar);
     	TextView tvMaghrib=(TextView) rootView.findViewById(R.id.maghrb);
     	TextView tvIsya=(TextView) rootView.findViewById(R.id.isya);
     	TextView tvImsyak=(TextView) rootView.findViewById(R.id.imsyak);
     	dbAdapter = new DBAdapter(getActivity().getApplicationContext());
     	

		
         try {
 			dbAdapter.createDataBase();
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        dbAdapter.openDataBase();
        dbAdapter.close();
        
        double latitude=Double.parseDouble(dbAdapter.getSettings(dbAdapter.SETTING_LATITUDE).getString(2));
 	    double longitude=Double.parseDouble(dbAdapter.getSettings(dbAdapter.SETTING_LONGITUDE).getString(2));
 	    String kota=dbAdapter.getSettings(dbAdapter.SETTING_LOKASI).getString(2);
 	    int metode=Integer.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_METODE).getString(2));
 	    int metodeAshar=Integer.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_METODE_ASHAR).getString(2));
 	   
 		double koreksiSubuh=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 		double koreksiDzuhur=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_DZUHUR).getString(2));
 	    double koreksiAshar=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_ASHAR).getString(2));
 	    double koreksiMaghrib=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_MAGHRIB).getString(2));
 	    double koreksiIsya=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_ISYA).getString(2));
 
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
	     // Write jadwal
	     //tvLokasi.setText(String.valueOf(7));
 	     SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
 	     String date=df.format(cal.getTime());
 	     tvLokasi.setText(kota);
		 tvSubuh.setText("Subuh \n"+prayerTimes.get(0));
		 tvDzuhur.setText("Dzuhur \n"+prayerTimes.get(2));
		 tvAshar.setText("Ashar \n"+prayerTimes.get(3));
		 tvMaghrib.setText("Maghrib \n"+prayerTimes.get(5));
		 tvIsya.setText("Isya \n"+prayerTimes.get(6));
		 tvImsyak.setText("Imsyak \n"+prayerTimes.get(7));
 	     
		 //handle imagebutton
		 //button quran
		 ImageButton btQuran=(ImageButton)  rootView.findViewById(R.id.imageQuran);
		 btQuran.setOnClickListener(this);
		 
		//button quran
		 ImageButton btKiblat=(ImageButton)  rootView.findViewById(R.id.imageKiblat);
		 btKiblat.setOnClickListener(this);
		 
		//button Majid
		 ImageButton btNews=(ImageButton)  rootView.findViewById(R.id.imageNews);
		 btNews.setOnClickListener(this);
		 
		//button Doa
		 ImageButton btDoa=(ImageButton)  rootView.findViewById(R.id.imageDoa);
		 btDoa.setOnClickListener(this);
		 
		//button Dzikir
		 ImageButton btDzikir=(ImageButton)  rootView.findViewById(R.id.imageDzikir);
		 btDzikir.setOnClickListener(this);
		 
		//button Shalawat
		 ImageButton btShalawat=(ImageButton)  rootView.findViewById(R.id.imageShalawat);
		 btShalawat.setOnClickListener(this);
		 
		//button Zakat
		 ImageButton btZakat=(ImageButton)  rootView.findViewById(R.id.imageZakat);
		 btZakat.setOnClickListener(this);
		//button Zakat
		 ImageButton btHadits=(ImageButton)  rootView.findViewById(R.id.imageHadits);
		 btHadits.setOnClickListener(this); 
         return rootView;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
			Intent intent;
			switch(v.getId()) {
	        case R.id.imageQuran:
	        	intent=new Intent(getActivity().getApplicationContext(), ActivityQuran.class);
	        	startActivity(intent);
	        break;
	        case R.id.imageKiblat:
	        	intent=new Intent(getActivity().getApplicationContext(), ActivityKiblat.class);
	        	startActivity(intent);
	        break;
	        case R.id.imageNews:
	        	Intent intentNews=new Intent(getActivity().getApplicationContext(), ActivityNews.class);
	        	startActivity(intentNews);
	        break;
	        case R.id.imageDoa:
	        	Intent intentDoa=new Intent(getActivity().getApplicationContext(), ListDoa.class);
	        	startActivity(intentDoa);
	        break;
	        case R.id.imageDzikir:
	        	Intent intentDzikir=new Intent(getActivity().getApplicationContext(), DzikirMain.class);
	        	startActivity(intentDzikir);
	        break;
	        case R.id.imageShalawat:
	        	Intent intentShalawat=new Intent(getActivity().getApplicationContext(), Shalawat.class);
	        	startActivity(intentShalawat);
	        break;
	        case R.id.imageZakat:
	        	Intent intentZakat=new Intent(getActivity().getApplicationContext(), ActivityZakat.class);
	        	startActivity(intentZakat);
	        break;
	        case R.id.imageHadits:
	        	Intent intentSunnah=new Intent(getActivity().getApplicationContext(), ActivitySunnah.class);
	        	startActivity(intentSunnah);
	        break;
		}
	}
}
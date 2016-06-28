package com.cipto.doa;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
	private String provider;
	 // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
 
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute
    public HomeFragment(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tvLokasi=(TextView) rootView.findViewById(R.id.lokasi);
     	TextView tvSubuh=(TextView) rootView.findViewById(R.id.subuh);
     	TextView tvDzuhur=(TextView) rootView.findViewById(R.id.dzuhur);
     	TextView tvAshar=(TextView) rootView.findViewById(R.id.ashar);
     	TextView tvMaghrib=(TextView) rootView.findViewById(R.id.maghrb);
     	TextView tvIsya=(TextView) rootView.findViewById(R.id.isya);
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
 	    double koreksiDzuhur=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 	    double koreksiAshar=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 	    double koreksiMaghrib=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 	    double koreksiIsya=Double.valueOf(dbAdapter.getSettings(dbAdapter.SETTING_KOREKSI_SUBUH).getString(2));
 	    
 	    //GEt device timezone
 	    TimeZone tz = TimeZone.getDefault();
 	    String gmt = TimeZone.getTimeZone(tz.getID()).getDisplayName(false,
 	            TimeZone.SHORT);
 	   // String z1 = gmt.substring(4);

 	    //String z = z1.replaceAll(":", ".");
 	    //double zo = Double.parseDouble(z);
 	    // Hitung Jadwal
 	     HitungWaktu prayers = new HitungWaktu();
 	     //format 24 jam
 	     prayers.setTimeFormat(prayers.Time24);
 	     //metode hitung
 	     prayers.setCalcMethod(prayers.Custom);
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
 	                latitude, longitude,7);
	     // Write jadwal
	     //tvLokasi.setText(String.valueOf(7));
 	     tvLokasi.setText("Demak Regency, Central Java, Indonesia");
		 tvSubuh.setText("Subuh \n"+prayerTimes.get(0));
		 tvDzuhur.setText("Dzuhur \n"+prayerTimes.get(2));
		 tvAshar.setText("Ashar \n"+prayerTimes.get(3));
		 tvMaghrib.setText("Maghrib \n"+prayerTimes.get(4));
		 tvIsya.setText("Isya \n"+prayerTimes.get(6));
 	     
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
	        	Toast.makeText(getActivity().getApplicationContext(), "comming soon...", Toast.LENGTH_LONG).show();
	        break;
	        case R.id.imageHadits:
	        	Intent intentSunnah=new Intent(getActivity().getApplicationContext(), ActivitySunnah.class);
	        	startActivity(intentSunnah);
	        break;
		}
	}
}
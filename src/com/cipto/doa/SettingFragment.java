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
		    CheckBox cbSubuh=(CheckBox) rootView.findViewById(R.id.cbSubuh);
			CheckBox  cbDzuhur=(CheckBox) rootView.findViewById(R.id.cbDzuhur);
		    CheckBox cbAshar=(CheckBox) rootView.findViewById(R.id.cbAshar);
		    CheckBox cbMaghrib=(CheckBox) rootView.findViewById(R.id.cbMaghrib);
		    CheckBox  cbIsya=(CheckBox) rootView.findViewById(R.id.cbIsya);
		    
		    
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
		    cbSubuh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiSubuh="0";
		        	String status="nonaktifkan";
		    		if(buttonView.isChecked()){
		    			notifikasiSubuh="1";
		    			status="aktifkan";
		    		}
		    		ContentValues notifSubuh= new ContentValues();
		    		notifSubuh.put("value",notifikasiSubuh);
		    		dbAdapter.updateSetting(notifSubuh,6);
		    		Toast.makeText(getActivity(),"Notifikasi Dzuhur di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    ); 
		    
		    cbDzuhur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

		        @Override
		        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
		        	String notifikasiDzuhur="0";
		        	String status="nonaktifkan";
		    		if(buttonView.isChecked()){
		    			notifikasiDzuhur="1";
		    			status="aktifkan";
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
		    		if(buttonView.isChecked()){
		    			notifikasiAshar="1";
		    			status="aktifkan";
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
		    		if(buttonView.isChecked()){
		    			notifikasiMaghrib="1";
		    			status="aktifkan";
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
		    		if(buttonView.isChecked()){
		    			notifikasiIsya="1";
		    			status="aktifkan";
		    		}
		    		ContentValues notifIsya= new ContentValues();
		    		notifIsya.put("value",notifikasiIsya);
		    		dbAdapter.updateSetting(notifIsya,10);
		    		Toast.makeText(getActivity(),"Notifikasi Isya di "+status, Toast.LENGTH_LONG).show();
		        }
		    }
		    );  
	 
		    //Koreksi Waktu
		    SeekBar sbSubuh=(SeekBar) rootView.findViewById(R.id.sbSubuh);
		    SeekBar sbDzuhur=(SeekBar) rootView.findViewById(R.id.sbDzuhur);
		    SeekBar sbAshar=(SeekBar) rootView.findViewById(R.id.sbAshar);
		    SeekBar sbMaghrib=(SeekBar) rootView.findViewById(R.id.sbMaghrib1);
		    SeekBar sbIsya=(SeekBar) rootView.findViewById(R.id.sbIsya);
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
		   
		    
		   
		    final TextView valueSubuh=(TextView) rootView.findViewById(R.id.valueSubuh);
		    final TextView valueDzuhur=(TextView) rootView.findViewById(R.id.valueDzuhur);
		    final TextView valueAshar=(TextView) rootView.findViewById(R.id.valueAshar);
		    final TextView valueMaghrib=(TextView) rootView.findViewById(R.id.valueMaghrib);
		    final TextView valueIsya=(TextView) rootView.findViewById(R.id. valueIsya);
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
		    		   dbAdapter.updateSetting(koreksiSubuh,14);
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
		    		   dbAdapter.updateSetting(koreksiDzuhur,15);
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
		    		   dbAdapter.updateSetting(koreksiAshar,16);
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
		    		   dbAdapter.updateSetting(koreksiMaghrib,17);
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
		    		   dbAdapter.updateSetting(koreksiIsya,18);
		    	   } 
		    }); 
	    return rootView;
	
	} 
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Do something that differs the Activity's menu here
    	 menu.clear();

        super.onCreateOptionsMenu(menu, inflater);
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {/*
        case R.id.activity_menu_item:
            // Not implemented here
            return false;
        case R.id.fragment_menu_item:
            // Do Fragment menu item stuff here
            return true;
            \*/
        default:
            break;
        }

        return false;
    }
	    


	public void saveSetting(View rootView){
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
		CheckBox cbSubuh=(CheckBox) rootView.findViewById(R.id.cbSubuh);
		CheckBox  cbDzuhur=(CheckBox) rootView.findViewById(R.id.cbDzuhur);
	    CheckBox cbAshar=(CheckBox) rootView.findViewById(R.id.cbAshar);
	    CheckBox cbMaghrib=(CheckBox) rootView.findViewById(R.id.cbMaghrib);
	    CheckBox  cbIsya=(CheckBox) rootView.findViewById(R.id.cbIsya);
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
		TextView valueSubuh=(TextView) rootView.findViewById(R.id.valueSubuh);
		TextView valueDzuhur=(TextView) rootView.findViewById(R.id.valueDzuhur);
		TextView valueAshar=(TextView) rootView.findViewById(R.id.valueAshar);
		TextView valueMaghrib=(TextView) rootView.findViewById(R.id.valueMaghrib);
		TextView valueIsya=(TextView) rootView.findViewById(R.id. valueIsya);
	
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
		
		Toast.makeText(getActivity(),"Pengaturan Jadwal Shalat Tersimpan", Toast.LENGTH_LONG).show();
		
	}
	

}
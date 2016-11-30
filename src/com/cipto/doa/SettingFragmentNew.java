package com.cipto.doa;

//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragmentNew extends Fragment {
 
    public SettingFragmentNew(){}
    Spinner spMetode,spMetodeAshar;
	DBAdapter dbAdapter;
	CheckBox cbSubuh,cbDzuhur,cbAshar,cbMaghrib,cbIsya;
	SeekBar sbSubuh,sbDzuhur,sbAshar,sbMaghrib,sbIsya;
	TextView valueSubuh,valueDzuhur,valueAshar,valueMaghrib,valueIsya;
	ListView lvSetting;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_setting_new, container, false);
        setHasOptionsMenu(true);
        dbAdapter = new DBAdapter(getActivity().getApplicationContext());
		dbAdapter.openDataBase();
	    lvSetting=(ListView) rootView.findViewById(R.id.lvSetting);
	    setData();
		return rootView;
		
    }
    
    
    public void setData(){
    
         List<HashMap<String,String>> listSetting =new ArrayList<HashMap<String,String>>(); 
         //metode perhitungan
         HashMap<String, String> hmMetode = new HashMap<String,String>(); 
         int metodeDb= Integer.valueOf(dbAdapter.getSettings(4).getString(2));
         String metode=savedMetode(metodeDb);
         hmMetode.put("nama_setting", "Metode");
         hmMetode.put("value_setting",metode);
         listSetting.add(hmMetode);
         
         
         HashMap<String, String> hmAshar = new HashMap<String,String>(); 
         int metodeAsharDb=Integer.valueOf(dbAdapter.getSettings(5).getString(2));
         String metodeAshar=metodeAshar(metodeAsharDb);
         hmAshar.put("nama_setting", "Metode Ashar");
         hmAshar.put("value_setting",metodeAshar);
         listSetting.add(hmAshar);
         
         
         HashMap<String, String> hmNotifikasi= new HashMap<String,String>(); 
         hmNotifikasi.put("nama_setting", "Notifikasi Adzan");
         hmNotifikasi.put("value_setting","");
         listSetting.add(hmNotifikasi);
         
         HashMap<String, String> hmKoreksi = new HashMap<String,String>(); 
         hmKoreksi.put("nama_setting", "Koreksi Waktu Manual");
         hmKoreksi.put("value_setting","");
         listSetting.add(hmKoreksi);
         
         HashMap<String, String> hmZakat = new HashMap<String,String>(); 
         hmZakat.put("nama_setting", "Perhitungan Zakat");
         hmZakat.put("value_setting","");
         listSetting.add(hmZakat);
 		
 	 	String[] from = new String[] { "nama_setting","value_setting" };
 		int[] to = new int[] { R.id.namaSetting, R.id.valueSetting};
 		SimpleAdapter adapter = new SimpleAdapter(getActivity(), listSetting, R.layout.list_setting, from, to);
 		lvSetting.setAdapter(adapter);
 		lvSetting.setOnItemClickListener(new OnItemClickListener() {

 			@Override
 			public void onItemClick(AdapterView<?> arg0, View view, int position,
 					long arg3) {
 				// TODO Auto-generated methoActivityZakatProfesi.javad stub
 	                
                 switch(position){
                 	case 0:
                 		dialogMetode();
                 	break;
                 	case 1:
                 		dialogMetodeAshar();
                 	break;
                 	case 2:
                 		dialogNotifikasi() ;
                 	break;
                 	case 3:
                 		dialogKoreksi();
                 	break;
                 	case 4:
                 		dialogZakat();
                 	break;
                
                 }
   
 			}
 			
 		});

    }
    public String savedMetode(int metode){
    	String text="";
    	switch(metode){
    		case 0:
    			text= "Ithna Ashari";
    		break;
    		case 1:
    			text= "University of Islamic Sciences, Karachi";
    		break;
    		case 2:
    			text= "Islamic Society of North America (ISNA)";
    		break;
    		case 3:
    			text= "Muslim World League (MWL)";
    		break;
    		case 4:
    			text= "Umm al-Qura, Makkah";
    		break;
    		case 5:
    			text= "Egyptian General Authority of Survey";
    		break;
    		case 6:
    			text= "Institute of Geophysics, University of Tehran";
    		break;
    		case 7:
    			text= "kementerian Agama Republik Indonesia";
    		break;
    		
    	}
    return text;
    }
    
    public int encodeMetode(int id){
    	int text=7;
    	switch(id){
    		case R.id.m0:
    			text= 0;
    		break;
    		case R.id.m1:
    			text= 1;
    		break;
    		case R.id.m2:
    			text= 2;
    		break;
    		case R.id.m3:
    			text= 3;
    		break;
    		case R.id.m4:
    			text= 4;
    		break;
    		case R.id.m5:
    			text= 5;
    		break;
    		case R.id.m6:
    			text= 6;
    		break;
    		case R.id.m7:
    			text= 7;
    		break;
    		
    	}
    	
    	return text;
    }
    
    public int decodeMetode(int metode){
    	int text=R.id.m7;
    	switch(metode){
    		case 0:
    			text= R.id.m0;
    		break;
    		case 1:
    			text=R.id.m1;
    		break;
    		case 2:
    			text= R.id.m2;
    		break;
    		case 3:
    			text=R.id.m3;
    		break;
    		case 4:
    			text= R.id.m4;
    		break;
    		case 5:
    			text=R.id.m5;
    		break;
    		case 6:
    			text= R.id.m6;
    		break;
    		case 7:
    			text=R.id.m7;
    		break;
    		
    		
    	}
    	
    	return text;
    }
    
    
    public int encodeMetodeAshar(int id){
    	int text=0;
    	switch(id){
    		case R.id.ma0:
    			text= 0;
    		break;
    		case R.id.ma1:
    			text= 1;
    		break;
    		
    	}
    	
    	return text;
    }
    
    public int decodeMetodeAshar(int metode){
    	int text=R.id.ma0;
    	switch(metode){
    		case 0:
    			text= R.id.ma0;
    		break;
    		case 1:
    			text=R.id.ma1;
    		break;
    		
    	}
    	
    	return text;
    }
    
    public String metodeAshar(int metode){
    	String text="";
    	if(metode==0){
    		text="Syafi'i";
    	}else{
    		text="Hanafi";
    	}
    	return text;
    }
    
    
    private void dialogMetode() 
    {
       final Dialog dialogMetode = new Dialog(getActivity(),R.style.DialogSetting);
       dialogMetode.setContentView(R.layout.setting_metode);
       dialogMetode.setCancelable(true);
       final RadioGroup rgMetode=(RadioGroup) dialogMetode.findViewById(R.id.rgMetode);
       int metodeDb=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_METODE).getString(2));
       int defaultMetode=decodeMetode(metodeDb);
       rgMetode.check(defaultMetode);
       
       Button simpan = (Button) dialogMetode.findViewById(R.id.btSimpan);
       Button batal= (Button) dialogMetode.findViewById(R.id.btBatal);
       dialogMetode.setTitle("Metode Perhitungan Waktu Shalat");
       dialogMetode.show();
       simpan.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	  int selectedId =encodeMetode(rgMetode.getCheckedRadioButtonId());
        	  ContentValues metode= new ContentValues();
   		   	  metode.put("value",String.valueOf(selectedId));
        	  dbAdapter.updateSetting(metode, dbAdapter.SETTING_METODE);
        	  settingAlarm();
        	  setData();
        	  dialogMetode.dismiss();
        	  Toast.makeText(getActivity(), "Pengaturan Metode tersimpan",Toast.LENGTH_SHORT).show();
           }
       });
       
       batal.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	   dialogMetode.dismiss();
           }
       });
    }
    
    private void dialogMetodeAshar() 
    {
       final Dialog dialogMetode = new Dialog(getActivity(),R.style.DialogSetting);
       dialogMetode.setContentView(R.layout.setting_metode_ashar);
       dialogMetode.setCancelable(true);
       final RadioGroup rgMetode=(RadioGroup) dialogMetode.findViewById(R.id.rgMetodeAshar);
       int metodeDb=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_METODE_ASHAR).getString(2));
       int defaultMetode=decodeMetodeAshar(metodeDb);
       rgMetode.check(defaultMetode);
       
       Button simpan = (Button) dialogMetode.findViewById(R.id.btSimpan);
       Button batal= (Button) dialogMetode.findViewById(R.id.btBatal);
       dialogMetode.setTitle("Metode Perhitungan Waktu Ashar");
       dialogMetode.show();
       simpan.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	  int selectedId =encodeMetodeAshar(rgMetode.getCheckedRadioButtonId());
        	  ContentValues metode= new ContentValues();
   		   	  metode.put("value",String.valueOf(selectedId));
        	  dbAdapter.updateSetting(metode, dbAdapter.SETTING_METODE_ASHAR);
        	  settingAlarm();
        	  setData();
        	  dialogMetode.dismiss();
        	  Toast.makeText(getActivity(), "Pengaturan Metode tersimpan",Toast.LENGTH_SHORT).show();
           }
       });
       
       batal.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	   dialogMetode.dismiss();
           }
       });
    }
    
    
    private void dialogNotifikasi() 
    {
       final Dialog dialogMetode = new Dialog(getActivity(),R.style.DialogSetting);
       dialogMetode.setContentView(R.layout.setting_notifikasi);
       dialogMetode.setCancelable(true);
       final Switch swSubuh=(Switch) dialogMetode.findViewById(R.id.swSubuh);
       final Switch swDzuhur=(Switch) dialogMetode.findViewById(R.id.swDzuhur);
       final Switch swAshar=(Switch) dialogMetode.findViewById(R.id.swAshar);
       final Switch swMaghrib=(Switch) dialogMetode.findViewById(R.id.swMaghrib);
       final Switch swIsya=(Switch) dialogMetode.findViewById(R.id.swIsya);
       
       int notifSubuh=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_NOTIFIKASI_SUBUH).getString(2));
       int notifDzuhur=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_NOTIFIKASI_DZUHUR).getString(2));
       int notifAshar=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_NOTIFIKASI_ASHAR).getString(2));
       int notifMaghrib=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_NOTIFIKASI_MAHRIB).getString(2));
       int notifIsya=Integer.parseInt(dbAdapter.getSettings(dbAdapter.SETTING_NOTIFIKASI_ISYA).getString(2));
       
       if(notifSubuh==1){
    	   swSubuh.setChecked(true);
       }
       if(notifDzuhur==1){
    	   swDzuhur.setChecked(true);
       }
       if(notifAshar==1){
    	   swAshar.setChecked(true);
       }
       if(notifMaghrib==1){
    	   swMaghrib.setChecked(true);
       }
       if(notifIsya==1){
    	   swIsya.setChecked(true);
       }
       
       Button simpan = (Button) dialogMetode.findViewById(R.id.btSimpan);
       Button batal= (Button) dialogMetode.findViewById(R.id.btBatal);
       dialogMetode.setTitle("Setting Adzan");
       dialogMetode.show();
       simpan.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	 
        	  ContentValues notifikasiSubuh= new ContentValues();
        	  String valueSubuh="0";
        	  if(swSubuh.isChecked()){
        		  valueSubuh="1";
        	  }
        	  notifikasiSubuh.put("value",valueSubuh);
        	  dbAdapter.updateSetting(notifikasiSubuh, dbAdapter.SETTING_NOTIFIKASI_SUBUH);
        	  
        	  ContentValues notifikasiDzuhur= new ContentValues();
        	  String valueDzuhur="0";
        	  if(swDzuhur.isChecked()){
        		  valueDzuhur="1";
        	  }
        	  notifikasiDzuhur.put("value",valueDzuhur);
        	  dbAdapter.updateSetting(notifikasiDzuhur, dbAdapter.SETTING_NOTIFIKASI_DZUHUR);
        	  
        	  ContentValues notifikasiAshar= new ContentValues();
        	  String valueAshar="0";
        	  if(swAshar.isChecked()){
        		  valueAshar="1";
        	  }
        	  notifikasiAshar.put("value",valueAshar);
        	  dbAdapter.updateSetting(notifikasiAshar,dbAdapter.SETTING_NOTIFIKASI_ASHAR);
        	  
        	  ContentValues notifikasiMaghrib= new ContentValues();
        	  String valueMaghrib="0";
        	  if(swMaghrib.isChecked()){
        		  valueMaghrib="1";
        	  }
        	  notifikasiMaghrib.put("value",valueMaghrib);
        	  dbAdapter.updateSetting(notifikasiMaghrib,dbAdapter.SETTING_NOTIFIKASI_MAHRIB);
        	  
        	  ContentValues notifikasiIsya= new ContentValues();
        	  String valueIsya="0";
        	  if(swIsya.isChecked()){
        		  valueIsya="1";
        	  }
        	  
        	  notifikasiIsya.put("value",valueIsya);
        	  dbAdapter.updateSetting(notifikasiIsya,dbAdapter.SETTING_NOTIFIKASI_ISYA);
      
        	  settingAlarm();
        	  setData();
        	  dialogMetode.dismiss();
        	  Toast.makeText(getActivity(), "Pengaturan Notifikasi tersimpan",Toast.LENGTH_SHORT).show();
           }
       });
       
       batal.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	   dialogMetode.dismiss();
           }
       });
    }
    
    
    private void dialogKoreksi() 
    {
       final Dialog dialogKoreksi = new Dialog(getActivity(),R.style.DialogSetting);
       dialogKoreksi.setContentView(R.layout.setting_koreksi);
       
       dialogKoreksi.setCancelable(true);
       double defaultKoreksiSubuh= Double.valueOf(dbAdapter.getSettings(14).getString(2))+60;
       double defaultKoreksiDzuhur= Double.valueOf(dbAdapter.getSettings(15).getString(2))+60;
       double defaultKoreksiAshar=Double.valueOf( dbAdapter.getSettings(16).getString(2))+60;
       double defaultKoreksiMaghrib=Double.valueOf( dbAdapter.getSettings(17).getString(2))+60;
       double defaultKoreksiIsya=Double.valueOf( dbAdapter.getSettings(18).getString(2))+60;
		
       SeekBar sbSubuh=(SeekBar) dialogKoreksi.findViewById(R.id.sbSubuh);
       SeekBar sbDzuhur=(SeekBar) dialogKoreksi.findViewById(R.id.sbDzuhur);
       SeekBar sbAshar=(SeekBar) dialogKoreksi.findViewById(R.id.sbAshar);
       SeekBar sbMaghrib=(SeekBar) dialogKoreksi.findViewById(R.id.sbMaghrib1);
       SeekBar sbIsya=(SeekBar) dialogKoreksi.findViewById(R.id.sbIsya);
       final TextView valueSubuh=(TextView) dialogKoreksi.findViewById(R.id.valueSubuh);
       final TextView valueDzuhur=(TextView) dialogKoreksi.findViewById(R.id.valueDzuhur);
       final TextView valueAshar=(TextView) dialogKoreksi.findViewById(R.id.valueAshar);
       final TextView valueMaghrib=(TextView) dialogKoreksi.findViewById(R.id.valueMaghrib);
       final TextView valueIsya=(TextView) dialogKoreksi.findViewById(R.id. valueIsya);
	   valueSubuh.setText(String.valueOf(defaultKoreksiSubuh-60));
	   valueDzuhur.setText(String.valueOf(defaultKoreksiDzuhur-60)); 
	   valueAshar.setText(String.valueOf(defaultKoreksiAshar-60)); 
	   valueMaghrib.setText(String.valueOf(defaultKoreksiMaghrib-60)); 
	   valueIsya.setText(String.valueOf(defaultKoreksiIsya-60)); 
	    
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
       
       Button simpan = (Button) dialogKoreksi.findViewById(R.id.btSimpan);
       Button batal= (Button) dialogKoreksi.findViewById(R.id.btBatal);
       dialogKoreksi.setTitle("Koreksi Waktu (Menit)");
       dialogKoreksi.show();
       simpan.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
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
        	settingAlarm();
        	setData();
        	dialogKoreksi.dismiss();
        	Toast.makeText(getActivity(), "Pengaturan koreksi tersimpan",Toast.LENGTH_SHORT).show();
           }
       });
       
       batal.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	   dialogKoreksi.dismiss();
           }
       });
    }
    
    
   private void dialogZakat() 
    {
       final Dialog dialogZakat= new Dialog(getActivity(),R.style.DialogSetting);
       dialogZakat.setContentView(R.layout.setting_zakat);
       dialogZakat.setTitle("Perhitungan Zakat (Rupiah)");
       /*
       int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");
	   	View titleDivider = dialogZakat.findViewById(titleDividerId);
	   	if (titleDivider != null)
	   	titleDivider.setBackgroundColor(getResources().getColor(R.color.green));
	   */
        dialogZakat.setCancelable(true);
      
       final EditText etEmas=(EditText) dialogZakat.findViewById(R.id.etEmas);
       String defaultEmas=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_EMAS).getString(2);
       etEmas.setBackgroundResource(R.drawable.edittext);
       etEmas.setText(defaultEmas);
       final EditText etPerak=(EditText) dialogZakat.findViewById(R.id.etPerak);
       etPerak.setBackgroundResource(R.drawable.edittext);
       String defaultPerak=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_PERAK).getString(2);
       etPerak.setText(defaultPerak);
       final EditText etPokok=(EditText) dialogZakat.findViewById(R.id.etPokok);
       etPokok.setBackgroundResource(R.drawable.edittext);
       String defaultPokok=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_BAHAN_POKOK).getString(2);
       etPokok.setText(defaultPokok);
       
       Button simpan = (Button) dialogZakat.findViewById(R.id.btSimpan);
       Button batal= (Button) dialogZakat.findViewById(R.id.btBatal);
       
       dialogZakat.show();
       simpan.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
      
        	  ContentValues valueEmas= new ContentValues();
        	  valueEmas.put("value",etEmas.getText().toString());
	      	  dbAdapter.updateSetting(valueEmas,dbAdapter.SETTING_HARGA_EMAS);
	      	  
	      	  ContentValues valuePerak= new ContentValues();
	      	  valuePerak.put("value",etPerak.getText().toString());
	      	  dbAdapter.updateSetting(valuePerak,dbAdapter.SETTING_HARGA_PERAK);
	      	  
	      	  ContentValues valuePokok= new ContentValues();
	      	  valuePokok.put("value",etPokok.getText().toString());
	      	  dbAdapter.updateSetting(valuePokok,dbAdapter.SETTING_HARGA_BAHAN_POKOK);
	      	  
        	  setData();
        	  dialogZakat.dismiss();
        	  Toast.makeText(getActivity(), "Pengaturan Zakat tersimpan",Toast.LENGTH_SHORT).show();
           }
       });
       
       batal.setOnClickListener(new OnClickListener()
       {

           @Override
           public void onClick(View v)
           {
        	   dialogZakat.dismiss();
           }
       });
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
	
	public void settingAlarm(){
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
	  	}else{
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
	  	}else{
	  		stopAlarm(3);
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
	}
}
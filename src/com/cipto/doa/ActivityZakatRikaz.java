package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityZakatRikaz extends Activity {
	EditText etNilaiTemuan;
	EditText etHargaEmas;
	double nishab=85;
	Button btHitung;
	Dialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_rikaz);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		
		DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
	    String defaultEmas=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_EMAS).getString(2);
	    
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
		ab.setTitle("Zakar Rikaz");
		etNilaiTemuan=(EditText) findViewById(R.id.etNilai);
		etNilaiTemuan.setBackgroundResource(R.drawable.edittext);
		etHargaEmas=(EditText) findViewById(R.id.etEmas);  
		etHargaEmas.setBackgroundResource(R.drawable.edittext);
		etHargaEmas.setText(defaultEmas);
		
		btHitung=(Button) findViewById(R.id.btHitung);
		alertDialog= new Dialog(this,R.style.DialogSetting);
	    alertDialog.setContentView(R.layout.dialog_custom);
	    final TextView etMessage=(TextView)  alertDialog.findViewById(R.id.dialog_text);
	    Button btOk=(Button) alertDialog.findViewById(R.id.btOK);
	    btOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
            	 alertDialog.dismiss();
            }
        });
		alertDialog.setTitle("Hasil Perhitungan");
		btHitung.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				double nilaiTemuan=0;
				double hargaEmas=0;
				double nilaiNishab;
				double zakat=0;
				String message;
				if(etNilaiTemuan.getText().toString().trim().length() > 0){
					nilaiTemuan=Double.valueOf(etNilaiTemuan.getText().toString());
				}
				if(etHargaEmas.getText().toString().trim().length() > 0){
					hargaEmas=Double.valueOf(etHargaEmas.getText().toString());
				}
				nilaiNishab=nishab*hargaEmas;
				if(nilaiTemuan > nilaiNishab){
					zakat=20*nilaiTemuan/100;
					message="Zakat Rikaz = "+zakat;
				}else{
					message="Nilai temuan kurang dari nishab.";
				}
				etMessage.setText(message);
				alertDialog.show();
			}
			
		});
		    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) { 
  	  switch (item.getItemId()) {
  	  	    case android.R.id.home:
  	    	Intent intentHome= new Intent(ActivityZakatRikaz.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

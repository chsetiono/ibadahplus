package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ActivityZakatRikaz extends Activity {
	EditText etNilaiTemuan;
	EditText etHargaEmas;
	double nishab=85;
	Button btHitung;
	AlertDialog.Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_rikaz);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
		ab.setTitle("Zakar Rikaz");
		etNilaiTemuan=(EditText) findViewById(R.id.etNilai);
		etHargaEmas=(EditText) findViewById(R.id.etEmas);  
		btHitung=(Button) findViewById(R.id.btHitung);
		alertDialog= new AlertDialog.Builder(this);
		
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
				alertDialog.setMessage(message);
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

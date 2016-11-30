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
import android.widget.TextView;


public class ActivityZakatFidyah extends Activity {
	TextView etBiaya;
	TextView etHari;
	double biaya;
	double hari;
	Button btHitung;
	Dialog alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_fidyah);
		ActionBar ab = getActionBar(); 
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Fidyah");
	    
	    etBiaya=(TextView) findViewById(R.id.etBiaya);
	    etBiaya.setBackgroundResource(R.drawable.edittext);
	    etHari=(TextView) findViewById(R.id.etHari);
	    etHari.setBackgroundResource(R.drawable.edittext);
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
	    btHitung.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					double biaya=0;
					double hari=0;
					double jumlah;
					
				
					if(etBiaya.getText().toString().trim().length() > 0){
						biaya=Double.valueOf(etBiaya.getText().toString());
					}
					
					if(etHari.getText().toString().trim().length() > 0){
						hari=Double.valueOf(etHari.getText().toString());
					}
				
					jumlah=biaya*hari;
					
					etMessage.setText("Fidyah yang harus anda bayar adalah Rp. "+String.valueOf(jumlah));
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
  	    	Intent intentHome= new Intent(ActivityZakatFidyah.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

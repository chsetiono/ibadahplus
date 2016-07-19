package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityZakatFidyah extends Activity {
	TextView etBiaya;
	TextView etHari;
	double biaya;
	double hari;
	Button btHitung;
	AlertDialog.Builder alertDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_fidyah);
		ActionBar ab = getActionBar(); 
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Fidyah");
	    
	    etBiaya=(TextView) findViewById(R.id.etBiaya);
	    etHari=(TextView) findViewById(R.id.etHari);
	    btHitung=(Button) findViewById(R.id.btHitung);
	    alertDialog= new AlertDialog.Builder(this);
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
					
					alertDialog.setMessage("Fidyah yang harus anda bayar adalah Rp. "+String.valueOf(jumlah));
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
	  String id_surah= getIntent().getStringExtra("id");  
  	  switch (item.getItemId()) {
  	  	    case android.R.id.home:
  	    	Intent intentHome= new Intent(ActivityZakatFidyah.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

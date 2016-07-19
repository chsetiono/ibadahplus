package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
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

public class ActivityZakatFitrah extends Activity {
	  TextView jumlah;
	  TextView harga;
	  TextView hasil;
	  double zakat;
	  double total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_fitrah);
		ActionBar ab = getActionBar(); 
	    ab.setDisplayHomeAsUpEnabled(true);
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Kalkulator Zakat");
	    
	    jumlah=(TextView) findViewById(R.id.etJumlah);
	    harga=(TextView) findViewById(R.id.etHarga);
	    final Button btHitung=(Button) findViewById(R.id.btHitung);
	    hasil=(TextView) findViewById(R.id.tvResult);
	  
	    btHitung.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(jumlah.getText().toString().trim().length() == 0){
					Toast.makeText(getApplicationContext(), "mohon isi jumlah jiwa", Toast.LENGTH_LONG).show();
				}else if(harga.getText().toString().trim().length() == 0){
					Toast.makeText(getApplicationContext(), "mohon isi harga baha pokok per kg", Toast.LENGTH_LONG).show();
				}else{
					zakat= Double.valueOf(jumlah.getText().toString())*2.5;
					total=zakat*Double.valueOf(harga.getText().toString());
					hasil.setText("Zakat yang harus anda bayar adalah "+ String.valueOf(zakat)+ " Kg atau uang senilai "+String.valueOf(total));
					
				}
				
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
  	    	Intent intentHome= new Intent(ActivityZakatFitrah.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

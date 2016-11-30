package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityZakatFitrah extends Activity {
	  EditText jumlah;
	  EditText harga;
	  TextView hasil;
	  double zakat;
	  double total;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_fitrah);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Fitrah");
	    
	    DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
	    String defaultPokok=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_BAHAN_POKOK).getString(2);
	    
	    jumlah=(EditText) findViewById(R.id.etJumlah);
	    jumlah.setBackgroundResource(R.drawable.edittext);
	    harga=(EditText) findViewById(R.id.etHarga);
	    harga.setBackgroundResource(R.drawable.edittext);
	    harga.setText(defaultPokok);
	    final Button btHitung=(Button) findViewById(R.id.btHitung);
	    hasil=(TextView) findViewById(R.id.tvResult);
	    final Dialog dialog=new Dialog (this,R.style.DialogSetting);
	    dialog.setContentView(R.layout.dialog_custom);
	    final TextView etMessage=(TextView)  dialog.findViewById(R.id.dialog_text);
	    Button btOk=(Button) dialog.findViewById(R.id.btOK);
	    btOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
            	 dialog.dismiss();
            }
        });
	    dialog.setTitle("Hasil Perhitungan");
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
					//hasil.setText("Zakat yang harus anda bayar adalah "+ String.valueOf(zakat)+ " Kg atau uang senilai "+String.valueOf(total));
					etMessage.setText("Zakat yang harus anda bayar adalah "+String.valueOf(zakat)+ " Kg atau uang senilai "+String.valueOf(total));
					dialog.show();
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

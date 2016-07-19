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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityZakatPerdagangan extends Activity {
	EditText etNilaiBarang;
	EditText etUang;
	EditText etPiutang;
	EditText etHutang;
	EditText etPengeluaranLain;
	EditText etEmas;

	double nishab=85;
	Button btHitung;
	AlertDialog.Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_perdagangan);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
		ab.setTitle("Zakar Rikaz");
		etNilaiBarang=(EditText) findViewById(R.id.etNilaiBarang);
		etUang=(EditText) findViewById(R.id.etUang);
		etPiutang=(EditText) findViewById(R.id.etPiutang);
		etHutang=(EditText) findViewById(R.id.etHutang); 
		etPengeluaranLain=(EditText) findViewById(R.id.etPengeluaran); 
		etEmas=(EditText) findViewById(R.id.etEmas); 
		btHitung=(Button) findViewById(R.id.btHitung);
		alertDialog= new AlertDialog.Builder(this);
		alertDialog= new AlertDialog.Builder(this);
		btHitung.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					double nilaiBarang=0;
					double uang=0;
					double piutang=0;
					double hutang=0;
					double pengeluaranLain=0;
					double total_uang;
					double total_pengeluaran;
					double emas=0;
					double netto;
					double nishab;
					double zakat;
					String message;
				
					if(etNilaiBarang.getText().toString().trim().length() > 0){
						nilaiBarang=Double.valueOf(etNilaiBarang.getText().toString());
					}
					if(etUang.getText().toString().trim().length() > 0){
						uang=Double.valueOf(etUang.getText().toString());
					}
					
					
					if(etPiutang.getText().toString().trim().length() > 0){
						piutang=Double.valueOf(etPiutang.getText().toString());
					}
					if(etHutang.getText().toString().trim().length() > 0){
						hutang=Double.valueOf(etHutang.getText().toString());
					}
					
					if(etPengeluaranLain.getText().toString().trim().length() > 0){
						pengeluaranLain=Double.valueOf(etPengeluaranLain.getText().toString());
					}
					
					if(etEmas.getText().toString().trim().length() > 0){
						emas=Double.valueOf(etEmas.getText().toString());
					}
					
					total_uang=nilaiBarang+uang+piutang;
					total_pengeluaran=hutang+pengeluaranLain;
					netto=total_uang-total_pengeluaran;
					nishab=emas*835;

					if(netto > nishab){
						zakat=2.5/100*netto;
						message="Zakat perdagangan = Rp. "+zakat;
					}else{
						message="Nilai Perdagangan dibawah nishab";
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
  	    	Intent intentHome= new Intent(ActivityZakatPerdagangan.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

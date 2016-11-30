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

public class ActivityZakatPerdagangan extends Activity {
	EditText etNilaiBarang;
	EditText etUang;
	EditText etPiutang;
	EditText etHutang;
	EditText etPengeluaranLain;
	EditText etEmas;

	double nishab=85;
	Button btHitung;
	Dialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_perdagangan);
		ActionBar ab = getActionBar(); 
		   // ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
		ab.setTitle("Zakar Perdagangan");

	    DBAdapter dbAdapter=new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
	    String defaultEmas=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_EMAS).getString(2);
	    
		etNilaiBarang=(EditText) findViewById(R.id.etNilaiBarang);
		etNilaiBarang.setBackgroundResource(R.drawable.edittext);
		etUang=(EditText) findViewById(R.id.etUang);
		etUang.setBackgroundResource(R.drawable.edittext);
		etPiutang=(EditText) findViewById(R.id.etPiutang);
		etPiutang.setBackgroundResource(R.drawable.edittext);
		etHutang=(EditText) findViewById(R.id.etHutang); 
		etHutang.setBackgroundResource(R.drawable.edittext);
		etPengeluaranLain=(EditText) findViewById(R.id.etPengeluaran); 
		etPengeluaranLain.setBackgroundResource(R.drawable.edittext);
		etEmas=(EditText) findViewById(R.id.etEmas); 
		etEmas.setBackgroundResource(R.drawable.edittext);
		etEmas.setText(defaultEmas);
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
  	    	Intent intentHome= new Intent(ActivityZakatPerdagangan.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

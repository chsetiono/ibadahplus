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

public class ActivityZakatEmas extends Activity {
	double nishabEmas=85;
	double nishabPerak=595;
	double zakatGram;
	double zakatRupiah;
	EditText etJumlahEmas;
	EditText etJumlahPerak;
	EditText etHargaEmas;
	EditText etHargaPerak;
	Button btHitung;
	
	DBAdapter dbAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_emas);
		ActionBar ab = getActionBar(); 
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Emas dan Perak");
	    dbAdapter=new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
	    String defaultEmas=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_EMAS).getString(2);
	    String defaultPerak=dbAdapter.getSettings(DBAdapter.SETTING_HARGA_PERAK).getString(2);
	    etJumlahEmas=(EditText) findViewById(R.id.etJumlahEmas);
	    etJumlahEmas.setBackgroundResource(R.drawable.edittext);
	    
	    etJumlahPerak=(EditText) findViewById(R.id.etJumlahPerak);
	    etJumlahPerak.setBackgroundResource(R.drawable.edittext);
	    
	    etHargaEmas=(EditText) findViewById(R.id.etHargaEmas);
	    etHargaEmas.setBackgroundResource(R.drawable.edittext);
	    etHargaEmas.setText(defaultEmas);
	    
	    etHargaPerak=(EditText) findViewById(R.id.etHargaPerak);
	    etHargaPerak.setBackgroundResource(R.drawable.edittext);
	    etHargaPerak.setText(defaultPerak);
	    
	    btHitung=(Button) findViewById(R.id.btHitung);
	    final Dialog alertDialog= new Dialog(this,R.style.DialogSetting);
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
					double jumlahEmas=0;
					double jumlahPerak=0;
					double hargaEmas=0;
					double hargaPerak=0;
					double zakatEmas;
					double zakatPerak;
					String message;
					
					if(etJumlahEmas.getText().toString().trim().length() > 0){
						jumlahEmas=Double.valueOf(etJumlahEmas.getText().toString());
					}
					if(etJumlahPerak.getText().toString().trim().length() > 0){
						jumlahPerak=Double.valueOf(etJumlahPerak.getText().toString());
					}
					
					if(etHargaEmas.getText().toString().trim().length() > 0){
						hargaEmas=Double.valueOf(etHargaEmas.getText().toString());
					}
					
					
					if(etHargaPerak.getText().toString().trim().length() > 0){
						hargaPerak=Double.valueOf(etHargaPerak.getText().toString());
					}
					
					if(jumlahEmas > nishabEmas){
						zakatEmas=2.5/100*jumlahEmas;
						message="Zakat Emas = "+zakatEmas+" gram atau Rp."+String.valueOf(zakatEmas*hargaEmas)+".";
					}else{
						message="Jumlah emas kurang dari nishab, Anda tidak diwajibkan membayar zakat Emas.";
					}
					
					if(jumlahPerak > nishabPerak){
						zakatPerak=2.5/100*jumlahPerak;
						message=message+"Zakat Perak = "+zakatPerak+" gram atau Rp."+String.valueOf(zakatPerak*hargaPerak);
					}else{
						message=message+"Jumlah perak kurang dari nishab, Anda tidak diwajibkan membayar zakat Perak.";
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
  	    	Intent intentHome= new Intent(ActivityZakatEmas.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}

}

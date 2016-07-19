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

public class ActivityZakatEmas extends Activity {
	double nishabEmas=85;
	double nishabPerak=595;
	double zakatGram;
	double zakatRupiah;
	TextView etJumlahEmas;
	TextView etJumlahPerak;
	TextView etHargaEmas;
	TextView etHargaPerak;
	Button btHitung;
	AlertDialog.Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_emas);
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Emas dan Perak");
	    
	    etJumlahEmas=(TextView) findViewById(R.id.etJumlahEmas);
	    etJumlahPerak=(TextView) findViewById(R.id.etJumlahPerak);
	    etHargaEmas=(TextView) findViewById(R.id.etHargaEmas);
	    etHargaPerak=(TextView) findViewById(R.id.etHargaPerak);
	   
	    btHitung=(Button) findViewById(R.id.btHitung);
	    alertDialog= new AlertDialog.Builder(this);
	    
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
  	    	Intent intentHome= new Intent(ActivityZakatEmas.this,Main.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

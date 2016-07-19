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
import android.widget.TextView;
import android.widget.Toast;

public class ActivityZakatProfesi extends Activity {
	 TextView gaji;
	 TextView pendapatanLain;
	 TextView pengeluaranRutin;
	 TextView pengeluaranLain;
	 TextView hargaEmas;
	 double total;
	  
	 AlertDialog.Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_profesi);
		ActionBar ab = getActionBar(); 
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Profesi");
	    
	    
	    gaji=(TextView) findViewById(R.id.etGaji);
	    pendapatanLain=(TextView) findViewById(R.id.etPendapatanLain);
	    pengeluaranRutin=(TextView) findViewById(R.id.etPengeluaranRutin);
	    pengeluaranLain=(TextView) findViewById(R.id.etPengeluaranLain);
	    hargaEmas=(TextView) findViewById(R.id.etEmas);
	    final Button btHitung=(Button) findViewById(R.id.btHitung);
	    alertDialog= new AlertDialog.Builder(this);
	  
	    
	    btHitung.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					double jumlah_gaji=0;
					double pendapatan_lain=0;
					double pengeluaran_rutin=0;
					double pengeluaran_lain=0;
					double harga_emas=0;
					String message;
					if(gaji.getText().toString().trim().length() > 0){
						jumlah_gaji=Double.valueOf(gaji.getText().toString());
					}
					if(pendapatanLain.getText().toString().trim().length() > 0){
						pendapatan_lain=Double.valueOf(pendapatanLain.getText().toString());
					}
					if(pengeluaranRutin.getText().toString().trim().length() > 0){
						pengeluaran_rutin=Double.valueOf(pengeluaranRutin.getText().toString());
					}
					
					if(pengeluaranLain.getText().toString().trim().length() > 0){
						pengeluaran_lain=Double.valueOf(pengeluaranLain.getText().toString());
					}
					
					if(hargaEmas.getText().toString().trim().length() > 0){
						harga_emas=Double.valueOf(hargaEmas.getText().toString())*85;
					}
					
					if (harga_emas==0){
						Toast.makeText(getApplicationContext(),"Harga Emas belum diisi",Toast.LENGTH_LONG).show();
						
					}else{
						  double netto=(jumlah_gaji*12+pendapatan_lain)-(pengeluaran_rutin*12-pengeluaran_lain);
						  double zakat= 12.5/100*netto;
						  if(zakat > harga_emas){
							  message ="nisab = Rp. "+String.valueOf(harga_emas) + "Zakat = Rp."+String.valueOf(zakat);
						  }else{
							  message = "anda tidak diwajibkan membayar zakat karena penghasilan kuran dari nisab";
						  }
						  alertDialog.setMessage(message);
						  alertDialog.show();
						  
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
  	  switch (item.getItemId()) {
  	  	    case android.R.id.home:
  	    	Intent intentHome= new Intent(ActivityZakatProfesi.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

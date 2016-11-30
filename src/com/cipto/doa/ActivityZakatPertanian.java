package com.cipto.doa;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityZakatPertanian extends Activity {
	double nishab;
	CheckBox cbBiaya;
	EditText etJumlahPanen;
	CheckBox cbJenis;
	Button btHitung;
	Dialog alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_pertanian);
		ActionBar ab = getActionBar(); 
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Pertanian");
	    cbBiaya=(CheckBox) findViewById(R.id.cbBiaya);
	    etJumlahPanen=(EditText)findViewById(R.id.etJumlahPanen);
	    etJumlahPanen.setBackgroundResource(R.drawable.edittext);
	    cbJenis=(CheckBox) findViewById(R.id.cbJenis);
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
					double jumlahPanen=0;
					double zakat;
				
					String message="";
					double pengali;
					
					if(cbBiaya.isChecked()){
						pengali=10;
					}else{
						pengali=5;
					}

					
					if(cbJenis.isChecked()){
						nishab=520;
					}else{
						nishab=653;
					}
					if(etJumlahPanen.getText().toString().trim().length() > 0){
						jumlahPanen=Double.valueOf(etJumlahPanen.getText().toString());
					}
					
					
					
					if(jumlahPanen > nishab){
						zakat=pengali/100*jumlahPanen;
						message="Zakat Pertanina= "+zakat+" Kg";
					}else{
						message="Hasil pertanian kurang dari nisab";
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
  	    	Intent intentHome= new Intent(ActivityZakatPertanian.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

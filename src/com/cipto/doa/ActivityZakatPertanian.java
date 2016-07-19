package com.cipto.doa;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivityZakatPertanian extends Activity {
	double nishab;
	Spinner spPengairan;
	EditText etJumlahPanen;
	CheckBox cbJenis;
	Button btHitung;
	AlertDialog.Builder alertDialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat_pertanian);
		ActionBar ab = getActionBar(); 
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Zakat Pertanian");
	    spPengairan=(Spinner) findViewById(R.id.spPengairan);
	    etJumlahPanen=(EditText)findViewById(R.id.etJumlahPanen);
	    cbJenis=(CheckBox) findViewById(R.id.cbJenis);
	    btHitung=(Button) findViewById(R.id.btHitung);
	    
	    List<String> listPengairan= new ArrayList<String>();
	    listPengairan.add("Dengan Biaya");
	    listPengairan.add("Tanpa Biaya");
	    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listPengairan);		   
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spPengairan.setAdapter(dataAdapter);
	    alertDialog= new AlertDialog.Builder(this);
 btHitung.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
					double jumlahPanen=0;
					double zakat;
				
					String message="";
					double pengali;
					String pengairan=spPengairan.getSelectedItem().toString();
					
					if(cbJenis.isChecked()){
						nishab=520;
					}else{
						nishab=653;
					}
					if(etJumlahPanen.getText().toString().trim().length() > 0){
						jumlahPanen=Double.valueOf(etJumlahPanen.getText().toString());
					}
					
					if(pengairan.equals("Tanpa Biaya")){
						pengali=10;
					}else{
						pengali=5;
					}
					
					if(jumlahPanen > nishab){
						zakat=pengali/100*jumlahPanen;
						message="Zakat Pertanina= "+zakat+" Kg";
					}else{
						message="Hasil pertanian kurang dari nisab";
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
  	    	Intent intentHome= new Intent(ActivityZakatPertanian.this,ActivityZakat.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

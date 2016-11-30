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
import android.widget.Toast;

public class ActivityZakat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zakat);
		ActionBar ab = getActionBar(); 
	   // ab.setDisplayHomeAsUpEnabled(true);
	    ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Kalkulator Zakat");
		final ListView lvDzikir=(ListView) findViewById(R.id.lvDzikir);
		String[] values=new String[] {"Zakat Fitrah","Zakat Profesi","Zakat Fidyah","Zakat Emas dan Perak","Zakat Pertanian","Zakat Rikaz (Barang Temuan)","Zakat Perdagangan"};
		ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_dzikir,values);
		lvDzikir.setAdapter(adapter);
		lvDzikir.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated methoActivityZakatProfesi.javad stub
	                
                Intent intent;
                switch(position){
                	case 0:
                		intent=new Intent(ActivityZakat.this, ActivityZakatFitrah.class);
                		startActivity(intent);
                	break;
                	case 1:
                		intent=new Intent(ActivityZakat.this, ActivityZakatProfesi.class);
                		startActivity(intent);
                	break;
                	case 2:
                		intent=new Intent(ActivityZakat.this, ActivityZakatFidyah.class);
                		startActivity(intent);
                	break;
                	case 3:
                		intent=new Intent(ActivityZakat.this, ActivityZakatEmas.class);
                		startActivity(intent);
                	break;
                	case 4:
                		intent=new Intent(ActivityZakat.this, ActivityZakatPertanian.class);
                		startActivity(intent);
                	break;
                	case 5:
                		intent=new Intent(ActivityZakat.this, ActivityZakatRikaz.class);
                		startActivity(intent);
                	break;
                	
                	case 6:
                		intent=new Intent(ActivityZakat.this, ActivityZakatPerdagangan.class);
                		startActivity(intent);
                	break;
                	
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
  	    	Intent intentHome= new Intent(ActivityZakat.this,Main.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

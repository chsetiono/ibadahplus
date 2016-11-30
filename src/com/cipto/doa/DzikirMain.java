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

public class DzikirMain extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dzikir_main);
		ActionBar ab = getActionBar(); 
		ab.setHomeButtonEnabled(true);
	    ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
	    ab.setTitle("Dzikir");
		final ListView lvDzikir=(ListView) findViewById(R.id.lvDzikir);
		String[] values=new String[] {"Istighfar","Tahmid","Tasbih","Takbir","Tahlil"};
		ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_dzikir,values);
	
		lvDzikir.setAdapter(adapter);
		lvDzikir.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				// TODO Auto-generated method stub
	                
                Intent intent;
               
             
                switch(position){
                	case 0:
                		intent=new Intent(DzikirMain.this, Istigfar.class);
                		startActivity(intent);
                	break;
                	case 1:
                		intent=new Intent(DzikirMain.this, Tahmid.class);
                		startActivity(intent);
                	break;
                	case 2:
                		intent=new Intent(DzikirMain.this, Tasbih.class);
                		startActivity(intent);
                	break;
                	case 3:
                		intent=new Intent(DzikirMain.this, Takbir.class);
                		startActivity(intent);
                	break;
                	case 4:
                		intent=new Intent(DzikirMain.this, Tahlil.class);
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
	   // inflater.inflate(R.menu.menu_display_surah, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
  	  switch (item.getItemId()) {
  	  	    case android.R.id.home:
  	    	Intent intentHome= new Intent(DzikirMain.this,Main.class);
  	  		startActivity(intentHome);
          return true;
  	  }
  	  return true;
  	}


}

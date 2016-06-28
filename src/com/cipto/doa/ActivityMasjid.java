package com.cipto.doa;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;

public class ActivityMasjid extends Activity {
	final String GOOGLE_KEY = "AIzaSyAu24MS8S5gD95Mb1xsecp-t2kIm7ynlaE";
	final String latitude = "40.7463956";
	final String longtitude = "-73.9852992";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masjid);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_masjid, menu);
		return true;
	}

}

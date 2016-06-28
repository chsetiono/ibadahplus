package com.cipto.doa;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Kiblat extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kiblat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.kiblat, menu);
		return true;
	}

}

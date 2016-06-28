package com.cipto.doa;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.res.Resources;
import android.graphics.*;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Config;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
public class ActivityKiblat extends Activity {

	private static final String TAG = "Compass";
	   
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SampleView kiblat;
    private float[] mValues;
    private double lonMosque;
       private double latMosque;
       private LocationManager lm;
    private LocationListener locListenD;

    //for finding north direction
    private final SensorEventListener mListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            if (Config.DEBUG) Log.d(TAG,
                     "sensorChanged (" + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + ")");
            mValues = event.values;
            if (kiblat != null) {
                kiblat.invalidate();
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

       @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        kiblat = new SampleView(this);
        setContentView(kiblat);
        ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
        // for calling the gps
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location loc = lm.getLastKnownLocation("gps");
       
     // ask the Location Manager to send us location updates
        locListenD = new DispLocListener();
        lm.requestLocationUpdates("gps", 30000L, 10.0f, locListenD);
       
        locListenD = new DispLocListener();
        lm.requestLocationUpdates("gps", 30000L, 10.0f, locListenD);       
       }     
  
       //finding kabah location
    private double QiblaCount(double lngMasjid, double latMasjid) {
       double lngKabah=39.82616111;
              double latKabah=21.42250833;
              double lKlM=(lngKabah-lngMasjid);
              double sinLKLM= Math.sin(lKlM*2.0*Math.PI/360);
              double cosLKLM= Math.cos(lKlM*2.0*Math.PI/360);
              double sinLM = Math.sin(latMasjid *2.0 * Math.PI/360);
              double cosLM =  Math.cos(latMasjid *2.0 * Math.PI/360);
              double tanLK = Math.tan(latKabah*2*Math.PI/360);
              double denominator= (cosLM*tanLK)-sinLM*cosLKLM;
             
              double Qibla;
              double direction;
             
              Qibla = Math.atan2(sinLKLM, denominator)*180/Math.PI;
              direction= Qibla<0 ? Qibla+360 : Qibla;
              return direction;
      
       }

    //resume location update when we are resume
       @Override
    protected void onResume()
    {
        if (Config.DEBUG) Log.d(TAG, "onResume");
        super.onResume();

        mSensorManager.registerListener(mListener, mSensor,
                SensorManager.SENSOR_DELAY_GAME);
    }

       //stop location update when we are stop
    @Override
    protected void onStop()
    {
        if (Config.DEBUG) Log.d(TAG, "onStop");
        mSensorManager.unregisterListener(mListener);
        super.onStop();
    }

    private class SampleView extends View {
        private Paint   mPaint = new Paint();
        private Path    mPath = new Path();
        private boolean mAnimate;
             

        public SampleView(Context context) {
            super(context);

            // Construct a wedge-shaped path
            mPath.moveTo(0, -100);
            mPath.lineTo(20, 120);
            mPath.lineTo(0, 100);
            mPath.lineTo(-20, 120);
            mPath.close();
        }

        //make an arrow for pointing direction
       
        protected void onDraw(Canvas canvas) {
            Paint paint = mPaint;
           
           this.setBackgroundResource(R.drawable.bg_kompas);
        
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            int w = canvas.getWidth();
            int h = canvas.getHeight();
            int cx = w / 2;
            int cy = h / 2;
           float Qibla=(float) QiblaCount(lonMosque,latMosque);
         //   float Qiblat = mValues [0] + Qibla;
            canvas.translate(cx, cy);
            if (mValues != null) {
                canvas.rotate(-(mValues[0]+ Qibla));
            }
            canvas.drawPath(mPath, mPaint);
        }

        @Override
        protected void onAttachedToWindow() {
            mAnimate = true;
            if (Config.DEBUG) Log.d(TAG, "onAttachedToWindow. mAnimate=" + mAnimate);
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimate = false;
            if (Config.DEBUG) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
            super.onDetachedFromWindow();
        }
   
            }
   
    private class DispLocListener implements LocationListener {
       public void onLocationChanged(Location loc) {
       // update TextViews
        latMosque = loc.getLatitude();
        lonMosque = loc.getLongitude();
       }
      
       public void onProviderDisabled(String provider) {
       }
      
       public void onProviderEnabled(String provider) {
       }
      
       public void onStatusChanged(String provider, int status, Bundle extras) {
       }
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.activity_kiblat, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
  	  switch (item.getItemId()) {
  	    case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent home= new Intent(ActivityKiblat.this, Main.class);
		 startActivity(home);  
         return true;
  	  }
  	  return true;
  	}
}
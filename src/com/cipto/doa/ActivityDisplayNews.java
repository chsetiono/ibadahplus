package com.cipto.doa;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.webkit.WebView;
import android.widget.ListView;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityDisplayNews extends Activity {
	DBAdapter dbAdapter;
	TextView judul;
	TextView isi;
	TextView tanggal;
	TextView sumber;
	ImageView image;
	
	private WebView webView;
	private final static String url = "http://www.ppiln.or.id/apitest.php?data=1";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_news);
		
		Intent intent = getIntent();
		final String id=intent.getStringExtra("id");
		final String judulVal=intent.getStringExtra("judul");
		final String tanggalVal=intent.getStringExtra("tanggal");
		final String isiVal=intent.getStringExtra("isi");
		final String imageUrl=intent.getStringExtra("imageUrl");
		final String sumberVal=intent.getStringExtra("sumber");
		
		
		
		judul=(TextView) findViewById(R.id.judulNews);
		tanggal=(TextView) findViewById(R.id.tanggal);
		image=(ImageView) findViewById(R.id.imageNews);
		isi=(TextView) findViewById(R.id.isiNews);
		sumber=(TextView) findViewById(R.id.sumber);
		judul.setText(judulVal);
		tanggal.setText(tanggalVal);
		isi.setText(isiVal);
		sumber.setText("Sumber : "+sumberVal);
		Picasso.with(this).load(imageUrl).fit().into(image);
		
	    dbAdapter = new DBAdapter(getApplicationContext());
	    dbAdapter.openDataBase();
		ActionBar ab = getActionBar(); 
		// ab.setDisplayHomeAsUpEnabled(true);
		ab.setHomeButtonEnabled(true);
		ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
		
	    //new ExecuteTask(this, url).execute();
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu_display_news, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	  String id_sunnah= getIntent().getStringExtra("id");  
  	  switch (item.getItemId()) {
  	  	case R.id.bookmark:
  	  		Intent intent = getIntent();
  			addBookmark(intent.getStringExtra("id"));
	      break;
  	    case android.R.id.home:
          // app icon in action bar clicked; go home
      	 Intent listDoa = new Intent(ActivityDisplayNews.this, ActivityNews.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
          return true;
  	  }
  	  return true;
  	}
	
	
	
	
	public void addBookmark(String id){
		 ContentValues values = new ContentValues();
		 values.put("tipe","5");
 	     values.put("id_item",id);
 	     dbAdapter.insertBookmark(values);
 	     dbAdapter.close();
 	     Toast.makeText(ActivityDisplayNews.this, "ditambahkan ke bookmark", Toast.LENGTH_SHORT).show();
	}
	
	private class ExecuteTask extends AsyncTask<Void, Void, String> {

		 private String url;
		 private ProgressDialog dialog;
	
		 public ExecuteTask(Activity activity, String url) {
		  super();
		  this.url = url;
		 }
		 
		 @Override
		 protected void onPreExecute() {
		  super.onPreExecute();
		  // Create a progress dialog
		  dialog = new ProgressDialog(ActivityDisplayNews.this); 
		  // Set progress dialog title
		  dialog.setTitle("Getting Data");
		  // Set progress dialog message
		  dialog.setMessage("Loading...");
		  dialog.setIndeterminate(false);
		  // Show progress dialog
		  dialog.show(); 
		 }
		 
		 @Override
		 protected String doInBackground(Void... params) {
		 
		  // call load JSON from url method
		  return loadJSON(url).toString();
		 }
		 
		 @Override
		 protected void onPostExecute(String result) {
		  //parseJsonResponse(result);
			 parseJsonResponse(result);
			 dialog.dismiss();
		 }
		 
		 public JSONObject loadJSON(String url) {
		  // Creating JSON Parser instance
		  JSONGetter jParser = new JSONGetter();
		 
		  // getting JSON string from URL
		  JSONObject json = jParser.getJSONFromUrl(url);
		 
		  return json;
		 }
		 
		 private class JSONGetter {
		 
		  private InputStream is = null;
		  private JSONObject jObj = null;
		  private String json = "";
		 
		  // constructor
		  public JSONGetter() {
		 
		  }
		 
		  public JSONObject getJSONFromUrl(String url) {
		 
		   // Making HTTP request
		   try {
		    // defaultHttpClient
		    DefaultHttpClient httpClient = new DefaultHttpClient();
		    HttpPost httpPost = new HttpPost(url);
		 
		    HttpResponse httpResponse = httpClient.execute(httpPost);
		    HttpEntity httpEntity = httpResponse.getEntity();
		    is = httpEntity.getContent();
		 
		   } catch (UnsupportedEncodingException e) {
		    e.printStackTrace();
		   } catch (ClientProtocolException e) {
		    e.printStackTrace();
		   } catch (IOException e) {
		    e.printStackTrace();
		   }
		 
		   try {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"),
		      8);
		    StringBuilder sb = new StringBuilder();
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		     sb.append(line + "\n");
		    }
		    is.close();
		    json = sb.toString();
		   } catch (Exception e) {
		    Log.e("Buffer Error", "Error converting result " + e.toString());
		   } 
		 
		   // try parse the string to a JSON object
		   try {
		    jObj = new JSONObject(json);
		   } catch (JSONException e) {
		    Log.e("JSON Parser", "Error parsing data " + e.toString());
		   }
		 
		   // return JSON String
		   return jObj;
		 
		  }
		 }
		 
		//parse response data after asynctask finished
		
	}
	
	 public void parseJsonResponse(String result) {
			
		 try {
			   JSONObject json = new JSONObject(result);
			   judul.setText(json.getString("judul"));
			   tanggal.setText(json.getString("tanggal"));
			   isi.setText(json.getString("isi"));
			   sumber.setText(json.getString("sumber"));
			   String imgurl=json.getString("image");
			   sumber.setText(json.getString("image"));
			   Picasso.with(this).load(imgurl).into(image);
		 } catch (JSONException e) {
			   e.printStackTrace();
	 	}
	 }
}

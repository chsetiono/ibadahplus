package com.cipto.doa;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cipto.doa.ActivityQuran.ListClickHandler;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class ActivityNews extends Activity {

 private ListView listview;
 private ArrayList<News> listNews;
 private ArrayAdapter<News> adapter;

 private final static String TAG = ActivityNews.class.getSimpleName();
 //private final static String url = "http://www.json-generator.com/api/json/get/ccLAsEcOSq?indent=2";
 private final static String url = "http://www.ppiln.or.id/apitest.php";
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_news);
  ActionBar ab = getActionBar(); 
	// ab.setDisplayHomeAsUpEnabled(true);
  ab.setHomeButtonEnabled(true);
  ab.setIcon(getResources().getDrawable(R.drawable.icon_back));
  listview = (ListView) findViewById(R.id.lvNews);
  setListViewAdapter();
  getDataFromInternet();
  //listview.setOnItemClickListener(new ListClickHandler());
  

 }

 private void getDataFromInternet() {
  new GetJsonFromUrlTask(this, url).execute();
 }

 private void setListViewAdapter() {
  listNews = new ArrayList<News>();
  adapter = new CustomListAdapter(this, R.layout.list_news, listNews);
  listview.setAdapter(adapter);
  /*
  listview.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view,
              int position, long id) {
          Intent intent = new Intent(ActivityNews.this, ActivityDisplaySunnah.class);
          startActivity(intent);    

      }
  });
  */
 }
 
 //parse response data after asynctask finished
 public void parseJsonResponse(String result) {
  Log.i(TAG, result);
  try {
   JSONObject json = new JSONObject(result);
   JSONArray jArray = new JSONArray(json.getString("result"));
   for (int i = 0; i < jArray.length(); i++) {

    JSONObject jObject = jArray.getJSONObject(i);
    
    News news = new News();
    news.setJudul(jObject.getString("judul"));
    news.setImageUrl(jObject.getString("image"));
    news.setId(jObject.getString("id"));
    news.setIsi(jObject.getString("isi"));
    news.setTanggal(jObject.getString("tanggal"));
    news.setSumber(jObject.getString("sumber"));
    
    listNews.add(news);
   }

   adapter.notifyDataSetChanged();
  } catch (JSONException e) {
   e.printStackTrace();
  }
 }
 public class ListClickHandler implements OnItemClickListener{
	  	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
	  			
				TextView listText = (TextView) view.findViewById(R.id.author);
		        String text = listText.getText().toString();
				Intent intent = new Intent(getApplicationContext(), ActivityDisplayNews.class);
		        intent.putExtra("id",text);
		        startActivity(intent);  
		        
	  			//Toast.makeText(ActivityNews.this, "klik....", Toast.LENGTH_SHORT).show();
			}
	  
	}
 
 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	  String id_sunnah= getIntent().getStringExtra("id");  
	  switch (item.getItemId()) {
	    case android.R.id.home:
       // app icon in action bar clicked; go home
	    	Intent listDoa = new Intent(ActivityNews.this, Main.class);
	            listDoa.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		        startActivity(listDoa);  
       return true;
	  }
	  return true;
	}
}
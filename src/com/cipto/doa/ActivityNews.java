package com.cipto.doa;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cipto.doa.ActivityQuran.ListClickHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

  listview = (ListView) findViewById(R.id.lvNews);
  setListViewAdapter();
  getDataFromInternet();
  
 }

 private void getDataFromInternet() {
  new GetJsonFromUrlTask(this, url).execute();
 }

 private void setListViewAdapter() {
  listNews = new ArrayList<News>();
  adapter = new CustomListAdapter(this, R.layout.list_news, listNews);
  listview.setAdapter(adapter);
  listview.setOnItemClickListener(new ListClickHandler());
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
	  			/*
				TextView listText = (TextView) view.findViewById(R.id.idDoa);
		        String text = listText.getText().toString();
				Intent intent = new Intent(ActivityNews.this, ActivityDisplayNews.class);
		        intent.putExtra("id",text);
		        startActivity(intent);  
		        */
	  			Toast.makeText(ActivityNews.this, "klik....", Toast.LENGTH_SHORT).show();
			}
	  
	}
}
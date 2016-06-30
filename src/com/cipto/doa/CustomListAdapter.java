package com.cipto.doa;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

public class CustomListAdapter extends ArrayAdapter<News> {

 private Activity activity;

 public CustomListAdapter(Activity activity, int resource, List<News> listNews) {
  super(activity, resource, listNews);
  this.activity = activity;

 }

 @Override
 public View getView(int position, View convertView, ViewGroup parent) {

  ViewHolder holder = null;
  LayoutInflater inflater = (LayoutInflater) activity
    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
  // If holder not exist then locate all view from UI file.
  if (convertView == null) {
   // inflate UI from XML file
   convertView = inflater.inflate(R.layout.list_news, parent, false);
   // get all UI view
   holder = new ViewHolder(convertView);
   // set tag for holder
   convertView.setTag(holder);
  } else {
   // if holder created, get tag from view
   holder = (ViewHolder) convertView.getTag();
  }

  final News news = getItem(position);
  
  holder.name.setText(news.getJudul());
  holder.authorName.setText(news.getId());
  Picasso.with(activity).load(news.getImageUrl()).into(holder.image);
 
  convertView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
    	  
    	  Intent intent = new Intent(activity.getApplicationContext(),ActivityDisplayNews.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          intent.putExtra("id", news.getId());
          intent.putExtra("judul",news.getJudul());
          intent.putExtra("isi",news.getIsi());
          intent.putExtra("imageUrl",news.getImageUrl());
          
    	  activity.getApplicationContext().startActivity(intent);
          
          
    	 // Toast.makeText(activity.getApplicationContext(), "testvvvv", Toast.LENGTH_LONG).show();
      }
  });
 
  return convertView;
 }

 private static class ViewHolder {
  private TextView name;
  private TextView authorName;
  private ImageView image;

  public ViewHolder(View v) {
   name = (TextView) v.findViewById(R.id.title);
   image = (ImageView) v.findViewById(R.id.thumbnail);
   authorName = (TextView) v.findViewById(R.id.author);
  }
 }

}
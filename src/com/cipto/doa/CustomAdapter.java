package com.cipto.doa;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CustomAdapter extends ArrayAdapter<String> {
 
	private final Context context;
	private final String[] values;
	private final int type;
     public CustomAdapter(Context context,int type, String[] values) {
         super(context, R.layout.list_row, values);

         this.context = context;
         this.values = values;
         this.type=type;
     }

     /**
      * Here we go and get our rowlayout.xml file and set the textview text.
      * This happens for every row in your listview.
      */
     @Override
     public View getView(int position, View convertView, ViewGroup parent) {
         LayoutInflater inflater = (LayoutInflater) context
                 .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

         View rowView = inflater.inflate(R.layout.list_row, parent, false);

         TextView textView = (TextView) rowView.findViewById(R.id.judul);

         // Setting the text to display
         textView.setText(values[position]);
         return rowView;
     }
}
package com.cipto.doa;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class BookmarkFragment extends Fragment {
 
    public BookmarkFragment(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.main, container, false);
         
        return rootView;
    }
}
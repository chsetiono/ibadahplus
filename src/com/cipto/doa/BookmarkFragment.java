package com.cipto.doa;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
 
public class BookmarkFragment extends Fragment {
	private FragmentTabHost tab;
    public BookmarkFragment(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
        tab= (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        tab.setup(getActivity(), getChildFragmentManager(), R.id.tabcontent);
        tab.addTab(tab.newTabSpec("quran").setIndicator("Quran"),
                BookmarkQuran.class, null);
        tab.addTab(tab.newTabSpec("Artikel").setIndicator("Artikel"),
                BookmarkNews.class, null);
        tab.addTab(tab.newTabSpec("doa").setIndicator("Doa"),
                BookmarkDoa.class, null);
        tab.addTab(tab.newTabSpec("sunnah").setIndicator("Sunnah"),
                BookmarkSunnah.class, null);   
        tab.setCurrentTab(0);
        return rootView;
    }
    
    
}
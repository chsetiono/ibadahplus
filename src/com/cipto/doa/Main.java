package com.cipto.doa;
 
import java.util.ArrayList;
 
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class Main extends FragmentActivity {
 
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
         
        mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
         
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
         
        navDrawerItems = new ArrayList<NavDrawerItem>();
         
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, ""));
       // navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
       // navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1), true, "50+"));
         
        navMenuIcons.recycle();
         
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
         
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);
        mDrawerList.setAdapter(adapter);
         
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
       
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
                R.drawable.icon_drawable, 
                R.string.app_name, 
                R.string.app_name){
             
            public void onDrawerClosed(View view){
                getActionBar().setTitle(mTitle);
                 
                invalidateOptionsMenu();
            }
             
            public void onDrawerOpened(View drawerView){
                getActionBar().setTitle(mDrawerTitle);
                 
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.post(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				mDrawerToggle.syncState();
			}
        	
        });
         
        if(savedInstanceState == null){
            displayView(0);
        }
		
    }
     
    private class SlideMenuClickListener implements ListView.OnItemClickListener{
 
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            displayView(position);          
        }
         
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
     
    public boolean onOptionsItemSelected(MenuItem item){
         
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
         
        switch (item.getItemId()) {
        case R.id.action_settings:
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
     
    public boolean onPreparationsMenu(Menu menu){
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
     
    private void displayView(int position){
    	Intent i=getIntent();
    	String page=i.getStringExtra("fragment");
        Fragment fragment = null;
        if(page!=null){
        	if(page.equals("bookmark")){
        		position=1;
        		getIntent().removeExtra("fragment");
        	}
        }
        	
	       //Intent i;
	    switch (position) {
	        case 0:
	            fragment = new HomeFragment();
	            //i = new Intent(this, TTS.class);
	            //startActivity(i);
	            break;
	        case 1:
	            fragment = new BookmarkFragment();
	            break;   
	        case 2:
	            fragment = new SetLokasiFragment();
	            break;
	        case 3:
	            fragment = new SettingFragmentNew();
	            break;
	       
	        default:
	            break;
	     }

         
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
             
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        }else{
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
     
    public void setTitle(CharSequence title){
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
    
  
}
package com.cipto.doa;

import java.util.HashMap;
import java.util.List;

import com.cipto.doa.ActivityQuran.ListClickHandler;
import com.cipto.doa.R.id;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BookmarkSunnah extends Fragment {
	public ListView lv;
	DBAdapter dbAdapter;
	SimpleAdapter adapter;
    public BookmarkSunnah(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_bookmark_sunnah, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvSunnah);
        
        dbAdapter = new DBAdapter(getActivity().getApplicationContext());
	    dbAdapter.openDataBase();
	    setData(lv);
        return rootView;
    }
    
    public class ListClickHandler implements OnItemClickListener{
	  	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				TextView tvDoa=(TextView) view.findViewById(R.id.idDoa);
				String id = tvDoa.getText().toString();
				//Toast.makeText(getActivity().getApplicationContext(), id_surat, Toast.LENGTH_LONG).show();
				
	  			Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDisplaySunnah.class);
	  			intent.putExtra("id", id);
		        startActivity(intent);  
		        
		       
			}
	  
	}
    
    @Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
    	
	 MenuInflater inflater = getActivity().getMenuInflater();
	 inflater.inflate(R.menu.menu_options_bookmark, menu);
		
	}
    
    @Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
	  int selectpos = info.position+1;
	  String id_bookmark=((TextView)info.targetView.findViewById(R.id.idBookmark)).getText().toString();
	  switch(item.getItemId()) {
      case R.id.deleteBookmark:
    	  dbAdapter.deleteBookmark(Integer.valueOf(id_bookmark));
    	  setData(lv);
    	  
    	  Toast.makeText(getActivity().getApplicationContext(),"dihapus dari bookmark",Toast.LENGTH_LONG).show();
         return true;
      
       default:
             return super.onContextItemSelected(item);
	  }

	}
    
    private void setData(ListView listview){
    	List<HashMap<String,String>> listUsers = dbAdapter.getBookmarkSunnah();
    	
  	    String[] from = new String[] { "id_bookmark","id_sunnah","judul"};
		int[] to = new int[] { R.id.idBookmark, R.id.idDoa,R.id.judulDoa};
	  	SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), listUsers, R.layout.list_row_bookmark_doa, from, to);
	  		
	  	listview.setAdapter(adapter);
	  	listview.setTextFilterEnabled(true);
	  	listview.setOnItemClickListener(new ListClickHandler());
	   registerForContextMenu(listview);
		
    	
    }
}

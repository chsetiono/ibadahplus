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

public class BookmarkNews extends Fragment {
	public ListView lv;
	DBAdapter dbAdapter;
	SimpleAdapter adapter;
    public BookmarkNews(){}
     
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_bookmark_artikel, container, false);
        lv = (ListView) rootView.findViewById(R.id.lvArtikel);
        
        dbAdapter = new DBAdapter(getActivity().getApplicationContext());
	    dbAdapter.openDataBase();
	    setData(lv,adapter);
        return rootView;
    }
    
    public class ListClickHandler implements OnItemClickListener{
	  	@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				TextView id_news=(TextView) view.findViewById(R.id.idArtikel);
				TextView judul_news=(TextView) view.findViewById(R.id.judulArtikel);
				TextView isi_news=(TextView) view.findViewById(R.id.isi);
				TextView sumber_news=(TextView) view.findViewById(R.id.sumber);
				TextView tanggal_news=(TextView) view.findViewById(R.id.tanggal);
				TextView url_gambar=(TextView) view.findViewById(R.id.gambar);
				
				String id = id_news.getText().toString();
				String judul=judul_news.getText().toString();
				String isi=isi_news.getText().toString();
				String tanggal=tanggal_news.getText().toString();
				String sumber=sumber_news.getText().toString();
				String gambar=url_gambar.getText().toString();
				
				//Toast.makeText(getActivity().getApplicationContext(), id_surat, Toast.LENGTH_LONG).show();
				
	  			Intent intent = new Intent(getActivity().getApplicationContext(), ActivityDisplayNews.class);
	  			intent.putExtra("id", id);
	  			intent.putExtra("judul",judul);
	  			intent.putExtra("isi", isi);
	  			intent.putExtra("sumber",sumber);
	  			intent.putExtra("tanggal", tanggal);
	  			intent.putExtra("imageUrl",gambar);
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
	  String id=((TextView)info.targetView.findViewById(R.id.idArtikel)).getText().toString();
	  switch(item.getItemId()) {
      case R.id.deleteBookmark:
    	  dbAdapter.deleteNews(Integer.valueOf(id));
    	  setData(lv,adapter);
    	  
    	  Toast.makeText(getActivity().getApplicationContext(),"dihapus dari bookmark",Toast.LENGTH_LONG).show();
         return true;
      
    
       default:
             return super.onContextItemSelected(item);
	  }

	}
    
    private void setData(ListView listview,SimpleAdapter simpelAdpater){
    	List<HashMap<String,String>> listUsers = dbAdapter.getAllNews();
    	
  	    String[] from = new String[] { "id_news","judul","isi","sumber","imageUrl","tanggal"};
		int[] to = new int[] { R.id.idArtikel, R.id.judulArtikel,R.id.isi,R.id.sumber,R.id.gambar,R.id.tanggal};
	  	  SimpleAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), listUsers, R.layout.list_row_bookmark_artikel, from, to);
	  		
	  	listview.setAdapter(adapter);
	  	listview.setTextFilterEnabled(true);
	  	listview.setOnItemClickListener(new ListClickHandler());
	   registerForContextMenu(listview);
		
    	
    }
}

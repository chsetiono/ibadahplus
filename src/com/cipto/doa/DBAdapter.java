package com.cipto.doa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter extends SQLiteOpenHelper {
	
	public static String DB_PATH = "/data/data/com.cipto.doa/databases/";
	public static String DB_NAME = "doa.sqlite";
	public static final int DB_VERSION = 1;
	
	public static final String TB_DOA = "tbdoa";
	public static final String TB_SHALAWAT = "shalawat";
	public static final String TB_JADWAL_SHALAT = "jadwal_shalat";
	public static final String TB_SETTINGS = "settings";
	public static final String TB_SURAH = "surah";
	public static final String TB_QURAN = "quran";
	public static final String TB_SUNNAH = "sunnah";
	
	public static final int SETTING_LATITUDE=1;
	public static final int SETTING_LONGITUDE=2;
	public static final int SETTING_LOKASI=3;
	public static final int SETTING_METODE=4;
	public static final int SETTING_METODE_ASHAR=5;
	public static final int SETTING_NOTIFIKASI_SUBUH=6;
	public static final int SETTING_NOTIFIKASI_DZUHUR=7;
	public static final int SETTING_NOTIFIKASI_ASHAR=8;
	public static final int SETTING_NOTIFIKASI_MAHRIB=9;
	public static final int SETTING_NOTIFIKASI_ISYA=10;
	public static final int SETTING_SUARA=11;
	public static final int SETTING_TIMEZONE=12;
	public static final int SETTING_KOREKSI_SUBUH=14;
	public static final int SETTING_KOREKSI_DZUHUR=15;
	public static final int SETTING_KOREKSI_ASHAR=16;
	public static final int SETTING_KOREKSI_MAGHRIB=17;
	public static final int SETTING_KOREKSI_ISYA=18;
	
	
	private SQLiteDatabase myDB;
	private Context context;
	
	public DBAdapter(Context context) {
		super(context, DB_NAME, null, DB_VERSION);	
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	
	@Override
	public synchronized void close(){
		if(myDB!=null){
			myDB.close();
		}
		super.close();
	}
		
	public List<HashMap<String,String>> getAllDoa(){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c = db.rawQuery("SELECT * FROM " + TB_DOA , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("id",  c.getString(0));
		            hm.put("judul",c.getString(1));
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	public Cursor getDoa(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_DOA, new String[] {
	                		"id",
	                		"judul",
	                		"arab",
	                		"indo",
	                		"arti",
	                		"kategori"
	                		}, 
	                		"id=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
		
	public List<HashMap<String,String>> getAllShalawat(){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c = db.rawQuery("SELECT * FROM " + TB_SHALAWAT , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("id_shalawat",  c.getString(0));
		            hm.put("judul_shalawat",c.getString(1));
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	
	public Cursor getShalawat(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_SHALAWAT, new String[] {
	                		"id_shalawat",
	                		"judul_shalawat",
	                		"teks_arab",
	                		"teks_latin",
	                		"arti_indonesia",
	                		"file"
	                		}, 
	                		"id_shalawat=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
	
	
	public List<HashMap<String,String>> getAllShalat(){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c = db.rawQuery("SELECT * FROM " + TB_JADWAL_SHALAT , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("id_jadwal",  c.getString(0));
		            hm.put("nama_shalat",c.getString(1));
		            hm.put("waktu",c.getString(2));
		            hm.put("alarm",c.getString(3));
		          
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	
	public void alarmOn(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.rawQuery("UPDATE jadwal_shalat SET alarm=1 where id_jadwal="+id,null);
	}
	
	
	public Cursor getSettings(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_SETTINGS, new String[] {
	                		"id_setting",
	                		"nama_setting",
	                		"value",
	                		}, 
	                		"id_setting=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
	
	
	public void updateSetting(ContentValues values,int id){
		SQLiteDatabase db = this.getWritableDatabase();
		db.update(TB_SETTINGS, values, "id_setting=" + id, null);
	}
	//inser to bookmark
	public void insertBookmark(ContentValues values) {
		  SQLiteDatabase database = this.getWritableDatabase();
		  database.insert("bookmark", null, values);
	}

	public Cursor getBookmark(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, "bookmark", new String[] {
	                		"id_bookmark",
	                		"tipe",
	                		"id_item",
	                		}, 
	                		"id_item=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}

	public List<HashMap<String,String>> getAllSurah(){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c = db.rawQuery("SELECT * FROM " + TB_SURAH , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("id_surah",  c.getString(0));
		            hm.put("teks_indo",c.getString(2));
		            hm.put("teks_arab",c.getString(3));
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	public Cursor getSurah(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_SURAH, new String[] {
	                		"id_surah",
	                		"teks_indo",
	                		"teks_arab",
	                		}, 
	                		"id_surah=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
	

	
	public List<HashMap<String,String>> getAllAyat(String id_surah){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c=db.query(true,TB_QURAN , new String[] {
                    "rowid",
                    "nomor_ayat",
                    "id_surat",
                    "teks_arab",
                    "teks_indo",
                  	}, 
                    "id_surat=?", 
                    new String[] {id_surah},
                    null, null, "rowid" , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("rowid",  c.getString(0));
		            hm.put("nomor_ayat",c.getString(1));
		            hm.put("id_surat",c.getString(2));
		            hm.put("teks_arab",c.getString(3));
		            hm.put("teks_indo",c.getString(4));
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	
	public Cursor getAyat(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_QURAN, new String[] {
	                		"rowid",
	                		"nomor_ayat",
	                		"id_surah",
	                		"teks_arab",
	                        "teks_indo",
	                		}, 
	                		"rowid=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
	
	
	
	public List<HashMap<String,String>> getAllSunnah(){
		List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c;
		
		try {
			c = db.rawQuery("SELECT * FROM " + TB_SUNNAH , null);
			if(c == null) return null;
			c.moveToFirst();
			do {
				
				 HashMap<String, String> hm = new HashMap<String,String>();
		           
		          
		            hm.put("id_sunnah",  c.getString(0));
		            hm.put("kategori",c.getString(1));
		            hm.put("judul",c.getString(2));
		            hm.put("teks_arab",c.getString(3));
		            hm.put("teks_indo",c.getString(3));
		            aList.add(hm);
			} while (c.moveToNext()); 
			
			c.close();
		} catch (Exception e) {
			Log.e("tle99", e.getMessage());
		}
		
		
		db.close();		
		
		return aList;
	}
	public Cursor getSunnah(int id){
		SQLiteDatabase db = this.getWritableDatabase();
		 Cursor c =
	                db.query(true, TB_SUNNAH, new String[] {
	                		"id_sunnah",
	     		            "kategori",
	     		            "judul",
	     		            "teks_arab",
	     		            "teks_indo",
	                		}, 
	                		"id_sunnah=" + id, 
	                		null,
	                		null, 
	                		null, 
	                		null, 
	                		null);

	        if (c != null) {
	            c.moveToFirst();
	        }
	        
	        db.close();	
	        return c;
	}
	
	/***
	 * Open database
	 * @throws SQLException
	 */
	public void openDataBase() throws SQLException{
		String myPath = DB_PATH + DB_NAME;
		myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
	}

	/***
	 * Copy database from source code assets to device
	 * @throws IOException
	 */
	public void copyDataBase() throws IOException{
		try {
			InputStream myInput = context.getAssets().open(DB_NAME);
			String outputFileName = DB_PATH + DB_NAME;
			OutputStream myOutput = new FileOutputStream(outputFileName);

			byte[] buffer = new byte[1024];
			int length;

			while((length = myInput.read(buffer))>0){
				myOutput.write(buffer, 0, length);
			}

			myOutput.flush();
			myOutput.close();
			myInput.close();
		} catch (Exception e) {
			Log.e("tle99 - copyDatabase", e.getMessage());
		}
		
	}
	
	/***
	 * Check if the database doesn't exist on device, create new one
	 * @throws IOException
	 */
	public void createDataBase() throws IOException {
		boolean dbExist = checkDataBase();		
		
		if (dbExist) {

		} else {
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				Log.e("tle99 - create", e.getMessage());
			}
		}
	}
	
	// ---------------------------------------------
	// PRIVATE METHODS
	// ---------------------------------------------
	
	/***
	 * Check if the database is exist on device or not
	 * @return
	 */
	private boolean checkDataBase() {
		SQLiteDatabase tempDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			tempDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READWRITE);
		} catch (SQLiteException e) {
			Log.e("tle99 - check", e.getMessage());
		}
		if (tempDB != null)
			tempDB.close();
		return tempDB != null ? true : false;
	}
	

}

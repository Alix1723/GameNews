package com.student.alix.gamenews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Alix on 18/12/2014.
 */
public class DevMapDBManager extends SQLiteOpenHelper {

    private static final int DB_VER = 1;
    private static final String DB_PATH = "/data/data/com.alix.student.gamenews/databases/";
    private static final String DB_NAME = "famousdevs.s3db";
    private static final String TBL_DEVS = "famousdevelopers";

    private static final String COL_ENTRYID = "entryID";
    private static final String COL_NAME = "Name";
    private static final String COL_KNOWNFOR = "KnownFor";
    private static final String COL_LATITUDE = "Latitude";
    private static final String COL_LONGITUDE = "Longituded";

    private final Context appContext;

    public DevMapDBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.appContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_FAMOUSDEVS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TBL_DEVS + "("
                + COL_ENTRYID + " INTEGER PRIMARY KEY," + COL_NAME
                + " TEXT," + COL_KNOWNFOR + " TEXT," + COL_LATITUDE + " FLOAT" + COL_LONGITUDE + " FLOAT" +")";

        db.execSQL(CREATE_FAMOUSDEVS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion > oldVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + TBL_DEVS);
            onCreate(db);
        }
    }

    //Creates empty table and overwrites it with ours
    public void dbCreate() throws IOException {

        boolean dbExist = dbCheck();

        if(!dbExist){
            //By calling this method an empty database will be created into the default system path
            //of your application so we can overwrite that database with our database.
            this.getReadableDatabase();
            try {
                copyDBFromAssets();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }

    }

    //Check if the database already exist to avoid re-copying the file each time you open the application - return true if it exists, false if it doesn't
    private boolean dbCheck(){

        SQLiteDatabase db = null;

        try{
            String dbPath = DB_PATH + DB_NAME;
            db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
            db.setLocale(Locale.getDefault());
            db.setVersion(1);
        }
        catch(SQLiteException e)
        {
            Log.e("SQLHelper", "Database not Found!");
        }

        if(db != null){
            db.close();
        }

        return db != null ? true : false;
    }

    // Copies your database from your local assets-folder to the just created empty database in the
    // system folder, from where it can be accessed and handled.
    // This is done by transfering bytestream.
    private void copyDBFromAssets() throws IOException{

        InputStream dbInput = null;
        OutputStream dbOutput = null;
        String dbFileName = DB_PATH + DB_NAME;

        try {
            dbInput = appContext.getAssets().open(DB_NAME);
            dbOutput = new FileOutputStream(dbFileName);
            //transfer bytes from the dbInput to the dbOutput
            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            //Close the streams
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        } catch (IOException e)
        {
            throw new Error("Problems copying DB!");
        }
    }


    public void addFamousDeveloperEntry(DevMapData aDev) {

        ContentValues values = new ContentValues();
        values.put(COL_NAME, aDev.getName());
        values.put(COL_KNOWNFOR, aDev.getKnownFor());
        values.put(COL_LATITUDE, aDev.getLatitude());
        values.put(COL_LONGITUDE, aDev.getLongitude());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TBL_DEVS, null, values);
        db.close();
    }

    public DevMapData getFamousDeveloperEntry(String aDev) {

        String query = "Select * FROM " + TBL_DEVS + " WHERE " + COL_NAME + " =  \"" + aDev + "\"";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        DevMapData MapDataEntry = new DevMapData();

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0)));
            MapDataEntry.setName(cursor.getString(1));
            MapDataEntry.setKnownFor(cursor.getString(2));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
            MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(4)));
            cursor.close();
        } else {
            MapDataEntry = null;
        }
        db.close();
        return MapDataEntry;
    }

    public boolean removeFamousDevEntry(String aDev) {

        boolean result = false;

        String query = "Select * FROM " + TBL_DEVS + " WHERE " + COL_NAME + " =  \"" + aDev + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        DevMapData devInfo = new DevMapData();

        if (cursor.moveToFirst()) {
            devInfo.setEntryID(Integer.parseInt(cursor.getString(0)));
            db.delete(TBL_DEVS, COL_ENTRYID + " = ?",
                    new String[] { String.valueOf(devInfo.getEntryID()) });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public List<DevMapData> allMapData()
    {
        String query = "SELECT * FROM " + TBL_DEVS;
        List<DevMapData> mcMapDataList = new ArrayList<DevMapData>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast()==false) {
                DevMapData MapDataEntry = new DevMapData();
                MapDataEntry.setEntryID(Integer.parseInt(cursor.getString(0)));
                MapDataEntry.setName(cursor.getString(1));
                MapDataEntry.setKnownFor(cursor.getString(2));
                MapDataEntry.setLatitude(Float.parseFloat(cursor.getString(3)));
                MapDataEntry.setLongitude(Float.parseFloat(cursor.getString(4)));
                mcMapDataList.add(MapDataEntry);
                cursor.moveToNext();
            }
        } else {
            mcMapDataList.add(null);
        }
        db.close();
        return mcMapDataList;
    }
}

package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.Place;

/**
 * Created by Hieu on 10/24/2015.
 */
public class PlacesDataSource {
    public static final String TAG = "PlacesDataSource";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBOpenHelper.PlacesTable.Columns.PLACE_ID,
            DBOpenHelper.PlacesTable.Columns.USERNAME,
            DBOpenHelper.PlacesTable.Columns.NAME,
            DBOpenHelper.PlacesTable.Columns.ADDRESS,
            DBOpenHelper.PlacesTable.Columns.MAIN_TYPE,
            DBOpenHelper.PlacesTable.Columns.LAT,
            DBOpenHelper.PlacesTable.Columns.LNG,
            DBOpenHelper.PlacesTable.Columns.DESCRIPTION,
            DBOpenHelper.PlacesTable.Columns.PHONE_NUMBER,
            DBOpenHelper.PlacesTable.Columns.IMAGE,
            DBOpenHelper.PlacesTable.Columns.WEB_URL
    };

    public PlacesDataSource(Context context) {
        dbhelper = new DBOpenHelper(context);
        database = dbhelper.getWritableDatabase();
    }

    public void open() {
        Log.i(TAG, "Database opened");
//        database = dbhelper.getWritableDatabase();
    }

    public void close() {
        Log.i(TAG, "Database closed");
        dbhelper.close();
    }

    public void create(Place place) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.PlacesTable.Columns.PLACE_ID, place.getPlaceID());
        values.put(DBOpenHelper.PlacesTable.Columns.USERNAME, place.getUsername());
        values.put(DBOpenHelper.PlacesTable.Columns.NAME, place.getName());
        values.put(DBOpenHelper.PlacesTable.Columns.ADDRESS, place.getAddress());
        values.put(DBOpenHelper.PlacesTable.Columns.MAIN_TYPE, place.getMainType());
        values.put(DBOpenHelper.PlacesTable.Columns.LAT, place.getLat());
        values.put(DBOpenHelper.PlacesTable.Columns.LNG, place.getLng());
        values.put(DBOpenHelper.PlacesTable.Columns.DESCRIPTION, place.getDescription());
        values.put(DBOpenHelper.PlacesTable.Columns.PHONE_NUMBER, place.getPhoneNumber());
        values.put(DBOpenHelper.PlacesTable.Columns.IMAGE, place.getImageReference());
        values.put(DBOpenHelper.PlacesTable.Columns.WEB_URL, place.getWebsiteURL());
        database.insert(DBOpenHelper.PlacesTable.NAME, null, values);
    }

    public List<Place> findUserPlaces(String selection) {
        List<Place> places = new ArrayList<Place>();
        Cursor cursor = database.query(DBOpenHelper.PlacesTable.NAME, allColumns,
                selection, null, null, null, null);
        Log.i(TAG, "Returned " + cursor.getCount() + " rows");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Place place = new Place();
                place.setPlaceID(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.PLACE_ID)));
                place.setUsername(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.USERNAME)));
                place.setName(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.NAME)));
                place.setAddress(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.ADDRESS)));
                place.setMainType(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.MAIN_TYPE)));
                place.setLat(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.LAT)));
                place.setLng(cursor.getDouble(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.LNG)));
                place.setDescription(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.DESCRIPTION)));
                place.setPhoneNumber(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.PHONE_NUMBER)));
                place.setImageReference(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.IMAGE)));
                place.setWebsiteURL(cursor.getString(cursor.getColumnIndex(DBOpenHelper.PlacesTable.Columns.WEB_URL)));
                places.add(place);
            }
        }
        return places;
    }
}


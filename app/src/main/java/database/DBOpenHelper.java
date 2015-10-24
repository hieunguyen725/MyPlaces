package database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Hieu on 10/23/2015.
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBOpenHelper";

    private static final String DATABASE_NAME =  "myplaces.db";
    private static final int DATABASE_VERSION = 2;

    public static final class UserTable {
        public static final String NAME = "user";

        public static final class Columns {
            public static final String USERNAME = "username";
            public static final String PASSWORD = "password";
        }
    }

    private static final String CREATE_USER_TABLE =
            "CREATE TABLE " + UserTable.NAME + " (" +
                    UserTable.Columns.USERNAME + " TEXT PRIMARY KEY, " +
                    UserTable.Columns.PASSWORD + " TEXT " +
                    ")";

    public static final class PlacesTable {
        public static final String NAME = "places";

        public static final class Columns {
            public static final String PLACE_ID = "placeID";
            public static final String USERNAME = "username";
            public static final String NAME = "name";
            public static final String ADDRESS = "address";
            public static final String MAIN_TYPE = "mainType";
            public static final String LAT = "latitude";
            public static final String LNG = "longitude";
            public static final String DESCRIPTION = "description";
            public static final String PHONE_NUMBER = "phoneNumber";
            public static final String IMAGE = "image";
            public static final String WEB_URL = "webURL";
        }
    }

    private static final String CREATE_PLACES_TABLE =
            "CREATE TABLE " + PlacesTable.NAME + " (" +
                    PlacesTable.Columns.PLACE_ID + " TEXT PRIMARY KEY, " +
                    PlacesTable.Columns.USERNAME + " TEXT, " +
                    PlacesTable.Columns.NAME + " TEXT, " +
                    PlacesTable.Columns.ADDRESS + " TEXT, " +
                    PlacesTable.Columns.MAIN_TYPE + " TEXT, " +
                    PlacesTable.Columns.LAT + " REAL, " +
                    PlacesTable.Columns.LNG + " REAL, " +
                    PlacesTable.Columns.DESCRIPTION + " TEXT, " +
                    PlacesTable.Columns.PHONE_NUMBER + " TEXT, " +
                    PlacesTable.Columns.IMAGE + " TEXT, " +
                    PlacesTable.Columns.WEB_URL + " TEXT " +
                    ")";



    public DBOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PLACES_TABLE);
        Log.i(TAG, "Tables have been created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserTable.NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlacesTable.NAME);
        onCreate(db);
    }
}

package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import model.User;

/**
 * Created by Hieu on 10/23/2015.
 */
public class UserDataSource {
    public static final String TAG = "UserDataSource";

    SQLiteOpenHelper dbhelper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBOpenHelper.UserTable.Columns.USERNAME,
            DBOpenHelper.UserTable.Columns.PASSWORD
    };

    public UserDataSource(Context context) {
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

    public void create(User user) {
        ContentValues values = new ContentValues();
        values.put(DBOpenHelper.UserTable.Columns.USERNAME, user.getUsername());
        values.put(DBOpenHelper.UserTable.Columns.PASSWORD, user.getPassword());
        database.insert(DBOpenHelper.UserTable.NAME, null, values);
    }

    public List<User> findAll() {
        List<User> users = new ArrayList<User>();
        Cursor cursor = database.query(DBOpenHelper.UserTable.NAME, allColumns,
        null, null, null, null, null);
        Log.i(TAG, "Returned " + cursor.getCount() + " rows");
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                User user = new User(
                        cursor.getString(cursor.getColumnIndex(
                                DBOpenHelper.UserTable.Columns.USERNAME)),
                        cursor.getString(cursor.getColumnIndex(
                                DBOpenHelper.UserTable.Columns.PASSWORD)));
                users.add(user);
            }
        }
        return users;
    }
}

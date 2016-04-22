package com.example.rasmusengmark.cardmanager;

/**
 * Created by Mikkel on 19-04-2016.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteAdapter {

    // Database Version
    public static final int DATABASE_VERSION = 1;
    // Database Name
    public static final String DATABASE_NAME = "CardManager";
    // Contacts table name
    public static final String TABLE_NAME = "Users";
    // Users Table Columns names
    public static final String USER_ID = "id";
    public static final String USER_NAME = "name";
    public static final String USER_EMAIL = "email";

    public static final String PREFERENCES_DB = "PreferencesDb";

    private SQLiteHelper sqLiteHelper;
    private SQLiteDatabase sqLiteDatabase;
    private Context context;

    private SharedPreferences preferences;

    private long id = 1;

    public SQLiteAdapter(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(PREFERENCES_DB, Activity.MODE_APPEND);
        if(preferences != null){
            id = preferences.getLong("nextId", 1);
        }

    }

    public SQLiteAdapter open () {
        sqLiteHelper = new SQLiteHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
        sqLiteDatabase = sqLiteHelper.getWritableDatabase(); //Should be in a background thread, i.e. Async
        return this;
    }

    public long create(User user) {
        ContentValues values = new ContentValues();
        values.put(USER_ID,id);
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL,user.getEmail());

        sqLiteDatabase.insert(TABLE_NAME,null,values);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("nextId",id+1);
        editor.commit();

        return id++;


    }

    public Cursor readAll() {
        String[] columns = new String[]{USER_ID, USER_NAME, USER_EMAIL};
        //return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        //(Table_name, columns, where, where args, group by, order by, having)
    }

    public boolean update(Long userId, User user) {
        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getName());
        values.put(USER_EMAIL, user.getEmail());

        String whereClause = USER_ID + " = ?";
        String[] whereArgs = new String[]{userId.toString()};

        int numberOfRowsUpdated = sqLiteDatabase.update(TABLE_NAME, values, whereClause, whereArgs);

        return (numberOfRowsUpdated == 1);
    }

    public boolean delete(Long userId) {
        String whereClause = USER_ID + " = ?";
        String[] whereArgs = new String[]{userId.toString()};
        int numberOfRowsUpdated = sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);

        return (numberOfRowsUpdated == 1);
    }


    public void close() {
        sqLiteDatabase.close();
    }



}

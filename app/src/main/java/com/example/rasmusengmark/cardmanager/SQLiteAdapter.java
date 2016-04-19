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
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdapter extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "cardManager";
    // Contacts table name
    private static final String TABLE_NAME = "Users";
    // Users Table Columns names
    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_EMAIL = "email";

    public static final String PREFERENCES_DB = "PreferencesDb";

    private SQLiteHelper dbHelper;
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
        values.put(PERSON_ID,id);
        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_AGE,person.getAge());

        sqLiteDatabase.insert(TABLE_NAME,null,values);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong("nextId",id+1);
        editor.commit();

        return id++;


    }

    public Cursor readAll() {
        String[] columns = new String[]{PERSON_ID, PERSON_NAME, PERSON_AGE};
        //return sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        //(Table_name, columns, where, where args, group by, order by, having)
    }

    public boolean update(Long personId, Person person) {
        ContentValues values = new ContentValues();
        values.put(PERSON_NAME, person.getName());
        values.put(PERSON_AGE, person.getAge());

        String whereClause = PERSON_ID + " = ?";
        String[] whereArgs = new String[]{personId.toString()};

        int numberOfRowsUpdated = sqLiteDatabase.update(TABLE_NAME, values, whereClause, whereArgs);

        return (numberOfRowsUpdated == 1);
    }

    public boolean delete(Long personId) {
        String whereClause = PERSON_ID + " = ?";
        String[] whereArgs = new String[]{personId.toString()};
        int numberOfRowsUpdated = sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);

        return (numberOfRowsUpdated == 1);
    }


    public void close() {
        sqLiteDatabase.close();
    }



}

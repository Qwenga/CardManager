package com.example.rasmusengmark.cardmanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.TABLE_NAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_ID;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_EMAIL;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_PASSWORD;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_FIRSTNAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_LASTNAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_AGE;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_CPR;

/**
 * Created by Mikkel on 22-04-2016.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String SCRIPT_CREATE_DATABASE = "CREATE TABLE "
            + TABLE_NAME + " (" + USER_ID + " NUMBER PRIMARY KEY,"
            + USER_EMAIL + " TEXT NOT NULL,"
            + USER_PASSWORD + " TEXT NOT NULL,"
            + USER_FIRSTNAME + " TEXT NOT NULL,"
            + USER_LASTNAME + " TEXT NOT NULL,"
            + USER_AGE + " NUMBER NOT NULL,"
            + USER_CPR + " TEXT NOT NULL)";

    public SQLiteHelper (Context context, String name, CursorFactory factory, int version ) {
        super(context,name,factory,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SCRIPT_CREATE_DATABASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //ToDo Add upgrade logic
    }
}

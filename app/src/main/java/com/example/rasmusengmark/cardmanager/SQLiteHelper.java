package com.example.rasmusengmark.cardmanager;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.TABLE_NAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_ID;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_NAME;
import static com.example.rasmusengmark.cardmanager.SQLiteAdapter.USER_EMAIL;

/**
 * Created by Mikkel on 22-04-2016.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String SCRIPT_CREATE_DATABASE = "CREATE TABLE "
            + TABLE_NAME + " (" + USER_ID + " NUMBER PRIMARY KEY,"
            + USER_NAME + " TEXT NOT NULL,"
            + USER_EMAIL + " NUMBER NOT NULL)"; //SKAL DEN TJEKKE DET?

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

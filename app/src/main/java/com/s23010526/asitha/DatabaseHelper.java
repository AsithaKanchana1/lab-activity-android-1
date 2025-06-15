package com.s23010526.asitha;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "User.db";
    public static final String TABLE_NAME = "users_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "USERNAME";
    public static final String COL_3 = "PASSWORD";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //this method will call when first time database creates
    @Override
    public void onCreate(SQLiteDatabase db) {
        //sql quaries tocreate tables
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");

    }

    // THIS WILL CALL WHEN DATABASE NEED TO UPDATE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //OLD TABLES NEED TO DROP WHEN NESSOSRY
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    // Method to insert a new user into the database
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, username); // Adds username to the content values
        contentValues.put(COL_3, password); // Adds password to the content values
        long result = db.insert(TABLE_NAME, null, contentValues); // Inserts the data into the table

        // db.insert() returns -1 if there is an error
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        // This interface provides random read-write access to the result set returned by a database query
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = ? AND " + COL_3 + " = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}

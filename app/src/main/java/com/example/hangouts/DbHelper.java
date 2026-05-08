package com.example.hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// OpenHelper Android class for SQLite CRUD
public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create
        db.execSQL(Constants.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // upgrade

        // drop if exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertContact(String name, String company, String phone, String email, String note, String addedTime, String updatedTime) {

        SQLiteDatabase db = this.getWritableDatabase(); // auto updates db if changes (calling onUpgrade, onCreate)

        ContentValues contentValues = new ContentValues(); // safe pair key-value of SDK

        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_COMPANY, company);
        contentValues.put(Constants.C_PHONE, phone);
        contentValues.put(Constants.C_EMAIL, email);
        contentValues.put(Constants.C_NOTE, note);
        contentValues.put(Constants.C_ADDED_TIME, addedTime);
        contentValues.put(Constants.C_UPDATED_TIME, updatedTime);

        long id = db.insert(Constants.TABLE_NAME, null, contentValues); // returns C_ID of contact (primary key) if successful
        db.close();
        return id;
    }
}

package com.example.hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Contact contact = new Contact(
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_COMPANY)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE))
                );

                contactList.add(contact);

                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return contactList;
    }

    public Contact getContactById(int contactId) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_NAME + " WHERE ID = ?", new String[]{String.valueOf(contactId)});

        if (cursor.moveToFirst()) {

            Contact contact = new Contact(
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_COMPANY)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_EMAIL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_NOTE))
            );
            cursor.close();
            db.close();
            return contact;
        }
        cursor.close();
        db.close();
        return null;
    }
}

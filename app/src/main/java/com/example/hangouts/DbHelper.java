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
        db.execSQL(Constants.CREATE_CONTACT_TABLE);
        db.execSQL(Constants.CREATE_MSG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // upgrade

        // drop if exists
        db.execSQL("DROP TABLE IF EXISTS " + Constants.CONTACT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.MSG_TABLE);
        onCreate(db);
    }



    /// ************** CONTACTS ************** ///


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

        long id = db.insert(Constants.CONTACT_TABLE, null, contentValues); // returns C_ID of contact (primary key) if successful
        db.close();
        return id;
    }

    public List<Contact> getAllContacts() {

        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.CONTACT_TABLE, null);

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Contact contact = new Contact(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
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
        //Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.CONTACT_TABLE + " WHERE ID = ?", new String[]{String.valueOf(contactId)});
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.CONTACT_TABLE + " WHERE " + Constants.C_ID + " = ?", new String[]{String.valueOf(contactId)});

        if (cursor.moveToFirst()) {

            Contact contact = new Contact(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
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

    public Contact getContactByPhone(String phone) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + Constants.CONTACT_TABLE +
                        " WHERE " + Constants.C_PHONE + " = ?",
                new String[]{phone}
        );

        if (cursor.moveToFirst()) {

            Contact contact = new Contact(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_ID)),
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

    public int updateContact(int id, String name, String company, String phone, String email, String note, String updatedTime) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.C_NAME, name);
        contentValues.put(Constants.C_COMPANY, company);
        contentValues.put(Constants.C_PHONE, phone);
        contentValues.put(Constants.C_EMAIL, email);
        contentValues.put(Constants.C_NOTE, note);
        contentValues.put(Constants.C_UPDATED_TIME, updatedTime);

        int result = db.update(Constants.CONTACT_TABLE, contentValues, Constants.C_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
        return result; // n rows affected
    }

    public int deleteContact(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(Constants.CONTACT_TABLE, Constants.C_ID + "=?", new String[]{String.valueOf(id)});

        db.close();
        return result; // n rows affected
    }


    /// ************** MESSAGES ************** ///


    public long insertMessage(int contactId, String msg, int isSent, String timestamp, String externalId) {

        SQLiteDatabase db = this.getWritableDatabase(); // auto updates db if changes (calling onUpgrade, onCreate)

        ContentValues contentValues = new ContentValues();

        contentValues.put(Constants.C_MSG_CONTACT_ID, contactId);
        contentValues.put(Constants.C_MSG, msg);
        contentValues.put(Constants.C_MSG_IS_SENT, isSent);
        contentValues.put(Constants.C_MSG_TIMESTAMP, timestamp);
        contentValues.put(Constants.C_MSG_EXTERNAL_ID, externalId);

        long id = db.insertWithOnConflict(Constants.MSG_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE); // returns C_MSG_ID (primary key) if successful

        db.close();
        return id;
    }

    public Message getMessageById(int messageId) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.MSG_TABLE + " WHERE " + Constants.C_MSG_ID + " = ?", new String[]{String.valueOf(messageId)});

        if (cursor.moveToFirst()) {

            Message message = new Message(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_ID)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_CONTACT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_MSG)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_IS_SENT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_MSG_TIMESTAMP))
            );

            cursor.close();
            db.close();
            return message;
        }

        cursor.close();
        db.close();
        return null;
    }

    public List<Message> getAllMessagesByContactId(int contactId) {

        List<Message> messageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.MSG_TABLE + " WHERE " + Constants.C_MSG_CONTACT_ID + " = ?", new String[]{String.valueOf(contactId)});

        if (cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {

                Message message = new Message(
                        cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_ID)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_CONTACT_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_MSG)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(Constants.C_MSG_IS_SENT)),
                        cursor.getString(cursor.getColumnIndexOrThrow(Constants.C_MSG_TIMESTAMP))
                );

                messageList.add(message);
                cursor.moveToNext();
            }
        }

        cursor.close();
        db.close();

        return messageList;
    }
}

package com.example.hangouts;

public class Constants {

    // db name
    public static final String DATABASE_NAME = "CONTACT_DB";
    public static final int DATABASE_VERSION =5;

    // table name
    public static final String TABLE_NAME = "CONTACT_TABLE";

        // columns
        public static final String C_ID = "ID";
        public static final String C_NAME = "NAME";
        public static final String C_COMPANY = "COMPANY";
        public static final String C_PHONE = "PHONE";
        public static final String C_EMAIL = "EMAIL";
        public static final String C_NOTE = "NOTE";
        public static final String C_ADDED_TIME = "ADDED_TIME";
        public static final String C_UPDATED_TIME = "UPDATED_TIME";


    // Create query
    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT, "
            + C_COMPANY + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_NOTE + " TEXT, "
            + C_ADDED_TIME + " TEXT, "
            + C_UPDATED_TIME + " TEXT "
            + " );";
}

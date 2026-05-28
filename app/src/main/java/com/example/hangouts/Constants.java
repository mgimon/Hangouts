package com.example.hangouts;

public class Constants {

    // db name
    public static final String DATABASE_NAME = "CONTACT_DB";
    public static final int DATABASE_VERSION =6;

    // CONTACT
    public static final String CONTACT_TABLE = "CONTACT_TABLE";

        public static final String C_ID = "ID";
        public static final String C_NAME = "NAME";
        public static final String C_COMPANY = "COMPANY";
        public static final String C_PHONE = "PHONE";
        public static final String C_EMAIL = "EMAIL";
        public static final String C_NOTE = "NOTE";
        public static final String C_ADDED_TIME = "ADDED_TIME";
        public static final String C_UPDATED_TIME = "UPDATED_TIME";

    // MESSAGE
    public static final String MSG_TABLE = "MSG_TABLE";
        public static final String C_MSG_ID = "ID";
        public static final String C_MSG_CONTACT_ID = "CONTACT_ID";
        public static final String C_MSG = "MSG";
        public static final String C_MSG_IS_SENT = "IS_SENT";
        public static final String C_MSG_TIMESTAMP = "TIMESTAMP";
        public static final String C_MSG_EXTERNAL_ID = "SMS_ID"; // avoids external insertion of duplicate SMS



    // Create query
    public static final String CREATE_CONTACT_TABLE = "CREATE TABLE " + CONTACT_TABLE + "( "
            + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_NAME + " TEXT, "
            + C_COMPANY + " TEXT, "
            + C_PHONE + " TEXT, "
            + C_EMAIL + " TEXT, "
            + C_NOTE + " TEXT, "
            + C_ADDED_TIME + " TEXT, "
            + C_UPDATED_TIME + " TEXT "
            + " );";

    public static final String CREATE_MSG_TABLE = "CREATE TABLE " + MSG_TABLE + "( "
            + C_MSG_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + C_MSG_CONTACT_ID + " INTEGER, "
            + C_MSG + " TEXT, "
            + C_MSG_IS_SENT + " INTEGER, "
            + C_MSG_TIMESTAMP + " TEXT, "
            + C_MSG_EXTERNAL_ID + " TEXT UNIQUE "
            + " );";
}

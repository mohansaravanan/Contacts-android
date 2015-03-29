package com.bh.contacts.db;

public interface ContactTable {

    public static final String TABLE_NAME = "contacts";

    public static final String COL_ID = "id";
    public static final String COL_FIRST_NAME = "first_name";
    public static final String COL_SURNAME = "surname";
    public static final String COL_ADDRESS = "address";
    public static final String COL_PHONE_NUMBER = "phone_number";
    public static final String COL_EMAIL = "email";
    public static final String COL_CREATED_AT = "createdAt";
    public static final String COL_UPDATED_AT = "updatedAt";
    public static final String COL_AVATAR_URL = "avatar_url";


    public static final String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY" + ","
            + COL_FIRST_NAME + " TEXT" + ","
            + COL_SURNAME + " TEXT" + ","
            + COL_ADDRESS + " TEXT" + ","
            + COL_PHONE_NUMBER + " TEXT" + ","
            + COL_EMAIL + " TEXT" + ","
            + COL_CREATED_AT + " INTEGER" + ","
            + COL_UPDATED_AT + " INTEGER" + ","
            + COL_AVATAR_URL + " TEXT"
            + ");";
}

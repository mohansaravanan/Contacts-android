package com.bh.contacts.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BHDatabaseHelper extends SQLiteOpenHelper {

    private static BHDatabaseHelper instance;

    private static final String DB_NAME = "contact.db";

    public static final int DB_VERSION = 1;

    private BHDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static BHDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new BHDatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactTable.CREATE_CONTACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

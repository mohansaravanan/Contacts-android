package com.bh.contacts.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bh.contacts.model.Contact;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactsDao {

    public static void insert(final Context context, final Contact contact) {
        final SQLiteOpenHelper openHelper = BHDatabaseHelper.getInstance(context);
        final SQLiteDatabase db = openHelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ContactTable.COL_ID, contact.getId());
            values.put(ContactTable.COL_FIRST_NAME, contact.getFirstName());
            values.put(ContactTable.COL_SURNAME, contact.getSurname());
            values.put(ContactTable.COL_ADDRESS, contact.getAddress());
            values.put(ContactTable.COL_PHONE_NUMBER, contact.getPhoneNumber());
            values.put(ContactTable.COL_EMAIL, contact.getEmail());
            values.put(ContactTable.COL_CREATED_AT, contact.getCreatedAt().getTime());
            values.put(ContactTable.COL_UPDATED_AT, contact.getUpdatedAt().getTime());
            values.put(ContactTable.COL_AVATAR_URL, contact.getAvatar());
            db.insert(ContactTable.TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public static List<Contact> getContacts(final Context context) {
        final SQLiteOpenHelper openHelper = BHDatabaseHelper.getInstance(context);
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM " + ContactTable.TABLE_NAME, null);
            if (cursor.moveToFirst()) {
                contacts = new ArrayList<Contact>();
                do {
                    Contact contact = new Contact.ContactBuilder().
                            withId(cursor.getLong(0))
                            .withFirstName(cursor.getString(1))
                            .withSurname(cursor.getString(2))
                            .withAddress(cursor.getString(3))
                            .withPhoneNumber(cursor.getString(4))
                            .withEmail(cursor.getString(5))
                            .withCreatedAt(new Date(cursor.getLong(6)))
                            .withUpdatedAt(new Date(cursor.getLong(7)))
                            .withAvatar(cursor.getString(8))
                            .build();
                    contacts.add(contact);

                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return contacts;
    }
}

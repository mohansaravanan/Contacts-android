package com.bh.contacts.ui;

import android.content.Context;
import android.content.Intent;

import com.bh.contacts.model.Contact;

public class Intents {

    public static final String CONTACT_PARCEL = "contact.parcel";

    public  static Intent createContactDetailIntent(final Context context, final Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra(CONTACT_PARCEL, contact);
        return intent;
    }

    public  static Intent createContactsIntent(final Context context) {
        Intent intent = new Intent(context, ContactsActivity.class);
        return intent;
    }


}

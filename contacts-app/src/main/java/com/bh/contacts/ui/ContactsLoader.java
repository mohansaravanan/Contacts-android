package com.bh.contacts.ui;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bh.contacts.db.ContactsDao;
import com.bh.contacts.model.Contact;
import com.bh.contacts.net.GetContactsRequest;
import com.bh.contacts.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactsLoader extends AsyncTaskLoader<ArbitraryPair<List<Contact>, Exception>> {
    private static List<Contact> contacts = new ArrayList<Contact>();

    private final boolean reset;
    private Context context;

    public ContactsLoader(Context context, boolean reset) {
        super(context);
        this.context = context;
        this.reset = reset;

        if (reset) {
            contacts.clear();
        }
    }

    @Override
    protected void onStartLoading() {
        if (!reset) {
            deliverResult(new ArbitraryPair<List<Contact>, Exception>(contacts, null));
            stopLoading();
            return;
        }

        forceLoad();
    }

    @Override
    public ArbitraryPair<List<Contact>, Exception> loadInBackground() {
        List<Contact> contactsFromDb = ContactsDao.getContacts(context);

        if (NetworkUtil.isConnectedToInternet(context)) {
            try {
                List<Contact> contactsFromServer = new GetContactsRequest().execute();
                for (Contact contact : contactsFromServer) {
                    if (!contactsFromDb.contains(contact)) {
                        ContactsDao.insert(context, contact);
                    }
                }
                return new ArbitraryPair<List<Contact>, Exception>(contactsFromServer, null);

            } catch (Exception e) {

            }
        }

        return new ArbitraryPair<List<Contact>, Exception>(contactsFromDb, null);
    }

    @Override
    public void deliverResult(ArbitraryPair<List<Contact>, Exception> result) {

        if (result.a != null) {
            if (contacts.size() == 0) {
                contacts.addAll(result.a);
            }
        }

        super.deliverResult(result);
    }

}

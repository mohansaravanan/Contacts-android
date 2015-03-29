package com.bh.contacts.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.bh.contacts.R;


public class ContactsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contacts);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, ContactsFragment.newInstance())
                    .commit();
        }
    }
}

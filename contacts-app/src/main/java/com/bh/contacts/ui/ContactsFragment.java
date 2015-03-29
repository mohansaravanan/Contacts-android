package com.bh.contacts.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bh.contacts.R;
import com.bh.contacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ArbitraryPair<List<Contact>, Exception>> {

    protected static final int MSG_NO_CONTACTS = 101;
    public static final String TAG = "FriendsFragment";
    private final ImageDownloader imageDownloader = new ImageDownloader();


    public static final ContactsFragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    private List<Contact> contacts;
    private List<Contact> filteredContacts;
    private boolean reset;

    private ContactListAdapter listAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
            reset = true;
        }

        this.listAdapter = new ContactListAdapter(getActivity(), new ArrayList<Contact>(), R.layout.item_contact, this);
        setListAdapter(this.listAdapter);

        setListShown(false);
        reset = true;

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<ArbitraryPair<List<Contact>, Exception>> onCreateLoader(int arg0, Bundle arg1) {
        return new ContactsLoader(getActivity(), reset);
    }

    @Override
    public void onListItemClick(ListView listView, View v, final int position, long id) {
        super.onListItemClick(listView, v, position, id);
        final Contact contact = listAdapter.getItem(position);
        startActivity(Intents.createContactDetailIntent(getActivity(), contact));
    }

    @Override
    public void onLoadFinished(Loader<ArbitraryPair<List<Contact>, Exception>> arg0, ArbitraryPair<List<Contact>, Exception> data) {

        if (data.b == null) {
            reset = false;

            this.contacts = data.a;
            this.filteredContacts = new ArrayList<Contact>(contacts.size());
            this.filteredContacts.addAll(contacts);

            listAdapter.setData(filteredContacts);

            if (contacts.size() == 0) {
                handler.sendEmptyMessage(MSG_NO_CONTACTS);
            }

            if (isResumed()) {
                setListShown(true);
            } else {
                setListShownNoAnimation(true);
            }
        } else {
            Toast.makeText(getActivity(), data.b.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onLoaderReset(Loader<ArbitraryPair<List<Contact>, Exception>> arg0) {
    }

    public final class ContactListAdapter extends ItemListAdapter<Contact> {

        public ContactListAdapter(Context context, List<Contact> items, int itemLayoutId, final ContactsFragment contactsFragment) {
            super(context, items, itemLayoutId, new ItemBinder<Contact>() {
                @Override
                public void bind(int position, View convertView, ViewGroup parent, final Contact item) {
                    ContactListItemViewHolder holder = ContactListItemViewHolder.getFromView(convertView);
                    holder.titleView.setText(item.getFirstName() + " " + item.getSurname());

                    if (item.getAvatar() != null && item.getAvatar().length() > 0) {
                        imageDownloader.download(item.getAvatar(), holder.contactImage);
                    } else {
                        holder.contactImage.setImageResource(R.drawable.ic_launcher);
                    }
                }
            });
        }

        public void setData(List<Contact> data) {
            clear();
            if (data != null) {
                for (Contact item : data) {
                    add(item);
                }
            }
        }
    }

    public static class ContactListItemViewHolder {

        TextView titleView;
        ImageView contactImage;

        public ContactListItemViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.item_title);
            contactImage = (ImageView) view.findViewById(R.id.contact_avatar_image);
        }

        public static ContactListItemViewHolder getFromView(View view) {
            ContactListItemViewHolder holder = (ContactListItemViewHolder) view.getTag();

            if (holder == null) {
                holder = new ContactListItemViewHolder(view);
                view.setTag(holder);
            }

            return holder;
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_NO_CONTACTS) {

            }
        }
    };

    public ContactListAdapter getListAdapter() {
        return listAdapter;
    }
}

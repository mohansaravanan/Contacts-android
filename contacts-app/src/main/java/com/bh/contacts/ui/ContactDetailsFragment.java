package com.bh.contacts.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bh.contacts.R;
import com.bh.contacts.model.Contact;


public class ContactDetailsFragment extends Fragment {

    private ImageView avatarImageView;
    private TextView firstName;
    private TextView surname;
    private TextView address;
    private TextView phoneNumber;
    private TextView email;
    private TextView createdAt;
    private TextView updatedAt;

    private final ImageDownloader imageDownloader = new ImageDownloader();

    private Contact contact;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        getActivity().setTitle(R.string.contact_detail);
        contact = (Contact) intent.getParcelableExtra(Intents.CONTACT_PARCEL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        avatarImageView = (ImageView) getView().findViewById(R.id.contact_avatar);
        firstName = (TextView) getView().findViewById(R.id.first_name);
        surname = (TextView) getView().findViewById(R.id.surname);
        address = (TextView) getView().findViewById(R.id.address);
        phoneNumber = (TextView) getView().findViewById(R.id.phone_number);
        email = (TextView) getView().findViewById(R.id.email);
        createdAt = (TextView) getView().findViewById(R.id.created_at);
        updatedAt = (TextView) getView().findViewById(R.id.updated_at);

        bindData(contact);
    }

    public void bindData(Contact contact) {

        firstName.setText(contact.getFirstName());
        surname.setText(contact.getSurname());
        address.setText(contact.getAddress());
        phoneNumber.setText(contact.getPhoneNumber());
        email.setText(contact.getEmail());
        createdAt.setText(contact.getCreatedAt().toString());
        updatedAt.setText(contact.getUpdatedAt().toString());
        if (contact.getAvatar() != null && contact.getAvatar().length() > 0) {
            imageDownloader.download(contact.getAvatar(), avatarImageView);
        }
    }

}

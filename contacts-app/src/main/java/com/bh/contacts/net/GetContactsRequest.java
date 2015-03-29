package com.bh.contacts.net;

import com.bh.contacts.model.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GetContactsRequest extends BHHttpRequest<List<Contact>> {

    public static final String JS_FIRST_NAME = "first_name";
    public static final String JS_SURNAME = "surname";
    public static final String JS_ADDRESS = "address";
    public static final String JS_PHONE_NUMBER = "phone_number";
    public static final String JS_EMAIL = "email";
    public static final String JS_CREATED_AT = "createdAt";
    public static final String JS_UPDATED_AT = "updatedAt";
    public static final String JS_ID = "id";
    private static String CONTACT_SERVICE = "http://fast-gorge.herokuapp.com/contacts";
    private static String AVATAR_URL_PREFIX = "http://api.adorable.io/avatars/285/";

    public static final String EMPTY_STRING = "";

    public GetContactsRequest() {
        super(CONTACT_SERVICE);
    }

    @Override
    protected List<Contact> handleResponse(String response) throws JSONException {

        List<Contact> contacts = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(response);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject contactJson = (JSONObject) jsonArray.get(i);

            Long id = isNotNull(contactJson, JS_ID) ? Long.valueOf((Integer) contactJson.get(JS_ID)) : null;
            if (id != null) {
                String firstName = isNotNull(contactJson, JS_FIRST_NAME) ? (String) contactJson.get(JS_FIRST_NAME) : EMPTY_STRING;
                String surname = isNotNull(contactJson, JS_SURNAME) ? (String) contactJson.get(JS_SURNAME) : EMPTY_STRING;
                String address = isNotNull(contactJson, JS_ADDRESS) ? (String) contactJson.get(JS_ADDRESS) : EMPTY_STRING;
                String phoneNumber = isNotNull(contactJson, JS_PHONE_NUMBER) ? (String) contactJson.get(JS_PHONE_NUMBER) : EMPTY_STRING;
                String email = isNotNull(contactJson, JS_EMAIL) ? (String) contactJson.get(JS_EMAIL) : EMPTY_STRING;
                Date createdAt = isNotNull(contactJson, JS_CREATED_AT) ? convertTimestampIntoMillisecond((String) contactJson.get(JS_CREATED_AT)) : new Date();
                Date updatedAt = isNotNull(contactJson, JS_UPDATED_AT) ? convertTimestampIntoMillisecond((String) contactJson.get(JS_UPDATED_AT)) : new Date();

                Contact contact = new Contact.ContactBuilder().
                        withId(id)
                        .withFirstName(firstName)
                        .withSurname(surname)
                        .withAddress(address)
                        .withPhoneNumber(phoneNumber)
                        .withEmail(email)
                        .withCreatedAt(createdAt)
                        .withUpdatedAt(updatedAt)
                        .withAvatar(AVATAR_URL_PREFIX + email + ".png")
                        .build();
                contacts.add(contact);

            }

        }
        return contacts;
    }

    private boolean isNotNull(JSONObject jsonObject, String field) {

        if (jsonObject.has(field)) {
            return true;
        }
        return false;
    }

    private Date convertTimestampIntoMillisecond(String timestamp) {

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = dateFormat.parse(timestamp);
            return date;
        } catch (ParseException e) {

        }
        return new Date();
    }
}

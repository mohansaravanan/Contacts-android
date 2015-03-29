package com.bh.contacts.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Contact implements Parcelable {

    private static final int PARCEL_VERSION = 1;

    public static final Creator<Contact> CREATOR = new ContactCreator();

    private Long id;
    private String firstName;
    private String surname;
    private String address;
    private String phoneNumber;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private String avatar;

    private Contact(ContactBuilder contactBuilder) {
        this.id = contactBuilder.id;
        this.firstName = contactBuilder.firstName;
        this.surname = contactBuilder.surname;
        this.address = contactBuilder.address;
        this.phoneNumber = contactBuilder.phoneNumber;
        this.email = contactBuilder.email;
        this.createdAt = contactBuilder.createdAt;
        this.updatedAt = contactBuilder.updatedAt;
        this.avatar = contactBuilder.avatar;
    }


    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;

        return true;
    }


    private static class ContactCreator implements Creator<Contact>{

        @Override
        public Contact createFromParcel(Parcel parcel) {
            int version = parcel.readInt();
            if (version != PARCEL_VERSION) {
                throw new IllegalArgumentException("Cannot un parcel contact version " + version);
            }


            return new Contact.ContactBuilder().
                    withId(parcel.readLong())
                    .withFirstName(parcel.readString())
                    .withSurname(parcel.readString())
                    .withAddress(parcel.readString())
                    .withPhoneNumber(parcel.readString())
                    .withEmail(parcel.readString())
                    .withAvatar(parcel.readString())
                    .withCreatedAt(new Date(parcel.readLong()))
                    .withUpdatedAt(new Date(parcel.readLong()))
                    .build();

        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(PARCEL_VERSION);
        parcel.writeLong(id);
        parcel.writeString(firstName);
        parcel.writeString(surname);
        parcel.writeString(address);
        parcel.writeString(phoneNumber);
        parcel.writeString(email);
        parcel.writeString(avatar);
        parcel.writeLong(createdAt.getTime());
        parcel.writeLong(updatedAt.getTime());

    }

    public static class ContactBuilder implements Builder<Contact> {

        private Long id;
        private String firstName;
        private String surname;
        private String address;
        private String phoneNumber;
        private String email;
        private Date createdAt;
        private Date updatedAt;
        private String avatar;

        public ContactBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public ContactBuilder withFirstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public ContactBuilder withSurname(final String surname) {
            this.surname = surname;
            return this;
        }

        public ContactBuilder withAddress(final String address) {
            this.address = address;
            return this;
        }

        public ContactBuilder withPhoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public ContactBuilder withEmail(final String email) {
            this.email = email;
            return this;
        }

        public ContactBuilder withUpdatedAt(final Date updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public ContactBuilder withCreatedAt(final Date createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public ContactBuilder withAvatar(final String avatar) {
            this.avatar = avatar;
            return this;
        }

        @Override
        public Contact build() {
            return new Contact(this) ;
        }
    }
}

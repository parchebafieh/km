package com.qusci.km.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created with IntelliJ IDEA.
 * User: pedram
 * Date: 10/4/14
 * Time: 12:21 AM
 */
public class Book implements Parcelable {

    String name;
    String authors;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(authors);

    }

    public Book() {
    }

    public Book(Parcel in) {
        name = in.readString();
        authors = in.readString();
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

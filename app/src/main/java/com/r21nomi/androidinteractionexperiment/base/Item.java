package com.r21nomi.androidinteractionexperiment.base;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by r21nomi on 2016/12/11.
 */

public class Item implements Parcelable {
    private String title;
    private String description;
    private Uri thumb;

    public Item(String title, String description, Uri thumb) {
        this.title = title;
        this.description = description;
        this.thumb = thumb;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Uri getThumb() {
        return thumb;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeParcelable(this.thumb, flags);
    }

    protected Item(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.thumb = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel source) {
            return new Item(source);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}

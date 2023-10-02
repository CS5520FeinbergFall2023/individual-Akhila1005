package edu.northeastern.numad23fa_akhilachiguluri;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Patterns;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class LcItem implements Parcelable {
    private String itemName;
    private String itemUrl;

    public LcItem(String itemName, String itemUrl) {
        this.itemName = itemName;
        this.itemUrl = itemUrl;
    }

    public void onLinkUnitClicked(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl));
        context.startActivity(browserIntent);
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemUrl() {
        return itemUrl;
    }

    public boolean isValid() {
        try {
            new URL(itemUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(itemUrl).matches();
    }


    protected LcItem(Parcel in) {
        itemName = in.readString();
        itemUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LcItem> CREATOR = new Creator<LcItem>() {
        @Override
        public LcItem createFromParcel(Parcel in) {
            return new LcItem(in);
        }

        @Override
        public LcItem[] newArray(int size) {
            return new LcItem[size];
        }
    };
}

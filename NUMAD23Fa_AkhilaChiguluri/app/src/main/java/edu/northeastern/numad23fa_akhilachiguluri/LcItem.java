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
    private String linkName;
    private String linkUrl;

    public LcItem(String linkName, String linkUrl) {
        this.linkName = linkName;
        this.linkUrl = linkUrl;
    }

    public void onLinkUnitClicked(Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl));
        context.startActivity(browserIntent);
    }

    public String getItemName() {
        return linkName;
    }

    public String getItemUrl() {
        return linkUrl;
    }

    public boolean isValid() {
        try {
            new URL(linkUrl).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return Patterns.WEB_URL.matcher(linkUrl).matches();
    }


    protected LcItem(Parcel in) {
        linkName = in.readString();
        linkUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(linkName);
        dest.writeString(linkUrl);
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

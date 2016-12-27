package com.r21nomi.androidinteractionexperiment.shared_element_transition.detail;

import android.net.Uri;

/**
 * Created by r21nomi on 2016/12/27.
 */

public class HeaderThumb {

    private boolean mIsVisible;
    private Uri mUri;

    public HeaderThumb(Uri mUri, boolean mIsVisible) {
        this.mUri = mUri;
        this.mIsVisible = mIsVisible;
    }

    public boolean isVisible() {
        return mIsVisible;
    }

    public void setIsVisible(boolean mIsVisible) {
        this.mIsVisible = mIsVisible;
    }

    public Uri getUri() {
        return mUri;
    }

    public void setUri(Uri mUri) {
        this.mUri = mUri;
    }
}

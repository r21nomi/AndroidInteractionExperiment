package com.r21nomi.androidinteractionexperiment.base.view;

import android.app.Activity;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.r21nomi.androidinteractionexperiment.R;

/**
 * Created by r21nomi on 2016/12/30.
 */

public class ViewUtil {

    public static View getActionBar(Activity activity) {
        View decor = activity.getWindow().getDecorView();
        int actionBarId = R.id.action_bar_container;
        View actionBar = decor.findViewById(actionBarId);
        ViewCompat.setTransitionName(actionBar, "action_bar");

        return actionBar;
    }
}

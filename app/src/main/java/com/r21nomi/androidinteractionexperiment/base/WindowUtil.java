package com.r21nomi.androidinteractionexperiment.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Ryota Niinomi on 2016/12/11.
 */

public class WindowUtil {
    public static int getWidth(final Context context) {
        final Display display = getDisplay(context);
        final android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);
        return size.x;
    }

    public static int getHeight(final Context context) {
        final Display display = getDisplay(context);
        final android.graphics.Point size = new android.graphics.Point();
        display.getSize(size);
        return size.y;
    }

    private static Display getDisplay(final Context context) {
        return ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect rect = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }
}

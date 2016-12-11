package com.r21nomi.androidinteractionexperiment.shared_element_transition;

import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.os.Build;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;

import com.github.r21nomi.androidrpinterpolator.Easing;
import com.github.r21nomi.androidrpinterpolator.RPInterpolator;

/**
 * Created by Ryota Niinomi on 2016/11/19.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class CustomTransitionSet extends TransitionSet {
    public CustomTransitionSet() {
        addTransition(new ChangeBounds().setInterpolator(getInterpolator()));
    }

    public TimeInterpolator getInterpolator() {
        return new RPInterpolator(Easing.BACK_OUT);
    }
}

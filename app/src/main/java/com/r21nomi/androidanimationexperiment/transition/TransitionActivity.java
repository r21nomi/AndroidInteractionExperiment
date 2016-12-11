package com.r21nomi.androidanimationexperiment.transition;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.transition.ChangeBounds;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.r21nomi.androidrpinterpolator.Easing;
import com.github.r21nomi.androidrpinterpolator.RPInterpolator;
import com.r21nomi.androidanimationexperiment.R;

import static android.widget.RelativeLayout.ALIGN_PARENT_BOTTOM;
import static android.widget.RelativeLayout.ALIGN_PARENT_LEFT;
import static android.widget.RelativeLayout.ALIGN_PARENT_RIGHT;
import static android.widget.RelativeLayout.ALIGN_PARENT_TOP;

public class TransitionActivity extends AppCompatActivity {

    private View animationView;
    private ViewGroup sceneRoot;
    private boolean isLeftTop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        sceneRoot = (ViewGroup) findViewById(R.id.root);
        animationView = findViewById(R.id.animationButton1);

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
    }

    private void startAnimation() {
        Transition t = new ChangeBounds();
        t.setInterpolator(new RPInterpolator(Easing.QUART_IN_OUT));
        t.setDuration(500);
        t.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(@NonNull Transition transition) {
                Log.d(this.getClass().getCanonicalName(), "onTransitionStart");
            }

            @Override
            public void onTransitionEnd(@NonNull Transition transition) {
                Log.d(this.getClass().getCanonicalName(), "onTransitionEnd");
            }

            @Override
            public void onTransitionCancel(@NonNull Transition transition) {
                Log.d(this.getClass().getCanonicalName(), "onTransitionCancel");
            }

            @Override
            public void onTransitionPause(@NonNull Transition transition) {
                Log.d(this.getClass().getCanonicalName(), "onTransitionPause");
            }

            @Override
            public void onTransitionResume(@NonNull Transition transition) {
                Log.d(this.getClass().getCanonicalName(), "onTransitionResume");
            }
        });

        TransitionManager.beginDelayedTransition(sceneRoot, t);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) animationView.getLayoutParams();
        params.addRule(ALIGN_PARENT_LEFT, isLeftTop ? 0 : 1);
        params.addRule(ALIGN_PARENT_TOP, isLeftTop ? 0 : 1);
        params.addRule(ALIGN_PARENT_RIGHT, isLeftTop ? 1 : 0);
        params.addRule(ALIGN_PARENT_BOTTOM, isLeftTop ? 1 : 0);

        animationView.setLayoutParams(params);

        isLeftTop = !isLeftTop;
    }
}

package com.r21nomi.androidinteractionexperiment.shared_element_transition.detail;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.github.r21nomi.androidrpinterpolator.Easing;
import com.github.r21nomi.androidrpinterpolator.RPInterpolator;
import com.r21nomi.androidinteractionexperiment.DeviceUtil;
import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.ResourceUtil;
import com.r21nomi.androidinteractionexperiment.WindowUtil;
import com.r21nomi.androidinteractionexperiment.evaluator.HeightEvaluator;
import com.r21nomi.androidinteractionexperiment.evaluator.WidthEvaluator;
import com.r21nomi.androidinteractionexperiment.activity_transition.Item;

import java.util.ArrayList;
import java.util.List;

public class SharedElementTransitionDetailActivity extends AppCompatActivity {

    private static final String X = "x";
    private static final String Y = "y";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String URI = "uri";
    private static final int DURATION = 500;

    private RecyclerView mRecyclerView;
    private ImageView mAnimationView;
    private HeaderThumb mHeaderThumb;
    private ItemDetailAdapter mAdapter;

    private ItemDetailAdapter.Listener mListener = new ItemDetailAdapter.Listener() {
        @Override
        public void onThumbClicked(Uri uri) {
            // no-op
        }

        @Override
        public void onItemClicked(Item item) {
            // no-op
        }

        @Override
        public void onStartPostponedEnterTransition() {
            if (DeviceUtil.isOverLollipop()) {
                startPostponedEnterTransition();
            }
        }
    };

    public static Intent createIntent(Context context, View view, Uri sharedElementUri) {
        Intent intent = new Intent(context, SharedElementTransitionDetailActivity.class);
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        int x = screenLocation[0];
        int y = screenLocation[1];
        int width = view.getWidth();
        int height = view.getHeight();

        intent.putExtra(X, x);
        intent.putExtra(Y, y);
        intent.putExtra(WIDTH, width);
        intent.putExtra(HEIGHT, height);
        intent.putExtra(URI, sharedElementUri);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_transition_detail);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAnimationView = (ImageView) findViewById(R.id.animationView);
        mHeaderThumb = new HeaderThumb((Uri) getIntent().getParcelableExtra(URI), false);

        mAdapter = new ItemDetailAdapter(
                mHeaderThumb,
                getDataSet()
        );
        mAdapter.setListener(mListener);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        initAnimation();
    }

    private List<Item> getDataSet() {
        List<Item> dataSet = new ArrayList<>();
        for (int i = 0, len = 15; i < len; i++) {
            dataSet.add(new Item("Item" + i, "description" + i, ResourceUtil.getDrawableAsUri(this, R.drawable.img1)));
        }
        return dataSet;
    }

    private void initAnimation() {
        mAnimationView.setImageURI((Uri) getIntent().getParcelableExtra(URI));
        mAnimationView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mAnimationView.getViewTreeObserver().removeOnPreDrawListener(this);
                mAnimationView.setVisibility(View.VISIBLE);

                int x = getIntent().getIntExtra(X, 0);
                int y = getIntent().getIntExtra(Y, 0);
                int width = getIntent().getIntExtra(WIDTH, 0);
                int height = getIntent().getIntExtra(HEIGHT, 0);
                int targetWidth = WindowUtil.getWidth(SharedElementTransitionDetailActivity.this);
                int targetHeight = targetWidth * width / height;
                int toolBarHeight = getResources().getDimensionPixelSize(R.dimen.toolbar_height);
                int statusBarHeight = WindowUtil.getStatusBarHeight(SharedElementTransitionDetailActivity.this);

                // Position
                mAnimationView.setTranslationX(x);
                mAnimationView.setTranslationY(y - toolBarHeight - statusBarHeight);

                // Size
                ViewGroup.LayoutParams params = mAnimationView.getLayoutParams();
                params.width = width;
                params.height = height;
                mAnimationView.setLayoutParams(params);

                // Animation
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(
                        ObjectAnimator.ofFloat(mAnimationView, "translationX", 0),
                        ObjectAnimator.ofFloat(mAnimationView, "translationY", 0),
                        ValueAnimator.ofObject(new WidthEvaluator(mAnimationView), width, targetWidth),
                        ValueAnimator.ofObject(new HeightEvaluator(mAnimationView), height, targetHeight)
                );
                animSet.addListener(new android.animation.AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(android.animation.Animator animation) {
                        super.onAnimationEnd(animation);

                        mHeaderThumb.setIsVisible(true);
                        // Use notifyDataSetChanged since if you use notifyItemChanged(0), the screen blink a little after finish animation.
                        mAdapter.notifyDataSetChanged();
                        mAnimationView.setVisibility(View.GONE);
                    }
                });
                animSet.setDuration(DURATION);
                animSet.setStartDelay(100);
                animSet.setInterpolator(new RPInterpolator(Easing.EXPO_IN_OUT));
                animSet.start();

                return true;
            }
        });
    }
}

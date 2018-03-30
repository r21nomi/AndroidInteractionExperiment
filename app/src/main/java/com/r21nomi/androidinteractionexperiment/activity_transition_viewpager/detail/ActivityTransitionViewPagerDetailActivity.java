package com.r21nomi.androidinteractionexperiment.activity_transition_viewpager.detail;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;

import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.activity_transition.CustomTransitionSet;
import com.r21nomi.androidinteractionexperiment.activity_transition_viewpager.ItemPagerAdapter;
import com.r21nomi.androidinteractionexperiment.base.DeviceUtil;
import com.r21nomi.androidinteractionexperiment.base.Item;
import com.r21nomi.androidinteractionexperiment.base.view.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActivityTransitionViewPagerDetailActivity extends AppCompatActivity {

    public static final String POSITION = "position";
    private static final String SHARED_ELEMENT_VIEW_NAME = "shared_element_view_name";
    private static final String DATA_SET = "data_set";

    private ViewPager mViewPager;
    private String mSharedElementViewName;

    public static Intent createIntent(Context context, String sharedElementViewName, List<Item> dataSet, int position) {
        Intent intent = new Intent(context, ActivityTransitionViewPagerDetailActivity.class);
        intent.putExtra(SHARED_ELEMENT_VIEW_NAME, sharedElementViewName);
        intent.putExtra(POSITION, position);
        intent.putParcelableArrayListExtra(DATA_SET, new ArrayList<>(dataSet));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_transition_viewpager_detail);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mSharedElementViewName = getIntent().getStringExtra(SHARED_ELEMENT_VIEW_NAME);

        int position = getIntent().getIntExtra(POSITION, 0);
        List<Item> dataSet = new ArrayList<>();
        for (Parcelable item : getIntent().getParcelableArrayListExtra(DATA_SET)) {
            dataSet.add((Item) item);
        }
        ItemPagerAdapter adapter = new ItemPagerAdapter(dataSet);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setResultPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (DeviceUtil.isOverLollipop()) {
            supportPostponeEnterTransition();
            getWindow().setSharedElementEnterTransition(new CustomTransitionSet());


            // Set transition name to ActionBar.
            ViewUtil.getActionBar(this);

            mViewPager.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public boolean onPreDraw() {
                    mViewPager.getViewTreeObserver().removeOnPreDrawListener(this);
                    supportStartPostponedEnterTransition();
                    return true;
                }
            });

            setEnterSharedElementCallback(new SharedElementCallback() {
                @Override
                public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                    int position = mViewPager.getCurrentItem();
                    View view = mViewPager.findViewWithTag(ItemPagerAdapter.getTag(position));
                    sharedElements.clear();
                    sharedElements.put(mSharedElementViewName, view);
                }
            });
        }

        setResultPosition(position);
    }

    private void setResultPosition(int position) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        intent.putExtras(bundle);

        setResult(Activity.RESULT_OK, intent);
    }
}

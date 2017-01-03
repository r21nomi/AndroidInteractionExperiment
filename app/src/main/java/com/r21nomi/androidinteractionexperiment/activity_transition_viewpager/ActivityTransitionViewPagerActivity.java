package com.r21nomi.androidinteractionexperiment.activity_transition_viewpager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.activity_transition_viewpager.detail.ActivityTransitionViewPagerDetailActivity;
import com.r21nomi.androidinteractionexperiment.base.DeviceUtil;
import com.r21nomi.androidinteractionexperiment.base.Item;
import com.r21nomi.androidinteractionexperiment.base.ItemCardAdapter;
import com.r21nomi.androidinteractionexperiment.base.ResourceUtil;
import com.r21nomi.androidinteractionexperiment.base.view.ViewUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ActivityTransitionViewPagerActivity extends AppCompatActivity {

    private static final String SHARED_ELEMENT_VIEW_NAME = "shared_element_view";
    private RecyclerView mRecyclerView;
    private ItemCardAdapter mAdapter;
    private GridLayoutManager mGridLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new ItemCardAdapter(getDataSet(), new ItemCardAdapter.Listener() {
            @Override
            public void onClick(View thumbView, int position) {
                startDetail(thumbView, position);
            }
        });

        mGridLayoutManager = new GridLayoutManager(this, 2);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);

        if (resultCode == Activity.RESULT_OK && DeviceUtil.isOverLollipop()) {
            final int dataSetPosition = data.getIntExtra(ActivityTransitionViewPagerDetailActivity.POSITION, 0);

            if (dataSetPosition < mGridLayoutManager.findFirstVisibleItemPosition()
                    || dataSetPosition > mGridLayoutManager.findLastVisibleItemPosition()) {
                supportPostponeEnterTransition();
                mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dataSetPosition >= mGridLayoutManager.findFirstVisibleItemPosition()
                                && dataSetPosition <= mGridLayoutManager.findLastVisibleItemPosition()) {
                            // TODO: Does not work.
                            setSharedElementViewForExit(dataSetPosition);
                            supportStartPostponedEnterTransition();
                            mRecyclerView.removeOnScrollListener(this);
                        }
                    }
                });
                mRecyclerView.scrollToPosition(dataSetPosition);
            } else {
                setSharedElementViewForExit(dataSetPosition);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setSharedElementViewForExit(final int dataSetPosition) {
        ItemCardAdapter.ViewHolder viewHolder = (ItemCardAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(dataSetPosition);
        final View sharedElementView = (viewHolder == null) ? null : viewHolder.thumb;

        setExitSharedElementCallback(new SharedElementCallback() {
            @Override
            public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                sharedElements.clear();
                sharedElements.put(SHARED_ELEMENT_VIEW_NAME, sharedElementView);
                setExitSharedElementCallback((SharedElementCallback) null);
            }
        });
    }

    private int getCurrentItemRecyclerViewPosition(int currentItemPositionOnDataSet) {
        int firstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition();
        return currentItemPositionOnDataSet - firstVisibleItemPosition;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startDetail(View thumbView, int position) {
        Intent intent = ActivityTransitionViewPagerDetailActivity.createIntent(this, SHARED_ELEMENT_VIEW_NAME, mAdapter.getDataSet(), position);

        View statusBar = findViewById(android.R.id.statusBarBackground);
        View navigationBar = findViewById(android.R.id.navigationBarBackground);
        View actionBar = ViewUtil.getActionBar(this);

        List<Pair<View, String>> pairs = new ArrayList<>();
        if (statusBar != null) {
            pairs.add(Pair.create(statusBar, statusBar.getTransitionName()));
        }
        if (navigationBar != null) {
            pairs.add(Pair.create(navigationBar, navigationBar.getTransitionName()));
        }
        if (actionBar != null) {
            pairs.add(Pair.create(actionBar, actionBar.getTransitionName()));
        }
        pairs.add(Pair.create(thumbView, SHARED_ELEMENT_VIEW_NAME));

        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                pairs.toArray(new Pair[pairs.size()])
        ).toBundle();
        startActivity(intent, options);
    }

    private List<Item> getDataSet() {
        List<Item> dataSet = Arrays.asList(
                new Item("Item 1", "description 1", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 2", "description 2", ResourceUtil.getDrawableAsUri(this, R.drawable.img2)),
                new Item("Item 3", "description 3", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 4", "description 4", ResourceUtil.getDrawableAsUri(this, R.drawable.img2)),
                new Item("Item 5", "description 5", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 6", "description 6", ResourceUtil.getDrawableAsUri(this, R.drawable.img2)),
                new Item("Item 7", "description 7", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 8", "description 8", ResourceUtil.getDrawableAsUri(this, R.drawable.img2)),
                new Item("Item 9", "description 9", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 10", "description 10", ResourceUtil.getDrawableAsUri(this, R.drawable.img2)),
                new Item("Item 11", "description 11", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 12", "description 12", ResourceUtil.getDrawableAsUri(this, R.drawable.img2))
        );
        return dataSet;
    }
}

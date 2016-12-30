package com.r21nomi.androidinteractionexperiment.activity_transition.detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.util.Log;

import com.r21nomi.androidinteractionexperiment.base.DeviceUtil;
import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.base.ResourceUtil;
import com.r21nomi.androidinteractionexperiment.activity_transition.CustomTransitionSet;
import com.r21nomi.androidinteractionexperiment.base.Item;

import java.util.ArrayList;
import java.util.List;

public class ActivityTransitionDetailActivity extends AppCompatActivity {

    private static final String SHARED_ELEMENT_VIEW_NAME = "shared_element_view_name";
    private static final String URI = "uri";

    private RecyclerView mRecyclerView;

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

    public static Intent createIntent(Context context, String sharedElementViewName, Uri sharedElementUri) {
        Intent intent = new Intent(context, ActivityTransitionDetailActivity.class);
        intent.putExtra(SHARED_ELEMENT_VIEW_NAME, sharedElementViewName);
        intent.putExtra(URI, sharedElementUri);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition_detail);

        if (DeviceUtil.isOverLollipop()) {
            postponeEnterTransition();
            getWindow().setSharedElementEnterTransition(new CustomTransitionSet());

            getWindow()
                    .getSharedElementEnterTransition()
                    .addListener(new Transition.TransitionListener() {
                        @Override
                        public void onTransitionStart(Transition transition) {
                            Log.d("TransitionListener", "onTransitionStart");
                        }

                        @Override
                        public void onTransitionEnd(Transition transition) {
                            Log.d("TransitionListener", "onTransitionEnd");
                        }

                        @Override
                        public void onTransitionCancel(Transition transition) {
                            Log.d("TransitionListener", "onTransitionCancel");
                        }

                        @Override
                        public void onTransitionPause(Transition transition) {
                            Log.d("TransitionListener", "onTransitionPause");
                        }

                        @Override
                        public void onTransitionResume(Transition transition) {
                            Log.d("TransitionListener", "onTransitionResume");
                        }
                    });

//            android.transition.Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.changebounds_with_arcmotion);
//            getWindow().setSharedElementEnterTransition(transition);
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        ItemDetailAdapter adapter = new ItemDetailAdapter(
                (Uri) getIntent().getParcelableExtra(URI),
                getDataSet(),
                getIntent().getStringExtra(SHARED_ELEMENT_VIEW_NAME)
        );
        adapter.setListener(mListener);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private List<Item> getDataSet() {
        List<Item> dataSet = new ArrayList<>();
        for (int i = 0, len = 15; i < len; i++) {
            dataSet.add(new Item("Item" + i, "description" + i, ResourceUtil.getDrawableAsUri(this, R.drawable.img1)));
        }
        return dataSet;
    }
}

package com.r21nomi.androidanimationexperiment.shared_element_transition;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.r21nomi.androidanimationexperiment.DeviceUtil;
import com.r21nomi.androidanimationexperiment.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SharedElementTransitionDetailActivity extends AppCompatActivity {

    private static final String TRANSITION_NAME = "transition_name";

    private RecyclerView mRecyclerView;

    private ItemAdapter.Listener mListener = new ItemAdapter.Listener() {
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

    public static Intent createIntent(Context context, String transitionName) {
        Intent intent = new Intent(context, SharedElementTransitionDetailActivity.class);
        intent.putExtra(TRANSITION_NAME, transitionName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element_detail);

        if (DeviceUtil.isOverLollipop()) {
            postponeEnterTransition();
            getWindow().setSharedElementEnterTransition(new CustomTransitionSet());
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        Uri thumbUri = Uri.parse(String.format(Locale.US,
                "android.resource://%s/%d",
                getPackageName(),
                R.drawable.img1
        ));

        ItemAdapter adapter = new ItemAdapter(
                thumbUri,
                getDataSet(),
                getIntent().getStringExtra(TRANSITION_NAME)
        );
        adapter.setListener(mListener);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    private List<Item> getDataSet() {
        List<Item> dataSet = new ArrayList<>();
        for (int i = 0, len = 15; i < len; i++) {
            dataSet.add(new Item("Item" + i, "description" + i));
        }
        return dataSet;
    }
}

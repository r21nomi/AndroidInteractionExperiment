package com.r21nomi.androidanimationexperiment.shared_element_transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.r21nomi.androidanimationexperiment.R;
import com.r21nomi.androidanimationexperiment.ResourceUtil;
import com.r21nomi.androidanimationexperiment.shared_element_transition.detail.SharedElementTransitionDetailActivity;

import java.util.Arrays;
import java.util.List;

public class SharedElementTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ItemAdapter adapter = new ItemAdapter(getDataSet(), new ItemAdapter.Listener() {
            @Override
            public void onClick(View thumbView, Item item) {
                startDetail(thumbView, item);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void startDetail(View thumbView, Item item) {
        String transitionName = "image";
        Intent intent = SharedElementTransitionDetailActivity.createIntent(this, transitionName, item.getThumb());
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                thumbView,
                transitionName
        ).toBundle();
        startActivity(intent, options);
    }

    private List<Item> getDataSet() {
        List<Item> dataSet = Arrays.asList(
                new Item("Item 1", "description 1", ResourceUtil.getDrawableAsUri(this, R.drawable.img1)),
                new Item("Item 2", "description 2", ResourceUtil.getDrawableAsUri(this, R.drawable.img2))
        );
        return dataSet;
    }
}

package com.r21nomi.androidinteractionexperiment.activity_transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.ResourceUtil;
import com.r21nomi.androidinteractionexperiment.activity_transition.detail.ActivityTransitionDetailActivity;

import java.util.Arrays;
import java.util.List;

public class ActivityTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

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
        String sharedElementViewName = "shared_element_view";
        Intent intent = ActivityTransitionDetailActivity.createIntent(this, sharedElementViewName, item.getThumb());
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                thumbView,
                sharedElementViewName
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
                new Item("Item 8", "description 8", ResourceUtil.getDrawableAsUri(this, R.drawable.img2))
        );
        return dataSet;
    }
}

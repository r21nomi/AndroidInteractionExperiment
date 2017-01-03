package com.r21nomi.androidinteractionexperiment.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.activity_transition.ActivityTransitionActivity;
import com.r21nomi.androidinteractionexperiment.activity_transition_viewpager.ActivityTransitionViewPagerActivity;
import com.r21nomi.androidinteractionexperiment.shared_element_transition.SharedElementTransitionActivity;
import com.r21nomi.androidinteractionexperiment.transition.TransitionActivity;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<Experiment> dataSet = Arrays.asList(
                new Experiment("ActivityTransition", ActivityTransitionActivity.class),
                new Experiment("ActivityTransitionViewPager", ActivityTransitionViewPagerActivity.class),
                new Experiment("SharedElementTransition", SharedElementTransitionActivity.class),
                new Experiment("Transition", TransitionActivity.class)
        );

        ExperimentsAdapter adapter = new ExperimentsAdapter(dataSet, new ExperimentsAdapter.Listener() {
            @Override
            public void onClick(Experiment item) {
                startActivity(new Intent(MainActivity.this, item.getaClass()));
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

package com.r21nomi.androidanimationexperiment.shared_element_transition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.r21nomi.androidanimationexperiment.R;

public class SharedElementTransitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_element);

        findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(view);
            }
        });
    }

    private void move(View view) {
        String transitionName = "image";
        Intent intent = SharedElementTransitionDetailActivity.createIntent(this, transitionName);
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                view,
                transitionName
        ).toBundle();
        startActivity(intent, options);
    }
}

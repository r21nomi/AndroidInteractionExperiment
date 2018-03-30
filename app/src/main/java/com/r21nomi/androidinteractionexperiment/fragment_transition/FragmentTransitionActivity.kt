package com.r21nomi.androidinteractionexperiment.fragment_transition

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.r21nomi.androidinteractionexperiment.R

/**
 * Created by Ryota Niinomi on 2018/03/27.
 */
class FragmentTransitionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_transition)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .add(R.id.content, FragmentTransitionFragment.newInstance())
                    .commit()
        }
    }
}
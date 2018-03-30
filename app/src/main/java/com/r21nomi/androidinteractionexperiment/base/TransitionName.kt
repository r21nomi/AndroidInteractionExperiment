package com.r21nomi.androidinteractionexperiment.base

/**
 * Created by Ryota Niinomi on 2018/03/30.
 */
sealed class TransitionName {

    data class FragmentTransition(
            val img1: String = "shared_element_1",
            val img2: String = "shared_element_2",
            val img3: String = "shared_element_3"
    ) : TransitionName()
}
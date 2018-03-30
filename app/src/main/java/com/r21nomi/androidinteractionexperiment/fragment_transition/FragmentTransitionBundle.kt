package com.r21nomi.androidinteractionexperiment.fragment_transition

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Ryota Niinomi on 2018/03/30.
 */
@SuppressLint("ParcelCreator")
@Parcelize
data class FragmentTransitionBundle(
        val uri1: Uri,
        val uri2: Uri,
        val uri3: Uri,
        val transitionNames: List<String>
) : Parcelable {

    fun getUri(): List<Uri> {
        return listOf(
                uri1,
                uri2,
                uri3
        )
    }
}
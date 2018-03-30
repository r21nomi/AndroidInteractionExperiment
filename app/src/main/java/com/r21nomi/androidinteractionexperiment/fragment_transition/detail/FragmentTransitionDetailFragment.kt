package com.r21nomi.androidinteractionexperiment.fragment_transition.detail

import android.annotation.TargetApi
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.transition.ChangeBounds
import android.support.transition.ChangeClipBounds
import android.support.transition.ChangeTransform
import android.support.transition.TransitionSet
import android.support.v4.app.Fragment
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.r21nomi.androidinteractionexperiment.R
import com.r21nomi.androidinteractionexperiment.fragment_transition.FragmentTransitionBundle

/**
 * Created by Ryota Niinomi on 2018/03/27.
 */
class FragmentTransitionDetailFragment : Fragment() {

    companion object {
        private const val KEY_BUNDLE = "key_bundle"

        fun newInstance(bundle: FragmentTransitionBundle): FragmentTransitionDetailFragment {
            return FragmentTransitionDetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_BUNDLE, bundle)
                }
            }
        }
    }

    private var bundle: FragmentTransitionBundle? = null
    private var rootView: View? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        postponeEnterTransition()
        sharedElementEnterTransition = TransitionSet().apply {
            duration = 500
            addTransition(ChangeClipBounds())
            addTransition(ChangeBounds())
            addTransition(ChangeTransform())
            // Do NOT add ChangeImageTransform not to change shape of the image inside ImageView.
//            addTransition(ChangeImageTransform())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            val view = inflater.inflate(R.layout.fragment_fragment_transition_detail, container, false)

            bundle = arguments?.getParcelable(KEY_BUNDLE)

            animate(listOf(
                    view.findViewById(R.id.firstImageView),
                    view.findViewById(R.id.secondImageView),
                    view.findViewById(R.id.thirdImageView)
            ))

            rootView = view
        }

        return rootView
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animate(views: List<ImageView>) {
        var count = 0

        val images = bundle?.getUri() ?: listOf()

        views.forEachIndexed { index, imageView ->
            ViewCompat.setTransitionName(imageView, bundle?.transitionNames?.get(index) ?: "")

            Glide.with(requireContext())
                    .load(images[index])
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?,
                                                  model: Any?,
                                                  target: com.bumptech.glide.request.target.Target<Drawable>?,
                                                  isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?,
                                                     model: Any?,
                                                     target: com.bumptech.glide.request.target.Target<Drawable>?,
                                                     dataSource: DataSource?,
                                                     isFirstResource: Boolean): Boolean {
                            imageView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                                override fun onPreDraw(): Boolean {
                                    imageView.viewTreeObserver.removeOnPreDrawListener(this)
                                    startPostponedEnterTransition(++count)
                                    return false
                                }
                            })
                            return false
                        }
                    })
                    .into(imageView)
        }
    }

    private fun startPostponedEnterTransition(count: Int) {
        if (count == 3) {
            startPostponedEnterTransition()
        }
    }
}
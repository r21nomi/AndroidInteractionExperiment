package com.r21nomi.androidinteractionexperiment.fragment_transition

import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.r21nomi.androidinteractionexperiment.R
import com.r21nomi.androidinteractionexperiment.base.Item
import com.r21nomi.androidinteractionexperiment.base.ItemsCardAdapter
import com.r21nomi.androidinteractionexperiment.base.ResourceUtil
import com.r21nomi.androidinteractionexperiment.fragment_transition.detail.FragmentTransitionDetailFragment
import java.util.*

/**
 * Created by Ryota Niinomi on 2018/03/27.
 */
class FragmentTransitionFragment : Fragment() {

    companion object {
        fun newInstance() = FragmentTransitionFragment()
    }

    private var itemCardAdapter: ItemsCardAdapter? = null
    private var rootView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            val view = inflater.inflate(R.layout.fragment_fragment_transition, container, false)

            itemCardAdapter = ItemsCardAdapter(getDataSet(), object : ItemsCardAdapter.Listener {
                override fun onClick(view: View, views: List<View>, position: Int) {
                    startDetail(view, views, position)
                }
            })

            view.findViewById<RecyclerView>(R.id.recyclerView).let { recyclerView ->
                recyclerView.layoutManager = GridLayoutManager(requireContext(), 2).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return 1
                        }
                    }
                }
                recyclerView.adapter = itemCardAdapter
            }

            rootView = view
        }

        return rootView
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startDetail(thumbView: View, views: List<View>, position: Int) {
        val uri = itemCardAdapter?.dataSet?.get(position)?.thumb ?: Uri.parse("")

        val bundle = FragmentTransitionBundle(
                uri,
                uri,
                uri,
                views.map { it.transitionName }
        )
        requireFragmentManager()
                .beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.content, FragmentTransitionDetailFragment.newInstance(bundle))
                .addToBackStack(null)
                .apply {
                    views.forEach {
                        addSharedElement(it, it.transitionName)
                    }
                }
                .commit()
    }

    private fun getDataSet(): List<Item> {
        return Arrays.asList(
                Item("Item 1", "description 1", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img1)),
                Item("Item 2", "description 2", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img2)),
                Item("Item 3", "description 3", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img1)),
                Item("Item 4", "description 4", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img2)),
                Item("Item 5", "description 5", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img1)),
                Item("Item 6", "description 6", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img2)),
                Item("Item 7", "description 7", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img1)),
                Item("Item 8", "description 8", ResourceUtil.getDrawableAsUri(requireContext(), R.drawable.img2))
        )
    }
}
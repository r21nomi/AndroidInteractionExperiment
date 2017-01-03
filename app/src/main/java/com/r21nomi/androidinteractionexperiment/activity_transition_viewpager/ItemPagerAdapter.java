package com.r21nomi.androidinteractionexperiment.activity_transition_viewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.r21nomi.androidinteractionexperiment.base.Item;

import java.util.List;

/**
 * Created by r21nomi on 2017/01/03.
 */

public class ItemPagerAdapter extends PagerAdapter {
    private static final String TAG_PREFIX = "tag_item";
    private List<Item> dataSet;

    public static String getTag(int position) {
        return TAG_PREFIX + position;
    }

    public ItemPagerAdapter(List<Item> dataSet){
        super();
        this.dataSet = dataSet;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public View instantiateItem(ViewGroup container, int position){
        ImageView imageview = new ImageView(container.getContext());
        imageview.setImageURI(dataSet.get(position).getThumb());
        imageview.setTag(getTag(position));
//            ViewCompat.setTransitionName(imageview, "item_"+position);
        container.addView(imageview);
        return imageview;
    }

    @Override
    public void destroyItem(ViewGroup collection, int pos, Object view) {
        collection.removeView((View)view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
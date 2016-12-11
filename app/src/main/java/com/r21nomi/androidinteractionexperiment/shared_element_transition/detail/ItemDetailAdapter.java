package com.r21nomi.androidinteractionexperiment.shared_element_transition.detail;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.r21nomi.androidinteractionexperiment.R;
import com.r21nomi.androidinteractionexperiment.WindowUtil;
import com.r21nomi.androidinteractionexperiment.shared_element_transition.Item;

import java.util.List;

/**
 * Created by r21nomi on 2016/12/11.
 */

public class ItemDetailAdapter extends RecyclerView.Adapter {

    private Uri mThumbUri;
    private List<Item> mItemDataSet;
    private Listener mListener;
    private final String mTransitionName;

    enum Type {
        THUMB(0),
        ITEM(1);

        int id;

        Type(int id) {
            this.id = id;
        }

        static Type getType(int id) {
            for (Type type : Type.values()) {
                if (type.id == id) {
                    return type;
                }
            }
            return ITEM;
        }

        int getItemCount(ItemDetailAdapter bookAdapter) {
            switch (this) {
                case THUMB:
                    return 1;

                default:
                    return bookAdapter.mItemDataSet.size();
            }
        }
    }

    public ItemDetailAdapter(Uri uri, List<Item> itemDataSet, String transitionName) {
        mThumbUri = uri;
        mItemDataSet = itemDataSet;
        mTransitionName = transitionName;
    }

    @Override
    public int getItemCount() {
        return 1 + mItemDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        int itemCount = 0;

        for (int i = 0; i < Type.values().length; i++) {
            Type type = Type.getType(i);

            itemCount += type.getItemCount(this);

            if (position < itemCount) {
                return type.id;
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (Type.getType(viewType)) {
            case THUMB: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_image_viewholder, parent, false);

                final ThumbViewHolder vh = new ThumbViewHolder(view);
                ViewCompat.setTransitionName(vh.thumb, mTransitionName);
                vh.thumb.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        Log.d(ItemDetailAdapter.class.getCanonicalName(), "onPreDraw");
                        mListener.onStartPostponedEnterTransition();
                        vh.thumb.getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
                return vh;
            }
            default: {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewholder, parent, false);
                return new ItemViewHolder(view);
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Context context = holder.itemView.getContext();

        if (holder instanceof ThumbViewHolder) {
            Log.d(ItemDetailAdapter.class.getCanonicalName(), "ThumbViewHolder onBindViewHolder");
            final ThumbViewHolder vh = (ThumbViewHolder) holder;

            int windowWidth = WindowUtil.getWidth(context);
            ViewGroup.LayoutParams params = vh.thumb.getLayoutParams();
            params.width = windowWidth;
            params.height = windowWidth;
            vh.thumb.setLayoutParams(params);

            int pos = getPosition(Type.THUMB, position);

            Glide.with(context)
                    .load(mThumbUri)
                    .into(vh.thumb);

            setOnClickListener(holder, Type.THUMB, pos);

        } else {
            final ItemViewHolder vh = (ItemViewHolder) holder;
            int pos = getPosition(Type.ITEM, position);
            Item item = mItemDataSet.get(pos);

            Glide.with(context)
                    .load(item.getThumb())
                    .into(vh.thumb);

            vh.title.setText(item.getTitle());
            vh.description.setText(item.getDescription());

            setOnClickListener(holder, Type.ITEM, pos);
        }
    }

    public ItemDetailAdapter setListener(Listener listener) {
        mListener = listener;
        return this;
    }

    private int getPosition(Type type, int position) {
        int total = 0;

        for (int i = 0; i < type.id; i++) {
            Type t = Type.getType(i);

            switch (t) {
                case THUMB:
                    total += 1;
                    break;

                default:
                    total += mItemDataSet.size();
                    break;
            }
        }

        return position - total;
    }

    private void setOnClickListener(RecyclerView.ViewHolder viewHolder,
                                    final Type type,
                                    final int position) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener == null) {
                    return;
                }
                switch (type) {
                    case THUMB:
                        mListener.onThumbClicked(mThumbUri);
                        break;

                    default:
                        mListener.onItemClicked(mItemDataSet.get(position));
                        break;
                }
            }
        });
    }

    public static class ThumbViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;

        public ThumbViewHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView thumb;
        TextView title;
        TextView description;

        public ItemViewHolder(View itemView) {
            super(itemView);

            thumb = (ImageView) itemView.findViewById(R.id.thumb);
            title = (TextView) itemView.findViewById(R.id.title);
            description = (TextView) itemView.findViewById(R.id.description);
        }
    }

    public interface Listener {
        void onThumbClicked(Uri uri);

        void onItemClicked(Item item);

        void onStartPostponedEnterTransition();
    }
}
package com.r21nomi.androidinteractionexperiment.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.r21nomi.androidinteractionexperiment.R;

import java.util.List;

/**
 * Created by r21nomi on 2016/12/11.
 */

class ExperimentsAdapter extends RecyclerView.Adapter<ExperimentsAdapter.ViewHolder> {

    private List<Experiment> dataSet;
    private Listener listener;

    ExperimentsAdapter(List<Experiment> dataSet, Listener listener) {
        this.dataSet = dataSet;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.experiment_viewholder, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Experiment item = dataSet.get(position);
        holder.title.setText(item.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(item);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    interface Listener {
        void onClick(Experiment item);
    }
}


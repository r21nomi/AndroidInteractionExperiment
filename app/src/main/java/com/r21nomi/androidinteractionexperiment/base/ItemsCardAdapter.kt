package com.r21nomi.androidinteractionexperiment.base

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.bumptech.glide.Glide
import com.r21nomi.androidinteractionexperiment.R

/**
 * Created by r21nomi on 2016/12/11.
 */

class ItemsCardAdapter(val dataSet: List<Item>,
                       private val listener: Listener) : RecyclerView.Adapter<ItemsCardAdapter.ViewHolder>() {

    private val transitionName = TransitionName.FragmentTransition()

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_card_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.title.text = item.title
        holder.description.text = item.description

        ViewCompat.setTransitionName(holder.firstImage, "${transitionName.img1}_$position}")
        Glide.with(holder.itemView.context)
                .load(item.thumb)
                .into(holder.firstImage)

        ViewCompat.setTransitionName(holder.secondImage, "${transitionName.img2}_$position}")
        Glide.with(holder.itemView.context)
                .load(item.thumb)
                .into(holder.secondImage)

        ViewCompat.setTransitionName(holder.thirdImage, "${transitionName.img3}_$position}")
        Glide.with(holder.itemView.context)
                .load(item.thumb)
                .into(holder.thirdImage)

        holder.itemView.setOnClickListener {
            listener.onClick(
                    holder.thumbs,
                    listOf(
                            holder.firstImage,
                            holder.secondImage,
                            holder.thirdImage
                    ),
                    position
            )
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val thumbs: View
        val firstImage: ImageView
        val secondImage: ImageView
        val thirdImage: ImageView
        val title: TextView
        val description: TextView

        init {

            thumbs = itemView.findViewById<View>(R.id.thumbs)
            firstImage = itemView.findViewById(R.id.firstImageView)
            secondImage = itemView.findViewById(R.id.secondImageView)
            thirdImage = itemView.findViewById(R.id.thirdImageView)
            title = itemView.findViewById<View>(R.id.title) as TextView
            description = itemView.findViewById<View>(R.id.description) as TextView
        }
    }

    interface Listener {
        fun onClick(view: View, views: List<View>, position: Int)
    }
}


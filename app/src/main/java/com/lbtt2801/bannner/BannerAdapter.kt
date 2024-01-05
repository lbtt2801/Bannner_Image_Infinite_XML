package com.lbtt2801.bannner

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var images: MutableList<ImageModel?> = mutableListOf()
    private lateinit var viewPager2: ViewPager2

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvID: TextView = itemView.findViewById(R.id.tvID)
        val tvPage: TextView = itemView.findViewById(R.id.tvPage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.img_banner, parent, false)
        return BannerViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        images[position]?.let { image ->
            holder.tvID.text = "ID: ${image.id}"
            holder.imageView.setImageResource(image.dataRes)
            holder.tvPage.text = "Page: $position"
        }
    }

    override fun getItemCount(): Int = images.size

    fun setData(images: List<ImageModel>, viewPager2: ViewPager2) {
        if (this.images.isNotEmpty()) {
            this.images.clear()
        }

        this.images.addAll(images)
        notifyItemRangeInserted(0, images.count())

        this.viewPager2 = viewPager2
    }
}
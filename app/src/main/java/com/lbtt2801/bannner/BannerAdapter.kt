package com.lbtt2801.bannner

import android.annotation.SuppressLint
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
        val textView: TextView = itemView.findViewById(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.img_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        images[position]?.let { image ->
            holder.textView.text = image.id.toString()
            holder.imageView.setImageResource(image.dataRes)
        }
    }

    override fun getItemCount(): Int = images.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(images: List<ImageModel>, viewPager2: ViewPager2) {
        if (this.images.isNotEmpty()) {
            this.images.clear()
        }

        this.images.addAll(images)
        notifyDataSetChanged()
//        notifyItemRangeInserted(0, images.count())

        this.viewPager2 = viewPager2
    }
}
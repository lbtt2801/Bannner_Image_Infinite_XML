package com.lbtt2801.bannner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var images: MutableList<ImageModel>
    private lateinit var bannerAdapter: BannerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        images = mutableListOf()

        initView()
        setUpTransformer()
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val offset = 1 - abs(position)
            page.scaleX = 0.85f + offset * 0.15f
            page.scaleY = 0.85f + offset * 0.15f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun initView() {
        viewPager2 = findViewById(R.id.viewPager2)

        images = buildList {
            repeat(5) { id ->
                add(ImageModel(id = id, dataRes = R.drawable.img_book))
            }
        }.toMutableList()
        images.addAll(images)

        bannerAdapter = BannerAdapter()
        bannerAdapter.setData(images = images, viewPager2 = viewPager2)

        viewPager2.adapter = bannerAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.currentItem = images.size / 2

        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val itemCount = viewPager2.adapter?.itemCount ?: 0

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItemVisible
                        = layoutManager.findFirstVisibleItemPosition()
                val lastItemVisible
                        = layoutManager.findLastVisibleItemPosition()
                if (firstItemVisible == (itemCount - 1)) {
                    recyclerView.scrollToPosition(itemCount / 2 - 1)
                } else if (lastItemVisible == 0) {
                    recyclerView.scrollToPosition(itemCount / 2)
                }
            }
        })
    }
}
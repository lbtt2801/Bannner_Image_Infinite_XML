package com.lbtt2801.bannner

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
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
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        images = mutableListOf()
        handler = Handler(Looper.myLooper()!!)
        initView()
        setUpTransformer()

        /** Auto Scroll **/
//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                handler.removeCallbacks(runnable)
//                handler.postDelayed(runnable , 1000)
//            }
//        })
    }

    private val runnable = Runnable {
        if (viewPager2.currentItem == images.size - 1) {
            viewPager2.setCurrentItem(0, true)
        } else
            viewPager2.setCurrentItem(viewPager2.currentItem + 1, true)
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val offset = 1 - abs(position)
            page.scaleX = 0.85f + offset * 0.15f
            page.scaleY = 0.85f + offset * 0.15f
            page.alpha = 1f * offset + 0.75f

            /** Flip face **/
//            val rotation = 180f * position
//            page.visibility = if (rotation > 90f || rotation < -90f) View.INVISIBLE else View.VISIBLE
//            page.pivotX = page.width * 0.5f
//            page.pivotY = page.height * 0.5f
//            page.rotationY = rotation

            /** Clock Spin **/
//            page.translationX = -position * page.width
//            val offset = abs(position)
//            if (offset < 0.5) {
//                page.visibility = View.VISIBLE
//                page.scaleX = 1 - offset
//                page.scaleY = 1 - offset
//            } else if (offset > 0.5) {
//                page.visibility = View.GONE
//            }
//
//            if (position < -1) {  // [-Infinity,-1)
//                // This page is way off-screen to the left.
//                page.alpha = 0F
//
//            } else if (position <= 0) {    // [-1,0]
//                page.alpha = 1F
//                page.rotation = 360 * (1 - offset)
//
//            } else if (position <= 1) {    // (0,1]
//                page.alpha = 1F
//                page.rotation = -360 * (1 - offset)
//
//            } else {  // (1,+Infinity]
//                page.alpha = 0F
//            }

            /** Fade Out **/
//            page.translationX = -position * page.width
//            page.alpha = 1 - abs(position)

            /** Hinge **/
//            page.translationX = -position * page.width
//            page.pivotX = 0F
//
//            if (position < -1) {
//                page.alpha = 0F
//            } else if (position <= 0) {
//                page.rotation = (90 * abs(position))
//                page.alpha = 1 - abs(position)
//            } else if (position <= 1) {
//                page.rotation = 0F
//                page.alpha = 1 - abs(position)
//            } else page.alpha = 0F



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
        viewPager2.currentItem = images.size / 2

        val recyclerView = viewPager2.getChildAt(0) as RecyclerView
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val itemCount = viewPager2.adapter?.itemCount ?: 0

        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItem = layoutManager.findFirstVisibleItemPosition()
                val lastItem = layoutManager.findLastVisibleItemPosition()
                if (firstItem == (itemCount - 1)) {
                    recyclerView.scrollToPosition(itemCount / 2 - 1)
                } else if (lastItem == 0) {
                    recyclerView.scrollToPosition(itemCount / 2)
                }
            }
        })
    }
}
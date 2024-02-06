package com.min.infiniteviewpager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback


class MainActivity : AppCompatActivity() {

    private lateinit var bannerView: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bannerView = findViewById(R.id.bannerView)

        val originalList: List<DemoItemDTO> = listOf(
            DemoItemDTO("item1", "#ff0000"),
            DemoItemDTO("item2", "#00ff00"),
            DemoItemDTO("item3", "#0000ff"),
            DemoItemDTO("item4", "#f0f0f0")
        )

        initBannerViewPager(originalList)
    }

    private var currentPosition = 0

    private fun initBannerViewPager(originalList: List<DemoItemDTO>) {
        val fakeSize = originalList.size + 2 // use in image ViewPager
        val realSize: Int = originalList.size // use in indicator
        val startIdx = fakeSize - 2 //number two because we have two elements before the chosen one

        bannerView.adapter = BannerAdapter(originalList)
        bannerView.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            // 페이지가 바뀔 때 마다 호출
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                currentPosition = position
            }

            // 스크롤 감지 이벤트
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)

                // 스크롤 완료시
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if (currentPosition == 0) { // ViewPager 왼쪽 끝
                        bannerView.setCurrentItem(fakeSize - 2, false)
                    } else if (currentPosition == fakeSize - 1) { // ViewPager 오른쪽 끝
                        bannerView.setCurrentItem(1, false)
                    }
                }
            }
        })
        bannerView.setCurrentItem(startIdx, false)
    }
}
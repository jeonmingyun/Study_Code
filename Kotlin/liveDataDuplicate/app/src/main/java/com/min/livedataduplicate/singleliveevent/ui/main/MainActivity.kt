package com.min.livedataduplicate.singleliveevent.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.min.livedataduplicate.R
import com.min.livedataduplicate.singleliveevent.ui.next.NextActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 이벤트 관찰
        viewModel.navigateToDetailEvent.observe(this) {
            goToDetail()
        }

        findViewById<Button>(R.id.detailBtn).setOnClickListener {
            viewModel.onDetailButtonClick()
        }
    }

    private fun goToDetail() {
        startActivity(Intent(this, DetailActivity::class.java))
    }

    // EventWrapper
    private fun setEventWrapperObserver() {
        viewModel.event.observe(this) { event ->
            // 이벤트의 내용을 가져오되, 아직 처리되지 않은 경우에만 실행
            event.getContentIfNotHandled()?.let { isClicked ->
                // isClicked는 Boolean 값 (true)
                if(isClicked) {
                    Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
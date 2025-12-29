package com.min.livedataduplicate.singleliveevent.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.min.livedataduplicate.R
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setObservers()
    }

    // EventWrapper
    private fun setObservers() {
        viewModel.event_ld.observe(this) { event_ld ->
            Toast.makeText(this, event_ld, Toast.LENGTH_SHORT).show()
        }
        viewModel.event_ew.observe(this) { event_ew ->
            // 이벤트의 내용을 가져오되, 아직 처리되지 않은 경우에만 실행
            event_ew.getContentIfNotHandled()?.let { event_ew ->
                Toast.makeText(this, event_ew, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
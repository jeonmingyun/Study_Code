package com.min.livedataduplicate.singleliveevent.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.min.livedataduplicate.R
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.min.livedataduplicate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
        setObservers()
    }
    /**
     * 기본 초기화 세팅
     */
    private fun initialize() {
        // ViewModel 할당
        binding.viewmodel = viewModel
        // 데이터 옵저빙 방법1 : 상태 값 등 단순 데이터 변경시에만 사용하며 미사용시 xml의 answer_tv1에 최초 값 초기화 후 LiveData 값이 반영되지 않음
        binding.lifecycleOwner = this
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
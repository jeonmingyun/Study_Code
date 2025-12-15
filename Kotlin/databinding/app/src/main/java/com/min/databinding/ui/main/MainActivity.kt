package com.min.databinding.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.min.databinding.R
import com.min.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view 할당  - setContentView(R.layout.activity_main) 대체
        // fragemnt, customView에서는 inflate를 사용해서 binding 함
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // view 할당 방법2
        // binding = ActivityMainBinding.inflate(layoutInflater)
        // setContentView(binding.root)

        initialize() // 기본 초기화 세팅
        setUpViewModelObserver() // ViewModel 옵저버 세팅
        setUpClickListeners() // 클릭 리스너 세팅
    }

    /**
     * 기본 초기화 세팅
     */
    private fun initialize() {
        // ViewModel 할당
        binding.viewModel = viewModel
        // 데이터 옵저빙 방법1 : 상태 값 등 단순 데이터 변경시에만 사용하며 미사용시 xml의 answer_tv1에 최초 값 초기화 후 LiveData 값이 반영되지 않음
        binding.lifecycleOwner = this
    }

    /**
     * 클릭 리스너 세팅
     * context 사용을 위해 activity에 리스너 선언
     */
    private fun setUpClickListeners() {
        binding.cleanBtn.setOnClickListener {
            viewModel.clean()
            Toast.makeText(this, "초기화 되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }


    /**
     * ViewModel 옵저버 세팅
     */
    private fun setUpViewModelObserver() {
        // 데이터 옵저빙 방법2 : 단순 값 변환이 아니라 toast등 추가 로직이 필요한 경우 사용
        viewModel.answer.observe(this) { answer ->
            binding.answerTv2.text = "데이터 옵저빙 방식2 : " + answer.toString()
        }
    }
}
package com.min.databinding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.min.databinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view 할당  - 기존 : setContentView(R.layout.activity_main)
        // fragemnt, customView에서는 inflate를 사용해서 binding 함
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        // view 할당 방법2
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        // ViewModel 할당
        binding.viewmodel = viewModel
        // 옵저버 생성
        viewModel.answer.observe(this) { answer ->
            binding.answerTv.text = answer.toString()
        }

        // 클릭 리스너 생성
        setUpClickListeners()
    }

    // 클릭 리스너 모음
    private fun setUpClickListeners() {
        binding.plusBtn.setOnClickListener {
            viewModel.plus()
        }
        binding.minusBtn.setOnClickListener {
            viewModel.minus()
        }
        binding.cleanBtn.setOnClickListener {
            viewModel.clean()
        }
    }
}
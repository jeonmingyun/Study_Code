package com.min.livedataduplicate.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.min.livedataduplicate.R
import com.min.livedataduplicate.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

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
        setLiveDataObserver()
        setEventWrapperObserver()
        setStateFlowObserver()
        setSharedFlowObserver()
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

    // LiveData 옵저버
    private fun setLiveDataObserver() {
        viewModel.event_ld.observe(this) { event_ld ->
            binding.answerTv.text = uiLog(event_ld, binding.answerTv.text.toString())
            Toast.makeText(this, event_ld, Toast.LENGTH_SHORT).show()
        }
    }

    // EventWrapper 옵저버
    private fun setEventWrapperObserver() {
        viewModel.event_ew.observe(this) { event_ew ->
            binding.answerTv.text = uiLog(event_ew.peekContent(), binding.answerTv.text.toString())
            // 이벤트의 내용을 가져오되, 아직 처리되지 않은 경우에만 실행
            event_ew.getContentIfNotHandled()?.let { event_ew ->
                Toast.makeText(this, event_ew, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // StateFlow 옵저버
    private fun setStateFlowObserver() {
        lifecycleScope.launch {
            // repeatOnLifecycle은 라이프사이클 값을 매개변수로 받는 suspend 함수입니다.
            // STARTED 상태(또는 그 이상)일 때마다 새 코루틴에서 블록을 실행하고, STOPPED 상태가 되면 취소하여 메모리 누수를 막습니다.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event_state_flow.collect { event_state_flow ->
                    binding.answerTv.text = uiLog(event_state_flow, binding.answerTv.text.toString())
                    Toast.makeText(this@MainActivity, event_state_flow, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // SharedFlow 옵저버
    private fun setSharedFlowObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.event_shared_flow.collect { event_shared_flow ->
                    binding.answerTv.text = uiLog(event_shared_flow, binding.answerTv.text.toString())
                    Toast.makeText(this@MainActivity, event_shared_flow, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun uiLog(newText: String, lastText: String): String {
        return "[${System.currentTimeMillis()}] ${newText} \n ${lastText}"
    }
}
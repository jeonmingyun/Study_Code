package com.min.ex_mvvm.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.min.ex_mvvm.databinding.ActivityMainBinding
import com.min.ex_mvvm.model.User
import com.min.ex_mvvm.view_model.MyViewModel

class MainActivity : AppCompatActivity() {
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // viewModel 연결
        val model: MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        binding.myViewModel = model


        // Create the observer which updates the UI.
        // 변수명, 람다식 파라미터명 모두 자동생성되기 때문에 viewModel의 변수 명과 맞춤
        val resultObserver = Observer<Int> { result ->
            // Update the UI
            binding.calEditText.setText(result.toString())
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.result.observe(this, resultObserver)

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.user.observe(this,  Observer<User> { user ->
            // Update the UI
            binding.idEditText.setText(user.id)
            binding.pwEditText.setText(user.pw)
        })
    }


}

package com.min.ex_mvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.min.ex_mvvm.R
import com.min.ex_mvvm.databinding.ActivityMainBinding
import com.min.ex_mvvm.model.User
import com.min.ex_mvvm.view_model.MyViewModel

class MainActivity : AppCompatActivity() {
    private var mBinding : ActivityMainBinding? = null
    private val binding get() = mBinding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val model: MyViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        model.getUsers().observe(this, Observer<List<User>>{ users ->
            // update UI
            if(users != null) {
                for (user:User in users) {
                    Log.e("ddddddd", user.id);
                    binding.idEditText.setText(user.id)
                }
            }

        })
    }


}

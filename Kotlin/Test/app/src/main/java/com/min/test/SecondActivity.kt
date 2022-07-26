package com.min.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.min.test.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setToolbar(this, binding.includeAppBar.topAppBar)

        // open MainActivity
        binding.btnSec.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        })
    }
}
package com.min.livedataduplicate.singleliveevent.ui.next

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.min.livedataduplicate.R
import com.min.livedataduplicate.singleliveevent.ui.main.MainActivity

class NextActivity : AppCompatActivity() {
    private val viewModel: NextViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setObserver()
    }

    private fun setObserver() {
        viewModel.moveBackEvent.observe(this) {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
package com.min.test

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.appbar.MaterialToolbar

open class BaseActivity : AppCompatActivity() {

    fun setToolbar(context: Context, toolbar: MaterialToolbar) {
        when (context) {
            is MainActivity -> {
                menuInflater.inflate(R.menu.top_app_bar, toolbar.menu)
                toolbar.isTitleCentered = true
                toolbar.title = "Main"
                setOnClickToolbarNavigation(context, toolbar)
                setOnClickToolbarMenuItem(context, toolbar)
            }
            is SecondActivity -> {
                menuInflater.inflate(androidx.core.R.menu.example_menu, toolbar.menu)
                toolbar.isTitleCentered = true
                toolbar.title = "Second"
                toolbar.navigationIcon?.setVisible(false, true)
                setOnClickToolbarNavigation(context, toolbar)
                setOnClickToolbarMenuItem(context, toolbar)
            }
        }
    }

    fun setOnClickToolbarMenuItem(context: Context, toolbar: MaterialToolbar) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorite -> {
                    // Handle favorite icon press
                    showToast("favorite click");
                    true
                }
                R.id.search -> {
                    // Handle search icon press
                    showToast("search click");
                    true
                }
                R.id.more -> {
                    // Handle more item (inside overflow menu) press
                    showToast("more click");
                    true
                }
                else -> false
            }
        }
    }

    fun setOnClickToolbarNavigation(context: Context, toolbar: MaterialToolbar) {
        toolbar.setNavigationOnClickListener {
            showToast("navigation click");

        }
    }

    fun showToast(msg: String) {
        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
    }

}
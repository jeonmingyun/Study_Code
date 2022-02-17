package com.example.yogiyo.activity

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

open class BaseActivity : AppCompatActivity() {
    val TAG = javaClass.simpleName

    override fun onStart() {
        super.onStart()
        /*disable activity open animation*/
//        overridePendingTransition(0,0);
    }

    override fun onPause() {
        super.onPause()
        /*disable activity close animation*/
        overridePendingTransition(0, 0)
    }

    fun showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showToast(msg: Int) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    protected fun onFragmentReplace(frameId: Int, fragment: Fragment, tag: String?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
            .replace(frameId, fragment, tag)
            .commit()
    }

}
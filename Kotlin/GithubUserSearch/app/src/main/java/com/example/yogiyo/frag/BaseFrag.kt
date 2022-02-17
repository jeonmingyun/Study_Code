package com.example.yogiyo.frag

import android.widget.Toast
import androidx.fragment.app.Fragment

open class BaseFrag : Fragment() {
    val TAG = javaClass.simpleName

    fun showToast(msg: String?) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    fun showToast(msg: Int) {
        val toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        toast.show()
    }

}
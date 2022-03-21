package com.min.ex_mvvm.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.min.ex_mvvm.model.User

class MyViewModel : ViewModel() {
    // 수정 가능한 데이터
    val user: MutableLiveData<User> = MutableLiveData<User>(User("id_01", "pw_01"))
//  val user: MutableLiveData<User> by lazy {
//        MutableLiveData<User>().also {
//            loadUsers()
//        }
//    }

    val result =  MutableLiveData(0)

    fun loadUsers() {
        // Do an asynchronous operation to fetch users.]
        user.value?.id = "id_test"
        user.value?.pw = "pw_test"
        Log.e("dddddd", user.value?.id.toString() + " / " + user.value?.pw.toString())
    }

    fun increase() {
        result.value = (result.value?: 0) + 1
    }

    fun decrease() {
        result.value = (result.value?: 0) - 1
    }
}
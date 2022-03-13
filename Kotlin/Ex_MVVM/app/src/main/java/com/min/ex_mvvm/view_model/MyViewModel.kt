package com.min.ex_mvvm.view_model

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.min.ex_mvvm.model.User

class MyViewModel : ViewModel() {
    // 수정 가능한 데이터
    private val users: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>().also {
            loadUsers()
        }
    }
      val _result =  MutableLiveData(0)

    // Read only 데이터
    fun getUsers(): LiveData<List<User >> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        User("id1", "pw1")
    }

    fun increase() {
        _result.value = (_result.value?: 0) + 1
    }

    fun decrease() {
        _result.value = (_result.value?: 0) - 1
    }
}
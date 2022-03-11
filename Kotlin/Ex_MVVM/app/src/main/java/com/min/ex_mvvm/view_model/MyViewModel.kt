package com.min.ex_mvvm.view_model

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

    // Read only 데이터
    fun getUsers(): LiveData<List<User >> {
        return users
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
    }
}
package com.min.ex_mvvm.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.min.ex_mvvm.model.User

class MyViewModel : ViewModel() {
    // 수정 가능한 데이터
    val user: MutableLiveData<User> by lazy {
        MutableLiveData<User>().also {
            
            loadUsers()
        }
    }

    val result =  MutableLiveData(0)

    // Read only 데이터
    fun getUsers(): LiveData<User> {
        return user
    }

    private fun loadUsers() {
        // Do an asynchronous operation to fetch users.
        User("id1", "pw1")
    }

    fun increase() {
        result.value = (result.value?: 0) + 1
    }

    fun decrease() {
        result.value = (result.value?: 0) - 1
    }
}
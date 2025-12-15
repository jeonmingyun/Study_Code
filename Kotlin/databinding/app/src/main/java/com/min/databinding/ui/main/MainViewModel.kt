package com.min.databinding.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

    // 데이터 캡슐화 - MutableLiveData(실 데이터)는 ViewModel 내부에서만 사용 외부에는 LiveData만 노출
    private val _answer = MutableLiveData<Int>(0)
    val answer: LiveData<Int> = _answer


    fun plus() {
        _answer.value = (_answer.value ?: 0) + 1
    }

    fun minus() {
        _answer.value = (_answer.value ?: 0) - 1
    }

    fun clean() {
        _answer.value = 0
    }

}
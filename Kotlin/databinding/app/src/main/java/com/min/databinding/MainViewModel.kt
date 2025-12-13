package com.min.databinding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel() : ViewModel() {

    private val _answer = MutableLiveData<Int>(0)
    val answer: LiveData<Int> = _answer


    fun plus() {
        val currentValue = _answer.value ?: 0
        _answer.value = currentValue + 1
    }

    fun minus() {
        val currentValue = _answer.value ?: 0
        _answer.value = currentValue - 1
    }

    fun clean() {
        _answer.value = 0
    }

}
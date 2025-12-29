package com.min.livedataduplicate.singleliveevent.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.min.livedataduplicate.singleliveevent.common.Event

class MainViewModel : ViewModel() {

    /**
     * Live Data
     */
    private val _event_ld = MutableLiveData<String>()
    val event_ld: LiveData<String> = _event_ld

    fun onLiveDataClick() {
        // 값을 설정할 때도 Event로 감싸서 설정
        _event_ld.value = "Live Data가 클릭되었습니다"
    }

    /**
     * Event Wrapper
     */
    private val _event_ew = MutableLiveData<Event<String>>()
    val event_ew: LiveData<Event<String>> = _event_ew

    fun onEventWrapperClick() {
        // 값을 설정할 때도 Event로 감싸서 설정
        _event_ew.value = Event("Event Wrapper가 클릭되었습니다")
    }
}
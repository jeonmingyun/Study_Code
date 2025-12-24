package com.min.livedataduplicate.singleliveevent.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.min.livedataduplicate.singleliveevent.common.Event
import com.min.livedataduplicate.singleliveevent.common.SingleLiveEvent

class MainViewModel : ViewModel() {
    /**
     * SingleLiveEvent
     */
    // 화면 이동 이벤트
    val navigateToDetailEvent = SingleLiveEvent<Unit>()

    fun onDetailButtonClick() {
        navigateToDetailEvent.call()
    }

    /**
     * Event Wrapper
     */
    private val _event = MutableLiveData<Event<Boolean>>() // 초기값은 이제 없음
    val event: LiveData<Event<Boolean>> = _event

    fun onEventWrapperClick() {
        // 값을 설정할 때도 Event로 감싸서 설정
        _event.value = Event(true) // 여기서 true는 이벤트가 발생했다는 의미
    }
}
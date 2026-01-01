package com.min.livedataduplicate.singleliveevent.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.min.livedataduplicate.singleliveevent.common.Event
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    /**
     * State Flow
     */
    private val _event_state_flow = MutableStateFlow("StateFlow 기본값") // 기본값이 있어야함
    val event_state_flow: StateFlow<String> = _event_state_flow

    fun onStateFlowClick() {
        _event_state_flow.value = "State Flow가 클릭되었습니다"
    }

    /**
     * Shared Flow
     */
    private val _event_shared_flow = MutableSharedFlow<String>()
    val event_shared_flow: SharedFlow<String> = _event_shared_flow

    fun onSharedFlowClick() {
        viewModelScope.launch {
            _event_shared_flow.emit("Shared Flow가 클릭되었습니다")
        }
    }
}
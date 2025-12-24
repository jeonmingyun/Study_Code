package com.min.livedataduplicate.singleliveevent.ui.next

import androidx.lifecycle.ViewModel
import com.min.livedataduplicate.singleliveevent.common.SingleLiveEvent

class NextViewModel : ViewModel() {
    val moveBackEvent = SingleLiveEvent<Unit>()


    fun onBackClick() {
        moveBackEvent.call()
    }

}
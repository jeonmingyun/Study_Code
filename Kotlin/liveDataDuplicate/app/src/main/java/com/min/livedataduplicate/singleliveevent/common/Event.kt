package com.min.livedataduplicate.singleliveevent.common

/**
 * Event Wrapper
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // 외부에서 값을 변경하는 것을 막음

    /**
     * 이벤트가 처리되지 않았을 경우에만 내용을 반환합니다.
     * 한 번 호출되면 hasBeenHandled가 true로 설정됩니다.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * 이벤트 처리 여부와 관계없이 내용을 확인만 할 때 사용합니다.
     */
    fun peekContent(): T = content
}
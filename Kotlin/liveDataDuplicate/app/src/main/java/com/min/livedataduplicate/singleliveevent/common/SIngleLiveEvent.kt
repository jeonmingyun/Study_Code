package com.min.livedataduplicate.singleliveevent.common

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(value: T?) {
        pending.set(true)
        super.setValue(value)
    }

    /** 값 없는 이벤트용 */
    @MainThread
    fun call() {
        value = null
    }
}

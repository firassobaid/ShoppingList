package com.fo.shoppinglist.util

import androidx.lifecycle.Observer

open class SingleEvent<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}

class SingleEventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<SingleEvent<T>> {
    override fun onChanged(event: SingleEvent<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }

}
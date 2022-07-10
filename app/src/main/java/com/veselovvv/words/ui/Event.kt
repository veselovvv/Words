package com.veselovvv.words.ui

class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set // allow external read but not write

    // Returns the content and prevents its use again:
    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) null
        else {
            hasBeenHandled = true
            content
        }
}

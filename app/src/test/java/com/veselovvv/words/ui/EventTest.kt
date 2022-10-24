package com.veselovvv.words.ui

import org.junit.Test

import org.junit.Assert.*

class EventTest {
    @Test
    fun test_get_content_if_not_handled() {
        val event = Event("content")
        assertEquals("content", event.getContentIfNotHandled())

        assertEquals(null, event.getContentIfNotHandled())
    }
}
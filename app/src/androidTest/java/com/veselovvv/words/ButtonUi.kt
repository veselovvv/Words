package com.veselovvv.words

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

class ButtonUi {
    fun checkIsVisible(id: Int) {
        onView(withId(id)).check(matches(isDisplayed()))
    }

    fun checkTextEquals(id: Int, text: String) {
        onView(withId(id)).check(matches(withText(text)))
    }

    fun clickButton(id: Int) {
        onView(withId(id)).perform(click())
    }
}

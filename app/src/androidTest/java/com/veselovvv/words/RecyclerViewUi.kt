package com.veselovvv.words

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.veselovvv.words.RecyclerViewItemCountAssertion.withItemCount
import org.hamcrest.Matchers.allOf

class RecyclerViewUi {
    private val interaction: ViewInteraction = onView(
        allOf(
            withId(R.id.recyclerView),
            isAssignableFrom(RecyclerView::class.java)
        )
    )

    fun checkInitialState() {
        interaction.check(matches(withItemCount(0)))
    }

    fun checkWordsListState(words: List<Pair<String, String>>) {
        words.forEachIndexed { index, (word, translation) ->
            interaction.perform(scrollToPosition<RecyclerView.ViewHolder>(index))
                .check(matches(isDisplayed()))
                .check(matches(withRecyclerViewItemText(R.id.word_text_view, word)))
                .check(matches(withRecyclerViewItemText(R.id.translate_text_view, translation)))
        }
    }

    fun clickOnItemInList(index: Int) {
        interaction.perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(index, click()))
    }
}

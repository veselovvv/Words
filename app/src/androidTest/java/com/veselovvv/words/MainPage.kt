package com.veselovvv.words

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId

class MainPage {
    private val buttonUi = ButtonUi()
    private val textFieldUi = TextFieldUi()
    private val recyclerViewUi = RecyclerViewUi()

    fun checkIsVisible() {
        onView(withId(R.id.root_layout)).check(matches(isDisplayed()))
    }

    fun checkInitialState() {
        textFieldUi.checkIsVisible(id = R.id.word_text)
        textFieldUi.checkIsVisible(id = R.id.translate_text)

        buttonUi.checkIsVisible(id = R.id.save_or_update_button)
        buttonUi.checkIsVisible(id = R.id.clear_all_or_delete_button)
        checkSaveOrUpdateButtonTextState(text = "Save")
        checkClearAllOrDeleteButtonTextState(text = "Clear all")

        recyclerViewUi.checkInitialState()
    }

    fun clickSaveOrUpdateButton() = buttonUi.clickButton(id = R.id.save_or_update_button)
    fun clickClearAllOrDeleteButton() = buttonUi.clickButton(id = R.id.clear_all_or_delete_button)
    fun clickOnItemInList(index: Int) = recyclerViewUi.clickOnItemInList(index = index)

    fun typeInWordTextField(text: String) =
        textFieldUi.typeIn(id = R.id.word_text, text = text)

    fun typeInTranslateTextField(text: String) =
        textFieldUi.typeIn(id = R.id.translate_text, text = text)

    fun checkWordTextFieldState(text: String) =
        textFieldUi.checkState(id = R.id.word_text, text = text)

    fun checkTranslateTextFieldState(text: String) =
        textFieldUi.checkState(id = R.id.translate_text, text = text)

    fun checkWordsListState(words: List<Pair<String, String>>) =
        recyclerViewUi.checkWordsListState(words = words)

    fun checkSaveOrUpdateButtonTextState(text: String) =
        buttonUi.checkTextEquals(id = R.id.save_or_update_button, text = text)

    fun checkClearAllOrDeleteButtonTextState(text: String) =
        buttonUi.checkTextEquals(id = R.id.clear_all_or_delete_button, text = text)
}

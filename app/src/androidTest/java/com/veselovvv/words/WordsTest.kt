package com.veselovvv.words

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.veselovvv.words.ui.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class WordsTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    /**
     * Check Main Page is visible
     * Check initial state
     * 1. Click "Save or update" button
     * Check initial state
     * 2. Type in word text field text "Apple"
     * 3. Click "Save or update" button
     * Check initial state
     * 4. Type in translate text field text "Yabloko"
     * 5. Recreate activity
     * Check word text field state with text "Apple"
     * Check translate text field state with text "Yabloko"
     * 6. Click "Save or update" button
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check words list state with one item with word "Apple" and translation "Yabloko"
     * 7. Recreate activity
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check words list state with one item with word "Apple" and translation "Yabloko"
     * 8. Type in word text field text "Water"
     * 9. Type in translate text field text "Woda"
     * 10. Click "Save or update" button
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check words list state with two items:
     * 1) with word "Apple" and translation "Yabloko"
     * 2) with word "Water" and translation "Woda"
     * 11. Recreate activity
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check words list state with two items:
     * 1) with word "Apple" and translation "Yabloko"
     * 2) with word "Water" and translation "Woda"
     */
    @Test
    fun addWords() = with(MainPage()) {
        checkIsVisible()
        checkInitialState()

        clickSaveOrUpdateButton()
        checkInitialState()

        typeInWordTextField(text = "Apple")
        clickSaveOrUpdateButton()
        checkInitialState()

        typeInTranslateTextField(text = "Yabloko")
        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "Yabloko")

        clickSaveOrUpdateButton()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "Yabloko")
            )
        )

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "Yabloko")
            )
        )

        typeInWordTextField(text = "Water")
        typeInTranslateTextField(text = "Woda")
        clickSaveOrUpdateButton()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "Yabloko"),
                Pair("Water", "Woda")
            )
        )

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "Yabloko"),
                Pair("Water", "Woda")
            )
        )
    }

    /**
     * 0. addWords()
     * 1. Click on first item in the list (index = 0)
     * Check word text field state with text "Apple"
     * Check translate text field state with text "Yabloko"
     * Check "Save or update" button text is "Update"
     * Check "Clear all or delete" button text is "Delete"
     * 2. Recreate activity
     * Check word text field state with text "Apple"
     * Check translate text field state with text "Yabloko"
     * Check "Save or update" button text is "Update"
     * Check "Clear all or delete" button text is "Delete"
     * 3. Click "Clear all or delete" button
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check "Save or update" button text is "Save"
     * Check "Clear all or delete" button text is "Clear all"
     * Check words list state with one item with word "Water" and translation "Woda"
     * 4. Recreate activity
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check "Save or update" button text is "Save"
     * Check "Clear all or delete" button text is "Clear all"
     * Check words list state with one item with word "Water" and translation "Woda"
     */
    @Test
    fun deleteWord() = with(MainPage()) {
        addWords()
        clickOnItemInList(index = 0)
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "Yabloko")
        checkSaveOrUpdateButtonTextState(text = "Update")
        checkClearAllOrDeleteButtonTextState(text = "Delete")

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "Yabloko")
        checkSaveOrUpdateButtonTextState(text = "Update")
        checkClearAllOrDeleteButtonTextState(text = "Delete")

        clickClearAllOrDeleteButton()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkSaveOrUpdateButtonTextState(text = "Save")
        checkClearAllOrDeleteButtonTextState(text = "Clear all")
        checkWordsListState(
            words = listOf(
                Pair("Water", "Woda")
            )
        )

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkSaveOrUpdateButtonTextState(text = "Save")
        checkClearAllOrDeleteButtonTextState(text = "Clear all")
        checkWordsListState(
            words = listOf(
                Pair("Water", "Woda")
            )
        )
    }

    /**
     * 0. addWords()
     * 1. Click on first item in the list (index = 0)
     * Check word text field state with text "Apple"
     * Check translate text field state with text "Yabloko"
     * Check "Save or update" button text is "Update"
     * Check "Clear all or delete" button text is "Delete"
     * 2. Recreate activity
     * Check word text field state with text "Apple"
     * Check translate text field state with text "Yabloko"
     * Check "Save or update" button text is "Update"
     * Check "Clear all or delete" button text is "Delete"
     * 3. Type in translate text field text "New Apple translation"
     * 4. Recreate activity
     * Check word text field state with text "Apple"
     * Check translate text field state with text "New Apple translation"
     * Check "Save or update" button text is "Update"
     * Check "Clear all or delete" button text is "Delete"
     * 5. Click "Save or update" button
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check "Save or update" button text is "Save"
     * Check "Clear all or delete" button text is "Clear all"
     * Check words list state with two items:
     * 1) with word "Apple" and translation "New Apple translation"
     * 2) with word "Water" and translation "Woda"
     * 6. Recreate activity
     * Check word text field state with text ""
     * Check translate text field state with text ""
     * Check "Save or update" button text is "Save"
     * Check "Clear all or delete" button text is "Clear all"
     * Check words list state with two items:
     * 1) with word "Apple" and translation "New Apple translation"
     * 2) with word "Water" and translation "Woda"
     */
    @Test
    fun updateWord() = with(MainPage()) {
        addWords()
        clickOnItemInList(index = 0)
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "Yabloko")
        checkSaveOrUpdateButtonTextState(text = "Update")
        checkClearAllOrDeleteButtonTextState(text = "Delete")

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "Yabloko")
        checkSaveOrUpdateButtonTextState(text = "Update")
        checkClearAllOrDeleteButtonTextState(text = "Delete")

        typeInTranslateTextField(text = "New Apple translation")
        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "Apple")
        checkTranslateTextFieldState(text = "New Apple translation")
        checkSaveOrUpdateButtonTextState(text = "Update")
        checkClearAllOrDeleteButtonTextState(text = "Delete")

        clickSaveOrUpdateButton()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkSaveOrUpdateButtonTextState(text = "Save")
        checkClearAllOrDeleteButtonTextState(text = "Clear all")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "New Apple translation"),
                Pair("Water", "Woda")
            )
        )

        activityScenarioRule.scenario.recreate()
        checkWordTextFieldState(text = "")
        checkTranslateTextFieldState(text = "")
        checkSaveOrUpdateButtonTextState(text = "Save")
        checkClearAllOrDeleteButtonTextState(text = "Clear all")
        checkWordsListState(
            words = listOf(
                Pair("Apple", "New Apple translation"),
                Pair("Water", "Woda")
            )
        )
    }

    /**
     * 0. addWords()
     * 1. Click "Clear all or delete" button
     * Check initial state
     * 2. Recreate activity
     * Check initial state
     */
    @Test
    fun deleteAllWords() = with(MainPage()) {
        addWords()
        clickClearAllOrDeleteButton()
        checkInitialState()

        activityScenarioRule.scenario.recreate()
        checkInitialState()
    }
}
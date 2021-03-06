package com.veselovvv.words.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.words.data.Word
import com.veselovvv.words.data.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel(), Observable {
    val words = repository.getWords()
    private var isUpdateOrDelete = false
    private lateinit var wordToUpdateOrDelete: Word

    @Bindable
    val inputWord = MutableLiveData<String>()
    @Bindable
    val inputTranslate = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        addText()
    }

    fun saveOrUpdate() {
        if (needToFillFields()) statusMessage.value = Event("Please fill the fields!")
        else {
            if (isUpdateOrDelete) {
                wordToUpdateOrDelete.word = inputWord.value!!
                wordToUpdateOrDelete.translate = inputTranslate.value!!
                update(wordToUpdateOrDelete)
            } else {
                viewModelScope.launch {
                    repository.insert(Word(0, inputWord.value!!, inputTranslate.value!!))
                    statusMessage.value = Event("Word is inserted")
                }
                clearText()
            }
        }
    }

    fun initUpdateAndDelete(word: Word) {
        inputWord.value = word.word
        inputTranslate.value = word.translate

        isUpdateOrDelete = true
        wordToUpdateOrDelete = word

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun clearAllOrDelete() = if (isUpdateOrDelete) delete(wordToUpdateOrDelete) else clearAll()

    private fun needToFillFields() = inputWord.value == null || inputTranslate.value == null
            || inputWord.value == "" || inputTranslate.value == ""

    private fun addText() {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    private fun clearText() {
        inputWord.value = ""
        inputTranslate.value = ""
    }

    private fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
        refreshUI()
        statusMessage.value = Event("Word is updated")
    }

    private fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
        refreshUI()
        statusMessage.value = Event("Word is deleted")
    }

    private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("Words are deleted")
    }

    private fun refreshUI() {
        clearText()
        isUpdateOrDelete = false
        addText()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) = Unit
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) = Unit
}

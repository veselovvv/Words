package com.veselovvv.words

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.words.db.Word
import com.veselovvv.words.db.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel(), Observable {

    val words = repository.words

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
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {
        if (inputWord.value == null || inputTranslate.value == null
            || inputWord.value == "" || inputTranslate.value == "") {

            statusMessage.value = Event("Please fill the fields!")
        } else {
            if (isUpdateOrDelete) {
                wordToUpdateOrDelete.word = inputWord.value!!
                wordToUpdateOrDelete.translate = inputTranslate.value!!

                update(wordToUpdateOrDelete)
            } else {
                val word = inputWord.value!!
                val translate = inputTranslate.value!!

                insert(Word(0, word, translate))

                inputWord.value = ""
                inputTranslate.value = ""
            }
        }
    }

    fun clearAllOrDelete() = if (isUpdateOrDelete) delete(wordToUpdateOrDelete) else clearAll()

    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
        statusMessage.value = Event("Word is inserted")
    }

    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)
        refreshUI()
        statusMessage.value = Event("Word is updated")
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)
        refreshUI()
        statusMessage.value = Event("Word is deleted")
    }

    fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("Words are deleted")
    }

    fun initUpdateAndDelete(word: Word) {
        inputWord.value = word.word
        inputTranslate.value = word.translate

        isUpdateOrDelete = true
        wordToUpdateOrDelete = word

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    private fun refreshUI() {
        inputWord.value = ""
        inputTranslate.value = ""

        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}

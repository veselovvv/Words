package com.veselovvv.words

import androidx.databinding.Bindable
import androidx.databinding.Observable
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

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {
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

    fun clearAllOrDelete() = if (isUpdateOrDelete) delete(wordToUpdateOrDelete) else clearAll()

    fun insert(word: Word) = viewModelScope.launch { repository.insert(word) }

    fun update(word: Word) = viewModelScope.launch {
        repository.update(word)

        inputWord.value = ""
        inputTranslate.value = ""

        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun delete(word: Word) = viewModelScope.launch {
        repository.delete(word)

        inputWord.value = ""
        inputTranslate.value = ""

        isUpdateOrDelete = false

        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun clearAll() = viewModelScope.launch { repository.deleteAll() }

    fun initUpdateAndDelete(word: Word) {
        inputWord.value = word.word
        inputTranslate.value = word.translate

        isUpdateOrDelete = true
        wordToUpdateOrDelete = word

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
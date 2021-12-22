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
        val word = inputWord.value!!
        val translate = inputTranslate.value!!

        insert(Word(0, word, translate))

        inputWord.value = ""
        inputTranslate.value = ""
    }

    fun clearAllOrDelete() = clearAll()

    fun insert(word: Word) = viewModelScope.launch { repository.insert(word) }

    fun update(word: Word) = viewModelScope.launch { repository.update(word) }

    fun delete(word: Word) = viewModelScope.launch { repository.delete(word) }

    fun clearAll() = viewModelScope.launch { repository.deleteAll() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
}
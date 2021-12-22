package com.veselovvv.words

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veselovvv.words.db.Word
import com.veselovvv.words.db.WordRepository
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    val inputWord = MutableLiveData<String>()
    val inputTranslate = MutableLiveData<String>()

    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun saveOrUpdate() {
        val word = inputWord.value!!
        val translate = inputTranslate.value!!

        insertWord(Word(0, word, translate))

        inputWord.value = ""
        inputTranslate.value = ""
    }

    private fun insertWord(word: Word) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }
}
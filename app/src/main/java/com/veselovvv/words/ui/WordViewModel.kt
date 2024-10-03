package com.veselovvv.words.ui

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.veselovvv.words.data.Word
import com.veselovvv.words.data.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel(), Observable {
    @Bindable
    val inputWord = MutableLiveData<String>()

    @Bindable
    val inputTranslate = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    private var isUpdateOrDelete = false
    private lateinit var wordToUpdateOrDelete: Word

    init {
        addText()
    }

    fun addText() {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear all"
    }

    fun observeWords(owner: LifecycleOwner, observer: Observer<List<Word>>) =
        repository.getWords().observe(owner, observer)

    fun observeMessage(owner: LifecycleOwner, observer: Observer<Event<String>>) =
        statusMessage.observe(owner, observer)

    fun saveOrUpdate() {
        if (dataIsEmpty(inputWord) || dataIsEmpty(inputTranslate))
            statusMessage.setEventWithText("Please fill the fields!")
        else {
            if (isUpdateOrDelete) {
                wordToUpdateOrDelete.apply {
                    word = inputWord.value ?: ""
                    translate = inputTranslate.value ?: ""
                }
                viewModelScope.launch {
                    repository.update(wordToUpdateOrDelete)
                    refreshUI()
                    statusMessage.setEventWithText("Word is updated")
                }
            } else {
                viewModelScope.launch {
                    repository.insert(
                        Word(0, inputWord.value ?: "", inputTranslate.value ?: "")
                    )
                    statusMessage.setEventWithText("Word is inserted")
                }
                clearText()
            }
        }
    }

    fun dataIsEmpty(data: MutableLiveData<String>) = data.value == null || data.value == ""

    fun MutableLiveData<Event<String>>.setEventWithText(text: String) {
        value = Event(text)
    }

    fun refreshUI() {
        clearText()
        isUpdateOrDelete = false
        addText()
    }

    fun clearText() {
        inputWord.value = ""
        inputTranslate.value = ""
    }

    fun initUpdateAndDelete(word: Word) {
        inputWord.value = word.word
        inputTranslate.value = word.translate

        isUpdateOrDelete = true
        wordToUpdateOrDelete = word

        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun clearAllOrDelete() = if (isUpdateOrDelete)
        viewModelScope.launch {
            repository.delete(wordToUpdateOrDelete)
            refreshUI()
            statusMessage.setEventWithText("Word is deleted")
        } else viewModelScope.launch {
            repository.deleteAll()
            statusMessage.setEventWithText("Words are deleted")
        }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) = Unit
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) = Unit
}

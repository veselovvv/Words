package com.veselovvv.words.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veselovvv.words.data.WordRepository

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) WordViewModel(repository) as T
        else throw IllegalArgumentException("Unknown View Model class")
}
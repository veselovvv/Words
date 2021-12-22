package com.veselovvv.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.veselovvv.words.db.WordRepository
import java.lang.IllegalArgumentException

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model class")
    }
}
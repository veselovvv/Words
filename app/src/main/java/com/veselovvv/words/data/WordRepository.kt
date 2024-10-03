package com.veselovvv.words.data

import androidx.lifecycle.LiveData

interface WordRepository {
    fun getWords(): LiveData<List<Word>>
    suspend fun insert(word: Word): Long
    suspend fun update(word: Word)
    suspend fun delete(word: Word)
    suspend fun deleteAll()

    class Base(database: WordDatabase) : WordRepository {
        private val dao = database.wordDAO()

        override fun getWords() = dao.getAllWords()
        override suspend fun insert(word: Word) = dao.insertWord(word)
        override suspend fun update(word: Word) = dao.updateWord(word)
        override suspend fun delete(word: Word) = dao.deleteWord(word)
        override suspend fun deleteAll() = dao.deleteAll()
    }
}
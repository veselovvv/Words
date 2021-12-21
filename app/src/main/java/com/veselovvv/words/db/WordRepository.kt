package com.veselovvv.words.db

class WordRepository(private val dao: WordDAO) {

    val words = dao.getAllWords()

    suspend fun insert(word: Word) {
        dao.insertWord(word)
    }

    suspend fun update(word: Word) {
        dao.updateWord(word)
    }

    suspend fun delete(word: Word) {
        dao.deleteWord(word)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}
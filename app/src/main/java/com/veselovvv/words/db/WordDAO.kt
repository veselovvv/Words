package com.veselovvv.words.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordDAO {

    @Insert
    suspend fun insertWord(word: Word): Long

    @Update
    suspend fun updateWord(word: Word)

    @Delete
    suspend fun deleteWord(word: Word)

    @Query("DELETE FROM word_data_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM word_data_table")
    fun getAllWords(): LiveData<List<Word>>
}
package com.veselovvv.words.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_data_table")
data class Word(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "word_id")
    val id: Int,

    @ColumnInfo(name = "word_name")
    val word: String,

    @ColumnInfo(name = "word_translate")
    val translate: String
)

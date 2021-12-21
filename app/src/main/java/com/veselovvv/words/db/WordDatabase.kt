package com.veselovvv.words.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1)
abstract class WordDatabase : RoomDatabase() {
    abstract val wordDAO: WordDAO

    companion object {
        @Volatile
        private var INSTANCE: WordDatabase? = null

        fun getInstance(context: Context): WordDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordDatabase::class.java,
                        "word_data_database"
                    ).build()
                }

                return instance
            }
        }
    }
}
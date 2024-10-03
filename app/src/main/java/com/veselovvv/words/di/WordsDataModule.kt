package com.veselovvv.words.di

import android.content.Context
import androidx.room.Room
import com.veselovvv.words.data.WordDatabase
import com.veselovvv.words.data.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WordsDataModule {
    @Provides
    @Singleton
    fun provideWordDatabase(@ApplicationContext context: Context): WordDatabase =
        Room.databaseBuilder(
            context,
            WordDatabase::class.java,
            DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideWordRepository(database: WordDatabase): WordRepository =
        WordRepository.Base(database)

    companion object {
        private const val DATABASE_NAME = "word_data_database"
    }
}
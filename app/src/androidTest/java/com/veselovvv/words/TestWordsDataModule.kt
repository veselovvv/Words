package com.veselovvv.words

import android.content.Context
import androidx.room.Room
import com.veselovvv.words.data.WordDatabase
import com.veselovvv.words.data.WordRepository
import com.veselovvv.words.di.WordsDataModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [WordsDataModule::class] // replaces WordsDataModule with this fake one
)
class TestWordsDataModule {
    @Provides
    @Singleton
    fun provideWordDatabase(@ApplicationContext context: Context): WordDatabase =
        Room.inMemoryDatabaseBuilder( // uses different database for tests
            context,
            WordDatabase::class.java
        ).build()

    @Provides
    @Singleton
    fun provideWordRepository(database: WordDatabase): WordRepository =
        WordRepository.Base(database)
}
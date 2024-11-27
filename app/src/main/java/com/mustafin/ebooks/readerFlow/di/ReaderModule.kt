package com.mustafin.ebooks.readerFlow.di

import com.mustafin.ebooks.core.data.source.local.readerProgressDatabase.ReaderProgressDatabase
import com.mustafin.ebooks.core.data.source.network.largeLanguageModelApi.LLMApi
import com.mustafin.ebooks.readerFlow.data.repositories.dictionaryRepository.DictionaryRepository
import com.mustafin.ebooks.readerFlow.data.repositories.dictionaryRepository.DictionaryRepositoryImpl
import com.mustafin.ebooks.readerFlow.data.repositories.readerProgressRepository.ReaderProgressRepository
import com.mustafin.ebooks.readerFlow.data.repositories.readerProgressRepository.ReaderProgressRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReaderModule {
    @Provides
    @Singleton
    fun provideReaderProgressRepository(readerProgressDatabase: ReaderProgressDatabase): ReaderProgressRepository {
        return ReaderProgressRepositoryImpl(readerProgressDatabase)
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(dictionaryApi: LLMApi): DictionaryRepository {
        return DictionaryRepositoryImpl(dictionaryApi)
    }
}
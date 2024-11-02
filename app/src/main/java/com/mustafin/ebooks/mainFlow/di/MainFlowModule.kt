package com.mustafin.ebooks.mainFlow.di

import android.content.Context
import com.mustafin.ebooks.core.data.source.network.LargeLanguageModelApi.LLMApi
import com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository.BookInfoRepository
import com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository.BookInfoRepositoryImpl
import com.mustafin.ebooks.mainFlow.domain.PdfReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainFlowModule {
    @Provides
    @Singleton
    fun provideBookInfoRepository(llmApi: LLMApi): BookInfoRepository {
        return BookInfoRepositoryImpl(llmApi)
    }

    @Provides
    @Singleton
    fun providePdfReader(@ApplicationContext context: Context): PdfReader {
        return PdfReader(context)
    }
}
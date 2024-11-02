package com.mustafin.ebooks.readerFlow.data.repositories.dictionaryRepository

import com.mustafin.ebooks.core.data.source.network.LargeLanguageModelApi.LLMApi
import com.mustafin.ebooks.core.domain.enums.ResponseStatus
import javax.inject.Inject

// Репозиторий для работы с толковым словарем
class DictionaryRepositoryImpl @Inject constructor(
    private val dictionaryApi: LLMApi
) : DictionaryRepository {
    override suspend fun getWordMeaning(
        word: String,
        context: String
    ): Pair<ResponseStatus, String?> {
        return dictionaryApi.makeRequest(
            "Дай определение слова $word в отрывке $context. " +
                    "Определение должно быть в формате \"Это ...\", или \"...\", или \"Это означает ...\". " +
                    "Определение должно содержать минимум одно предложение. " +
                    "Определение давай одному слову - $word. " +
                    "Определение пиши на русском языке."
        )
    }
}
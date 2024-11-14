package com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository

import com.mustafin.ebooks.core.data.source.network.LargeLanguageModelApi.LLMApi
import com.mustafin.ebooks.core.domain.enums.ResponseStatus
import com.mustafin.ebooks.mainFlow.domain.models.BookInfoModel
import javax.inject.Inject

class BookInfoRepositoryImpl @Inject constructor(
    private val llmApi: LLMApi
) : BookInfoRepository {
    override suspend fun getBookInfoByFragment(fragment: String): Pair<ResponseStatus, BookInfoModel?> {
        val llmResponse = llmApi.makeRequest(
            "Найди название произведения в фрагменте \"$fragment\"." +
                    "В ответе дай только название произведения и ничего больше." +
                    "Без кавычек рядом и других символов, которые не входят в название."
        )
        return if (llmResponse.second == null) {
            Pair(llmResponse.first, null)
        } else {
            Pair(llmResponse.first, BookInfoModel(llmResponse.second!!))
        }
    }
}
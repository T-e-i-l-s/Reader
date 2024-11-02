package com.mustafin.ebooks.core.data.source.network.LargeLanguageModelApi

import com.mustafin.ebooks.core.domain.enums.ResponseStatus
import retrofit2.Retrofit
import javax.inject.Inject

class LLMApi @Inject constructor(retrofit: Retrofit) {
    private val service: LLMApiService by lazy {
        retrofit.create(LLMApiService::class.java)
    }

    // Запрос на получение значения слова из сети
    suspend fun makeRequest(prompt: String): Pair<ResponseStatus, String?> {
        return try {
            val response = service.getWordMeaning(
                request = LLMApiService.GetWordMeaningRequestBody(
                    messages = listOf(
                        LLMApiService.RequestMessageModel(
                            "user",
                            prompt
                        )
                    )
                )
            )

            if (response.isSuccessful) {
                Pair(
                    ResponseStatus.SUCCESS,
                    response.body()!!.choices[0].message.content
                )
            } else if (response.code() >= 500) {
                Pair(ResponseStatus.NETWORK_ERROR, null)
            } else {
                Pair(ResponseStatus.ERROR, null)
            }
        } catch (e: Exception) {
            Pair(ResponseStatus.ERROR, null)
        }
    }
}
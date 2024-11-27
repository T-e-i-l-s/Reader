package com.mustafin.ebooks.core.data.source.network.largeLanguageModelApi

import com.mustafin.ebooks.core.domain.LLM_API_KEY
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface LLMApiService {
    @POST("chat/completions")
    @Headers("Accept: application/json")
    suspend fun getWordMeaning(
        @Header("Authorization") authToken: String = LLM_API_KEY,
        @Body request: GetWordMeaningRequestBody,
    ): Response<GetWordMeaningResponse>

    data class GetWordMeaningRequestBody (
        val model: String = "Meta-Llama-3.1-8B-Instruct",
        val messages: List<RequestMessageModel>
    )
    data class RequestMessageModel (
        val role: String,
        val content: String
    )

    data class GetWordMeaningResponse (
        val choices: List<ResponseChoiceModel>
    )
    data class ResponseChoiceModel (
        val message: ResponseMessageModel
    )
    data class ResponseMessageModel (
        val content: String
    )
}
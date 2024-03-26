package com.example.interviewtask01.network

import com.example.interviewtask01.utils.AppConstants
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceQuote {

    @GET(AppConstants.ENDPOINT_QUOTES)
    suspend fun quoteApiCall(): Response<ResponseBody>

    @GET(AppConstants.ENDPOINT_POSTS)
    suspend fun postApiCall1(
        @Query("limit") limit: Int? = null,
        @Query("skip") skip: Int? = null,
        @Query("select") select: String? = null
    ): Response<ResponseBody>

}
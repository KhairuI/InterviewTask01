package com.example.interviewtask01.repository

import com.example.interviewtask01.base.BaseRepository
import com.example.interviewtask01.model.PostRequestModel
import com.example.interviewtask01.network.ApiServiceQuote

class QuoteRepository(private val api: ApiServiceQuote) : BaseRepository() {


    // get Quote list
    suspend fun getQuoteList() = safeApiCall {
        api.quoteApiCall()
    }

    // get Post list
    suspend fun getPostList1(limit: Int, skip: Int, select: String) = safeApiCall {
        api.postApiCall1(limit, skip, select)
    }

}
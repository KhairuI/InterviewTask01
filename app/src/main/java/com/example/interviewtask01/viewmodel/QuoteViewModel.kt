package com.example.interviewtask01.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.interviewtask01.model.PostRequestModel
import com.example.interviewtask01.repository.QuoteRepository
import com.example.interviewtask01.utils.DataState
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response

class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {


    // get Quote list
    private val _getQuoteListResponse : MutableLiveData<DataState<Response<ResponseBody>>> = MutableLiveData()
    val getQuoteListResponse : LiveData<DataState<Response<ResponseBody>>> get() = _getQuoteListResponse

    // get Post list
    private val _getPostListResponse1 : MutableLiveData<DataState<Response<ResponseBody>>> = MutableLiveData()
    val getPostListResponse1 : LiveData<DataState<Response<ResponseBody>>> get() = _getPostListResponse1

    // get Quote list
    fun getQuoteList() = viewModelScope.launch {

        _getQuoteListResponse.value = DataState.Loading
        _getQuoteListResponse.value = repository.getQuoteList()
    }

    // get Post list
    fun getPostList1(limit: Int, skip: Int, select: String) = viewModelScope.launch {

        _getPostListResponse1.value = DataState.Loading
        _getPostListResponse1.value = repository.getPostList1(limit, skip, select)
    }


}
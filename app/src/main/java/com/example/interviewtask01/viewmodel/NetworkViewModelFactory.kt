package com.example.interviewtask01.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask01.base.BaseRepository
import com.example.interviewtask01.repository.QuoteRepository

@Suppress("UNCHECKED_CAST")
class NetworkViewModelFactory (
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(QuoteViewModel::class.java) -> QuoteViewModel(repository as QuoteRepository) as T

            else -> throw IllegalArgumentException("NetworkViewModelFactory Not Found")
        }
    }

}
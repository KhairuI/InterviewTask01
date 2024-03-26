package com.example.interviewtask01.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.interviewtask01.adapter.QuoteListAdapter
import com.example.interviewtask01.base.BaseActivity
import com.example.interviewtask01.databinding.ActivityMainBinding
import com.example.interviewtask01.model.PostRequestModel
import com.example.interviewtask01.model.QuoteModel
import com.example.interviewtask01.network.ApiServiceQuote
import com.example.interviewtask01.repository.QuoteRepository
import com.example.interviewtask01.utils.DataState
import com.example.interviewtask01.utils.LoadingDialog
import com.example.interviewtask01.utils.grid
import com.example.interviewtask01.utils.with
import com.example.interviewtask01.viewmodel.QuoteViewModel
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject

class MainActivity : BaseActivity<QuoteViewModel, QuoteRepository>() {


    override fun getViewModel() = QuoteViewModel::class.java
    override fun getRepository() =
        QuoteRepository(remoteDataSource.buildApi(ApiServiceQuote::class.java))


    // init all variable
    private lateinit var binding: ActivityMainBinding
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var quoteAdapter: QuoteListAdapter

    override fun getLayoutResourceId(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun init(savedInstanceState: Bundle?) {

        // loading
        loadingDialog = LoadingDialog(this)

        // observe all response
        observeQuoteListResponse()
        observePostListResponse1()

        // get all quote list
        viewModel.getQuoteList()
        viewModel.getPostList1(10,10,"title")

        // set adapter
        quoteAdapter = QuoteListAdapter()
        binding.listQuote.with(quoteAdapter.apply {
            click { item -> Log.d("xxx", "item: $item") }
        })

    }

    private fun observePostListResponse1() {
        viewModel.getPostListResponse1.observe(this) {

            when (it) {

                is DataState.Success -> {

                    try {

                        // get data
                        val body = it.value.body()?.string()
                        if (!body.isNullOrBlank()) {
                            val jsonObject = JSONObject(body)
                            Log.d("xxx", "Post List 1: $jsonObject")
                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }

                is DataState.Loading -> {
                   // if (!loadingDialog.isShowing) loadingDialog.show()
                }

                is DataState.Error -> {
                    if (it.isNetworkError) Toast.makeText(
                        this,
                        "Network Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("xxx", "$it")

                }
            }

        }
    }

    private fun observeQuoteListResponse() {

        viewModel.getQuoteListResponse.observe(this) {

            when (it) {

                is DataState.Success -> {

                    try {

                        // gone loading
                        if (loadingDialog.isShowing) loadingDialog.dismiss()

                        // get data
                        val body = it.value.body()?.string()
                        if (!body.isNullOrBlank()) {
                            val jsonObject = JSONObject(body)
                            Log.d("xxx", "Quote List: $jsonObject")
                            setQuoteList(jsonObject)

                        }

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                }

                is DataState.Loading -> {
                    if (!loadingDialog.isShowing) loadingDialog.show()
                }

                is DataState.Error -> {
                    if (it.isNetworkError) Toast.makeText(
                        this,
                        "Network Error",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("#X_API_RES", "$it")

                }
            }

        }
    }

    private fun setQuoteList(jsonObject: JSONObject) {
        val quoteList = Gson().fromJson(jsonObject.toString(), QuoteModel::class.java)
        quoteAdapter.submitList(quoteList.quotes)
    }
}
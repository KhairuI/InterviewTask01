package com.example.interviewtask01.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask01.network.RemoteDataSource
import com.example.interviewtask01.viewmodel.NetworkViewModelFactory

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding, R : BaseRepository> : Fragment() {

    // Fields ======================================================================================

    lateinit var viewModel: VM
    protected lateinit var binding: B
    protected val remoteDataSource = RemoteDataSource()
    protected lateinit var mContext: Context

    // ======================================================

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = getBinding(inflater, container)
        val factory = NetworkViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
        binding.lifecycleOwner = this
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    abstract fun getViewModel(): Class<VM>

    abstract fun getBinding(inflater: LayoutInflater, container: ViewGroup?): B

    abstract fun getRepository(): R

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(mContext)
    }


}
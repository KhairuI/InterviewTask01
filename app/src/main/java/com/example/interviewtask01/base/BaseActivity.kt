package com.example.interviewtask01.base

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.interviewtask01.network.RemoteDataSource
import com.example.interviewtask01.viewmodel.NetworkViewModelFactory

abstract class BaseActivity<VM : ViewModel, R : BaseRepository> : AppCompatActivity() {


    lateinit var viewModel: VM
    protected val remoteDataSource = RemoteDataSource()

    private fun setWindow() {
       /* window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getLayoutResourceId()?.let { setContentView(it) }
        val factory = NetworkViewModelFactory(getRepository())
        viewModel = ViewModelProvider(this, factory)[getViewModel()]
        setWindow()
        init(savedInstanceState)
    }

    protected abstract fun init(savedInstanceState: Bundle?)

    abstract fun getViewModel(): Class<VM>

    abstract fun getRepository(): R

    protected abstract fun getLayoutResourceId(): View?


    fun invokeActivity(cls: Class<*>?) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    fun invokeActivity(cls: Class<*>?, key: String, value: String) {
        val intent = Intent(this, cls).apply {
            flags = FLAG_ACTIVITY_CLEAR_TASK
            putExtra(key, value)
        }
        startActivity(intent)
    }

    fun invokeActivity(activity: Activity, cls: Class<*>?) {
        val intent = Intent(activity, cls)
        activity.startActivity(intent)
    }

    fun invokeActivityAndFinish(cls: Class<*>?) {
        val intent = Intent(this, cls).apply {
            flags = FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }


}
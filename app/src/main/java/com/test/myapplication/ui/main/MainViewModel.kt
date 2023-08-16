package com.test.myapplication.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

class MainViewModel : AndroidViewModel {
    constructor(app: Application): super(app)

    val isRunning = MutableLiveData(false)

    private val data: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            it.postValue("hello world !")
        }
    }

    fun getData(): LiveData<String> {
        return data
    }

    fun setData(dat: String) {
        data.value = dat + getApplication<Application>().packageName
        Log.d("data:", data.value!!)
    }

    val logSb: MutableLiveData<StringBuffer> =
        MutableLiveData<StringBuffer>().also {
            it.postValue(StringBuffer("准备好了，开始！\n"))
        }

    suspend fun updateLog(txt: String) {
        delay(600)
        logSb.value?.append("$txt\n")

        logSb.postValue(StringBuffer(logSb.value))
    }

    suspend fun clearLog() {
        delay(600)
        logSb.postValue(StringBuffer())
    }
}
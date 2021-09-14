package com.kyawhut.atsycast.share.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * @author kyawhtut
 * @date 8/31/21
 */
abstract class BaseViewModel : ViewModel() {

    private val job: Job = Job()
    private val io: CoroutineContext = job + Dispatchers.IO
    private val main: CoroutineContext = job + Dispatchers.Main

    protected fun processOnUI(callback: () -> Unit) {
        viewModelScope.launch(main) {
            callback()
        }
    }

    protected fun processOnIO(callback: () -> Unit) {
        viewModelScope.launch(io) {
            callback()
        }
    }

    protected fun viewModelScope(callback: suspend () -> Unit) {
        viewModelScope.launch(io) {
            callback()
        }
    }

    override fun onCleared() {
        job.cancel("Base ViewModel is onCleared.")
        super.onCleared()
    }
}

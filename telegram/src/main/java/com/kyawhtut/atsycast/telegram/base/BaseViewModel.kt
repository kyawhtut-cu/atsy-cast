package com.kyawhtut.atsycast.telegram.base

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawhtut.atsycast.telegram.BR
import com.kyawhtut.atsycast.telegram.data.common.AuthRepository
import com.kyawhtut.atsycast.telegram.utils.AuthState
import com.kyawhtut.atsycast.telegram.utils.NetworkState
import com.kyawhtut.atsycast.telegram.utils.TelegramException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlin.coroutines.CoroutineContext

internal abstract class BaseViewModel(
    private val networkState: NetworkState?, protected val authRepository: AuthRepository?
) : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    private val job: Job = Job()
    private val scope = viewModelScope + CoroutineExceptionHandler { _, throwable ->
        isLoading = false
        error = TelegramException(
            code = -1, message = throwable.message ?: "Code Error"
        )
        throwable.printStackTrace()
    }
    private val io: CoroutineContext = job + Dispatchers.IO
    private val main: CoroutineContext = job + Dispatchers.Main

    protected var onAuthState: ((AuthState) -> Unit)? = null

    @get:Bindable
    var isLoading: Boolean
        get() = networkState?.isLoading == true
        set(value) {
            networkState?.isLoading = value
            notifyPropertyChanged(BR.loading)
        }

    @get:Bindable
    var error: TelegramException?
        get() = networkState?.error
        set(value) {
            networkState?.error = value
            notifyPropertyChanged(BR.error)
        }

    init {
        processOnIO {
            networkState?.errorState?.collect {
                notifyPropertyChanged(BR.error)
            }
        }

        processOnIO {
            networkState?.loadingState?.collect {
                notifyPropertyChanged(BR.loading)
            }
        }

        processOnIO {
            authRepository?.authFlow?.collect {
                it?.let {
                    onAuthState?.invoke(it)
                }
            }
        }
    }

    fun setOnAuthStateListener(listener: ((AuthState) -> Unit)? = null) {
        onAuthState = listener
    }

    protected fun processOnIO(callback: suspend CoroutineScope.() -> Unit) {
        scope.launch(io) {
            callback.invoke(this)
        }
    }

    protected fun processOnMain(callback: suspend CoroutineScope.() -> Unit) {
        scope.launch(main) {
            callback.invoke(this)
        }
    }

    protected fun viewModelScope(
        thread: CoroutineContext = io, callback: suspend CoroutineScope.() -> Unit
    ) {
        scope.launch(thread) {
            callback.invoke(this)
        }
    }

    protected fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    protected fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    override fun addOnPropertyChangedCallback(p0: Observable.OnPropertyChangedCallback?) {
        callbacks.add(p0)
    }

    override fun removeOnPropertyChangedCallback(p0: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(p0)
    }
}

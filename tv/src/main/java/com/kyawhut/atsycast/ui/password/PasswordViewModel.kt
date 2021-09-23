package com.kyawhut.atsycast.ui.password

import android.content.Context
import com.kyawhut.atsycast.share.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repo: PasswordRepository
) : BaseViewModel() {

    fun checkAdultPassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        viewModelScope {
            repo.checkAdultPassword(context, password) { status, message ->
                processOnUI {
                    callback(status, message)
                }
            }
        }
    }

    fun checkDevicePassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        viewModelScope {
            repo.checkDevicePassword(context, password) { status, message ->
                processOnUI {
                    callback(status, message)
                }
            }
        }
    }
}

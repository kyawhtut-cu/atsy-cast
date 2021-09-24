package com.kyawhut.atsycast.ui.splash

import android.app.Application
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.data.network.response.UserResponse
import com.kyawhut.atsycast.share.base.BaseViewModel
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/17/21
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val application: Application,
    private val repo: SplashRepository
) : BaseViewModel() {

    fun checkDevice(callback: (NetworkResponse<UserResponse.Data?>) -> Unit) {
        viewModelScope {
            repo.checkDeviceStatus(application) {
                processOnUI {
                    callback(it)
                }
            }
        }
    }

    fun checkUpdateStatus(callback: (NetworkResponse<UpdateResponse.Data?>) -> Unit) {
        viewModelScope {
            repo.checkUpdate(application, callback)
        }
    }
}

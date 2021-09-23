package com.kyawhut.atsycast.ui.setting

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.kyawhut.atsycast.share.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/16/21
 */
@HiltViewModel
class SettingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: SettingRepository
) : BaseViewModel() {

    fun changeDisplayName(context: Context, name: String, callback: (Boolean, String) -> Unit) {
        viewModelScope {
            repository.changeDisplayName(context, name) { status, message ->
                processOnUI {
                    callback(status, message)
                }
            }
        }
    }
}

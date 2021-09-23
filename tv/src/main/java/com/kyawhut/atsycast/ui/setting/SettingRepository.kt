package com.kyawhut.atsycast.ui.setting

import android.content.Context

/**
 * @author kyawhtut
 * @date 9/16/21
 */
interface SettingRepository {

    suspend fun changeDisplayName(
        context: Context,
        name: String,
        callback: (Boolean, String) -> Unit
    )
}

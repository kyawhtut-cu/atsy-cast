package com.kyawhut.atsycast.ui.password

import android.content.Context

/**
 * @author kyawhtut
 * @date 9/16/21
 */
interface PasswordRepository {

    suspend fun checkDevicePassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    )

    suspend fun checkAdultPassword(
        context: Context,
        password: String,
        callback: (Boolean, String) -> Unit
    )
}

package com.kyawhut.atsycast.ui.splash

import android.content.Context
import androidx.preference.PreferenceManager
import com.kyawhut.atsycast.BuildConfig
import com.kyawhut.atsycast.R
import com.kyawhut.atsycast.data.network.SheetAPI
import com.kyawhut.atsycast.data.network.response.UpdateResponse
import com.kyawhut.atsycast.data.network.response.UserResponse
import com.kyawhut.atsycast.share.network.request.scriptRequest
import com.kyawhut.atsycast.share.network.utils.NetworkError
import com.kyawhut.atsycast.share.network.utils.NetworkResponse
import com.kyawhut.atsycast.share.network.utils.execute
import com.kyawhut.atsycast.share.telegram.utils.TelegramHelper
import com.kyawhut.atsycast.share.utils.Crashlytics
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceID
import com.kyawhut.atsycast.share.utils.ShareUtils.deviceName
import com.kyawhut.atsycast.share.utils.extension.Extension.devicePassword
import com.kyawhut.atsycast.share.utils.extension.put
import com.kyawhut.atsycast.utils.version.Version
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 9/17/21
 */
class SplashRepositoryImpl @Inject constructor(
    private val api: SheetAPI,
    private val crashlytics: Crashlytics,
) : SplashRepository {

    private fun saveUserInfo(context: Context, data: UserResponse.Data?) {
        with(PreferenceManager.getDefaultSharedPreferences(context)) {
            if (data == null) return@with
            put(context.getString(R.string.pref_display_name), data.displayName)
            put(context.getString(R.string.old_pref_display_name), data.displayName)
        }
    }

    override suspend fun checkDeviceStatus(
        context: Context,
        callback: (NetworkResponse<UserResponse.Data?>) -> Unit
    ) {
        val password = context.devicePassword
        val response = execute(crashlytics) {
            if (password.isEmpty()) api.registerDevice(
                scriptRequest {
                    route = "registerDevice"
                    payload = mutableMapOf(
                        "device_id" to context.deviceID,
                        "device_name" to context.deviceName,
                        "app_package_name" to BuildConfig.APPLICATION_ID
                    )
                },
            ) else api.checkDevicePassword(
                scriptRequest {
                    route = "checkDevicePassword"
                    payload = mutableMapOf(
                        "device_id" to context.deviceID,
                        "device_password" to password,
                    )
                },
            )
        }
        if (response.isSuccess) {
            if (response.data != null) {
                context.devicePassword = password
                saveUserInfo(context, response.data?.data)
                if (password.isEmpty()) {
                    NetworkResponse.success(null, callback)
                } else NetworkResponse.success(response.data?.data, callback)
            } else {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                error(
                    if (password.isEmpty()) {
                        context.getString(R.string.lbl_device_register_error)
                    } else {
                        context.getString(R.string.lbl_device_password_error)
                    }
                )
            }
        } else if (response.isError) {
            NetworkResponse.error(
                response.error ?: NetworkError(
                    context.getString(R.string.lbl_notify_to_developer)
                ),
                callback
            )
            error(
                if (response.error != null) {
                    if (response.error!!.resId != 0) context.getString(response.error!!.resId) else response.error!!.message
                } else if (password.isEmpty()) {
                    context.getString(R.string.lbl_device_register_error)
                } else {
                    context.getString(R.string.lbl_device_password_error)
                }
            )
        }
    }

    override suspend fun checkUpdate(
        context: Context,
        callback: (NetworkResponse<UpdateResponse.Data?>) -> Unit
    ) {
        val versionResponse = execute(crashlytics) {
            api.checkUpdate(scriptRequest {
                route = "checkUpdate"
            }).data
        }
        if (versionResponse.isSuccess) {
            val appStatus: UpdateResponse.Data? = versionResponse.data?.last()
            if (appStatus == null) {
                NetworkResponse.error(
                    NetworkError(context.getString(R.string.lbl_notify_to_developer)),
                    callback
                )
                error(
                    context.getString(R.string.lbl_check_update_error),
                    "checkUpdate"
                )
            } else {
                when {
                    BuildConfig.DEBUG -> NetworkResponse.success(null, callback)
                    appStatus.isMaintenance -> NetworkResponse.success(appStatus, callback)
                    Version(BuildConfig.VERSION_NAME).isLowerThan(Version(appStatus.versionName)) -> {
                        NetworkResponse.success(appStatus, callback)
                    }
                    else -> NetworkResponse.success(null, callback)
                }
            }
        } else if (versionResponse.isError) {
            NetworkResponse.error(
                versionResponse.error ?: NetworkError(
                    context.getString(R.string.lbl_notify_to_developer)
                ),
                callback
            )
            error(
                if (versionResponse.error != null) {
                    if (versionResponse.error!!.resId != 0) context.getString(versionResponse.error!!.resId) else versionResponse.error!!.message
                } else {
                    context.getString(R.string.lbl_check_update_error)
                },
                "checkUpdate"
            )
        }
    }

    private fun error(errorMessage: String, place: String = "checkDeviceStatus") {
        crashlytics.sendCrashlytics(
            RuntimeException(
                """
            Error occur in $place
            
            $errorMessage
        """.trimIndent()
            )
        )
        TelegramHelper.sendLog(
            """
                Error occur in <strong>$place</strong>
                
$errorMessage
                    """.trimIndent()
        )
    }
}

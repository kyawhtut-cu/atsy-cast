package com.kyawhut.atsycast.utils

import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.kyawhut.atsycast.share.utils.Crashlytics
import javax.inject.Inject

/**
 * @author kyawhtut
 * @date 10/6/21
 */
class CrashlyticsImpl @Inject constructor() : Crashlytics {

    override fun sendCrashlytics(e: Exception) {
        FirebaseCrashlytics.getInstance().recordException(e)
    }
}

@file:Suppress("UNCHECKED_CAST")

package com.kyawhut.atsycast.share.utils.extension

import android.app.Activity
import android.app.Service
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.Serializable

object IntentExtension {

    fun getBundle(params: Array<out Pair<String, Any?>>): Bundle {
        val bundle = Bundle()
        with(bundle) {
            params.forEach {
                when (val value = it.second) {
                    null -> putSerializable(it.first, null as Serializable?)
                    is Int -> putInt(it.first, value)
                    is Long -> putLong(it.first, value)
                    is CharSequence -> putCharSequence(it.first, value)
                    is String -> putString(it.first, value)
                    is Float -> putFloat(it.first, value)
                    is Double -> putDouble(it.first, value)
                    is Char -> putChar(it.first, value)
                    is Short -> putShort(it.first, value)
                    is Boolean -> putBoolean(it.first, value)
                    is Serializable -> putSerializable(it.first, value)
                    is Bundle -> putBundle(it.first, value)
                    is Parcelable -> putParcelable(it.first, value)
                    is Array<*> -> when {
                        value.isArrayOf<CharSequence>() -> putCharSequenceArray(
                            it.first,
                            value as Array<out CharSequence>?
                        )
                        value.isArrayOf<String>() -> putStringArray(
                            it.first,
                            value as Array<out String>?
                        )
                        value.isArrayOf<Parcelable>() -> putParcelableArray(
                            it.first,
                            value as Array<out Parcelable>?
                        )
                        else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                    }
                    is IntArray -> putIntArray(it.first, value)
                    is LongArray -> putLongArray(it.first, value)
                    is FloatArray -> putFloatArray(it.first, value)
                    is DoubleArray -> putDoubleArray(it.first, value)
                    is CharArray -> putCharArray(it.first, value)
                    is ShortArray -> putShortArray(it.first, value)
                    is BooleanArray -> putBooleanArray(it.first, value)
                    else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
            }
        }
        return bundle
    }

    private fun Intent.putArgument(params: Array<out Pair<String, Any?>>) {
        params.forEach {
            when (val value = it.second) {
                null -> this.putExtra(it.first, null as Serializable?)
                is Int -> this.putExtra(it.first, value)
                is Long -> this.putExtra(it.first, value)
                is CharSequence -> this.putExtra(it.first, value)
                is String -> this.putExtra(it.first, value)
                is Float -> this.putExtra(it.first, value)
                is Double -> this.putExtra(it.first, value)
                is Char -> this.putExtra(it.first, value)
                is Short -> this.putExtra(it.first, value)
                is Boolean -> this.putExtra(it.first, value)
                is Serializable -> this.putExtra(it.first, value)
                is Bundle -> this.putExtra(it.first, value)
                is Parcelable -> this.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> this.putExtra(it.first, value)
                    value.isArrayOf<String>() -> this.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> this.putExtra(it.first, value)
                    else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is IntArray -> this.putExtra(it.first, value)
                is LongArray -> this.putExtra(it.first, value)
                is FloatArray -> this.putExtra(it.first, value)
                is DoubleArray -> this.putExtra(it.first, value)
                is CharArray -> this.putExtra(it.first, value)
                is ShortArray -> this.putExtra(it.first, value)
                is BooleanArray -> this.putExtra(it.first, value)
                else -> throw IllegalArgumentException("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            return@forEach
        }
    }

    fun <T> createIntent(
        ctx: Context,
        clazz: Class<out T>,
        params: Array<out Pair<String, Any?>>
    ): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) intent.putExtras(getBundle(params))
        return intent
    }

    fun startActivity(
        ctx: Context,
        activity: Class<out Activity>,
        params: Array<out Pair<String, Any?>>
    ) {
        ctx.startActivity(createIntent(ctx, activity, params))
    }

    fun startActivityForResult(
        act: Activity,
        activity: Class<out Activity>,
        requestCode: Int,
        params: Array<out Pair<String, Any?>>
    ) {
        act.startActivityForResult(createIntent(act, activity, params), requestCode)
    }

    fun startActivityForResult(
        act: Fragment,
        activity: Class<out Activity>,
        requestCode: Int,
        params: Array<out Pair<String, Any?>>
    ) {
        act.startActivityForResult(
            createIntent(act.context.checkNull(), activity, params),
            requestCode
        )
    }

    fun startService(
        ctx: Context,
        service: Class<out Service>,
        params: Array<out Pair<String, Any?>>
    ): ComponentName? = ctx.startService(createIntent(ctx, service, params))


    fun stopService(
        ctx: Context,
        service: Class<out Service>,
        params: Array<out Pair<String, Any?>>
    ): Boolean = ctx.stopService(createIntent(ctx, service, params))
}

fun Context?.checkNull() = this ?: throw Exception("Context must not be null")

fun <T : Fragment> T.putArg(vararg params: Pair<String, Any?>): T = this.apply {
    arguments = IntentExtension.getBundle(params)
}

fun DialogFragment.putArg(vararg params: Pair<String, Any?>) = this.apply {
    arguments = IntentExtension.getBundle(params)
}

inline fun <reified T : Activity> Context?.startActivity(vararg params: Pair<String, Any?>) =
    IntentExtension.startActivity(checkNull(), T::class.java, params)

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
    IntentExtension.startActivity(this.context.checkNull(), T::class.java, params)

inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) = IntentExtension.startActivityForResult(this, T::class.java, requestCode, params)

inline fun <reified T : Activity> Fragment.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) = IntentExtension.startActivityForResult(this, T::class.java, requestCode, params)

inline fun <reified T : Activity> FragmentActivity.startActivityForResult(
    requestCode: Int,
    vararg params: Pair<String, Any?>
) = IntentExtension.startActivityForResult(this, T::class.java, requestCode, params)

inline fun <reified T : Service> Context.startService(vararg params: Pair<String, Any?>) =
    IntentExtension.startService(this, T::class.java, params)

inline fun <reified T : Service> Context.stopService(vararg params: Pair<String, Any?>) =
    IntentExtension.stopService(this, T::class.java, params)

inline fun <reified T : Any> Context.intentFor(vararg params: Pair<String, Any?>): Intent =
    IntentExtension.createIntent(this, T::class.java, params)

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.clearTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_CLEAR_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.clearTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_DOCUMENT] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.newDocument(): Intent = apply {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    } else {
        @Suppress("DEPRECATION")
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
    }
}

/**
 * Add the [Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.excludeFromRecents(): Intent =
    apply { addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS) }

/**
 * Add the [Intent.FLAG_ACTIVITY_MULTIPLE_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.multipleTask(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NEW_TASK] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.newTask(): Intent = apply { addFlags(FLAG_ACTIVITY_NEW_TASK) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_ANIMATION] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.noAnimation(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION) }

/**
 * Add the [Intent.FLAG_ACTIVITY_NO_HISTORY] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.noHistory(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY) }

/**
 * Add the [Intent.FLAG_ACTIVITY_SINGLE_TOP] flag to the [Intent].
 *
 * @return the same intent with the flag applied.
 */
inline fun Intent.singleTop(): Intent = apply { addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) }

fun Context.browse(url: String, newTask: Boolean = false): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        if (newTask) {
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun Context.share(text: String, subject: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, null))
        true
    } catch (e: ActivityNotFoundException) {
        e.printStackTrace()
        false
    }
}

fun Context.email(email: String, subject: String = "", text: String = ""): Boolean {
    val intent = Intent(Intent.ACTION_SENDTO)
    intent.data = Uri.parse("mailto:")
    intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
    if (subject.isNotEmpty())
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
    if (text.isNotEmpty())
        intent.putExtra(Intent.EXTRA_TEXT, text)
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
        return true
    }
    return false

}

fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Context.sendSMS(number: String, text: String = ""): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number"))
        intent.putExtra("sms_body", text)
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Context?.openPermission() {
    checkNull().startActivity(Intent().apply {
        action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        data = Uri.fromParts("package", checkNull().packageName, null)
    })
}

fun Activity.getIntentData(callback: Bundle.() -> Unit) {
    intent?.let {
        it.extras?.let(callback)
    }
}

fun Fragment.getArgumentData(callback: Bundle.() -> Unit) {
    arguments?.let(callback)
}

fun Context.openFacebookAccount(fbID: String, isPage: Boolean = false) {
    if (fbID.isEmpty()) throw Exception("Facebook ID must not be null.")
    try {
        packageManager.getPackageInfo("com.facebook.katana", 0)
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(String.format("fb://${if (isPage) "page" else "profile"}/%s", fbID))
            ).addFlags(FLAG_ACTIVITY_NEW_TASK)
        )
    } catch (e: Exception) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(
                    String.format(
                        if (isPage) "https://www.facebook.com/%s" else "https://www.facebook.com/profile.php?id=%s",
                        fbID
                    )
                )
            ).addFlags(FLAG_ACTIVITY_NEW_TASK)
        )
    }
}

fun Fragment.openFacebookAccount(fbID: String, isPage: Boolean = false) =
    requireContext().openFacebookAccount(fbID, isPage)

fun Context.openPlayStore() {
    try {
        val uri = Uri.parse("market://details?id=$packageName")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (e: ActivityNotFoundException) {
        browse("http://play.google.com/store/apps/details?id=$packageName")
    }
}

fun Fragment.openPlayStore() = requireContext().openPlayStore()

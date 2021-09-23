package com.kyawhut.atsycast.share.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

/**
 * @author kyawhtut
 * @date 8/31/21
 */
object ShareUtils {

    const val PLAYER_MP4_EXTENSION = "mp4"
    private const val PLAYER_M3U8_EXTENSION = "m3u8"
    private const val PLAYER_MKV_EXTENSION = "mkv"
    private const val PLAYER_MOV_EXTENSION = "mov"
    private const val PLAYER_WEBM_EXTENSION = "webm"
    private const val PLAYER_EMBED_EXTENSION = "embed"
    private const val PLAYER_YOUTUBE_EXTENSION = "youtube"

    internal const val DEVICE_PASSWORD = "DEVICE_PASSWORD"

    private val adultKey = listOf("18+", "adult")
    val String.isAdult: Boolean
        get() = adultKey.any { this.contains(it, true) }

    val String.isDirectPlayExtension: Boolean
        get() = this == PLAYER_MP4_EXTENSION || this == PLAYER_M3U8_EXTENSION || this == PLAYER_MKV_EXTENSION || this == PLAYER_MOV_EXTENSION

    val String.isWEBMExtension: Boolean
        get() = this == PLAYER_WEBM_EXTENSION

    val String.isEmbedExtension: Boolean
        get() = this == PLAYER_EMBED_EXTENSION

    val String.isYoutubeExtension: Boolean
        get() = this == PLAYER_YOUTUBE_EXTENSION

    val Context.deviceID: String
        @SuppressLint("HardwareIds")
        get() {
            return Settings.Secure.getString(
                this.contentResolver,
                Settings.Secure.ANDROID_ID
            )
        }

    fun String.toQRCode(qrColor: Int = Color.BLACK, bgColor: Int = Color.WHITE): Bitmap? {
        return try {
            val bitMatrix = QRCodeWriter().encode(
                Uri.encode(this, "utf-8"),
                BarcodeFormat.QR_CODE,
                500,
                500
            )
            val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
            for (i in 0 until 500) {
                for (j in 0 until 500) {
                    bitmap.setPixel(i, j, if (bitMatrix.get(i, j)) qrColor else bgColor)
                }
            }
            bitmap
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        } catch (e: WriterException) {
            e.printStackTrace()
            null
        }
    }

    val Context.deviceName: String
        get() = "%s - %s".format(Build.MODEL, Build.BRAND)
}

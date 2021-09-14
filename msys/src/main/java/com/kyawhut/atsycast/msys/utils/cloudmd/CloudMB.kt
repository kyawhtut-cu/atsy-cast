package com.kyawhut.atsycast.msys.utils.cloudmd

import android.net.Uri
import com.kyawhut.atsycast.msys.utils.cloudmd.tasks.DirectLinkTask
import com.kyawhut.atsycast.msys.utils.cloudmd.tasks.StreamTask
import com.kyawhut.atsycast.msys.utils.cloudmd.workers.DirectLinkTaskWorker
import com.kyawhut.atsycast.msys.utils.cloudmd.workers.StreamTaskWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.KeyManagementException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

object CloudMB {
    internal const val CLOUDE_MB_HOST = "cloudemb.com"
    private const val MSG_INVALID_HOST = "Input URL is not https://*****.com/."
    internal val socketFactory: SSLSocketFactory
        get() {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                override fun getAcceptedIssuers(): Array<out X509Certificate>? {
                    return arrayOf()
                }
            })

            try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                return sslContext.socketFactory
            } catch (e: Exception) {
                when (e) {
                    is RuntimeException, is KeyManagementException -> {
                        throw RuntimeException("Failed to create a SSL socket factory", e)
                    }
                    else -> throw e
                }
            }
        }

    fun fetch(url: String, callback: (streamTask: StreamTask) -> Unit) {
        if (url.isCloudeMBUrl()) {
            CoroutineScope(Dispatchers.IO).launch {
                val task = StreamTaskWorker.get(url)
                CoroutineScope(Dispatchers.Main).launch { callback.invoke(task) }
            }
        } else {
            callback.invoke(StreamTask(false, null, CloudeMBException(MSG_INVALID_HOST)))
        }
    }

    fun fetchDirectLink(
        media: Media,
        callback: (directLinkTask: DirectLinkTask) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = DirectLinkTaskWorker.get(media.downloadRoute)
            CoroutineScope(Dispatchers.Main).launch { callback.invoke(task) }
        }
    }

    private fun String.isCloudeMBUrl(): Boolean {
        val uri = Uri.parse(this)
        return uri != null && uri.host != null /*&& uri.host.equals(CLOUDE_MB_HOST)*/
    }
}

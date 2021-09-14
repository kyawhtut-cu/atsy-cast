package com.kyawhut.atsycast.msys.utils.cloudmd.workers

import com.kyawhut.atsycast.msys.utils.cloudmd.CloudMB
import com.kyawhut.atsycast.msys.utils.cloudmd.CloudeMBException
import com.kyawhut.atsycast.msys.utils.cloudmd.tasks.DirectLinkTask
import org.jsoup.Jsoup
import java.io.IOException

internal object DirectLinkTaskWorker {
    private const val MSG_DIRECT_LINK_NOT_FOUND = "Direct link not found in download route."

    fun get(url: String): DirectLinkTask {
        return try {
            val document = Jsoup.connect(url).sslSocketFactory(CloudMB.socketFactory).get()
            val aElement = document.selectFirst("div#container span a")
            return if (aElement != null) {
                val directUrl = aElement.attr("href")
                DirectLinkTask(true, directUrl, null)
            } else {
                DirectLinkTask(false, null, CloudeMBException(MSG_DIRECT_LINK_NOT_FOUND))
            }
        } catch (exception: IOException) {
            DirectLinkTask(false, null, with(exception.message ?: "") {
                if (this.contains(CloudMB.CLOUDE_MB_HOST)) {
                    Exception(this.replace(CloudMB.CLOUDE_MB_HOST, "https://*****.com/"))
                } else exception
            })
        }
    }
}

package com.kyawhut.atsycast.tiktok.data.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author kyawhtut
 * @date 10/4/21
 */
class HeaderInterceptor : Interceptor {

    private val headerList = listOf(
        "user-agent" to "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.71 Safari/537.36",
        "sec-fetch-site" to "same-site",
        "sec-gpc" to "1",
        "sec-fetch-mode" to "cors",
        "sec-fetch-dest" to "empty",
        "accept-language" to "en-US,en;q=0.9",
        "origin" to "https://www.tiktok.com/",
        "referer" to "https://www.tiktok.com/",
        "cookie" to "tt_webid_v2=7015032070158845445; tt_webid=7015032070158845445; tt_csrf_token=2FL1vrl8tQbJ5A9SOMx8HmKB; R6kq3TV7=AKu3IEl8AQAA8fV7AxAyjTGrOCLnJo3pXp2AOFyr7bY3BnCsDoUmPcSyL1LI|1|0|b3b19f9b1acaf402da78bcb6d3bd3de09f46b6cd; cookie-consent={%22ga%22:true%2C%22af%22:true%2C%22fbp%22:true%2C%22lip%22:true%2C%22version%22:%22v2%22}; passport_csrf_token_default=ee59762ff6c85b8809de84ebc1861ef9; passport_csrf_token=ee59762ff6c85b8809de84ebc1861ef9; d_ticket=426c110263902af1713dc904c3f49d71c8589; passport_auth_status=b76730a9462e6d72fcb17ece10638e37%2C275377176d20bf1205da075e03943d8b; passport_auth_status_ss=b76730a9462e6d72fcb17ece10638e37%2C275377176d20bf1205da075e03943d8b; sid_guard=e19cb0f6deba4cc981e9d8b1d2325375%7C1633318324%7C5184000%7CFri%2C+03-Dec-2021+03%3A32%3A04+GMT; uid_tt=86b44a20fd1761c47ac9b298087aaa2a06b07f7229116fc7142a6fa3072781d4; uid_tt_ss=86b44a20fd1761c47ac9b298087aaa2a06b07f7229116fc7142a6fa3072781d4; sid_tt=e19cb0f6deba4cc981e9d8b1d2325375; sessionid=e19cb0f6deba4cc981e9d8b1d2325375; sessionid_ss=e19cb0f6deba4cc981e9d8b1d2325375; sid_ucp_v1=1.0.0-KDM0NTY0M2E5YTkyMWJmZmE3MmM2ZTVkNmUzYjcxZDI5NTBiYjhhNjEKHwiBgJ2qmrKd01oQtOvpigYYswsgDDD6xvHbBTgIQAoQAxoGbWFsaXZhIiBlMTljYjBmNmRlYmE0Y2M5ODFlOWQ4YjFkMjMyNTM3NQ; ssid_ucp_v1=1.0.0-KDM0NTY0M2E5YTkyMWJmZmE3MmM2ZTVkNmUzYjcxZDI5NTBiYjhhNjEKHwiBgJ2qmrKd01oQtOvpigYYswsgDDD6xvHbBTgIQAoQAxoGbWFsaXZhIiBlMTljYjBmNmRlYmE0Y2M5ODFlOWQ4YjFkMjMyNTM3NQ; store-idc=alisg; store-country-code=mm; msToken=veZvf1xerEu8qj_3IyWM6mVKtcyEwTeJKaIYT5Smujlfgr2qnISWVcr89mt9rAvf6jE7gjVyK6WMfVgW2oSE7-bdqpHkH69T6HrYtJ1PBSywCteREKlMK_5Tvcn4sQ==; ttwid=1%7Ctd9pv71_RJypdfJAy4sonQRINQiMV6Z8kX7iKNa9usk%7C1633319421%7C256bb03c21c13a5a82063afa39c17c41fbf3f88f4baf3e77784b50516198f0ea; cmpl_token=AgQQAPPhF-RMpYqYmzYtdZk4-zv_s4pVv4dqYPz41Q; odin_tt=98063dec65145bc68e7efc46316a0a20f0ad37f818dda7b58d10e7c8c1e6fbd15ad12a9a01ce151fa0e7fd892624c9d12a3f1891744d463e2967c6993358bf44",
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder().apply {
                headerList.forEach {
                    addHeader(it.first, it.second)
                }
            }.build()
        )
    }
}

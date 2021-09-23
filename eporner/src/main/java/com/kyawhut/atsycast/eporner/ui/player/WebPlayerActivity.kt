package com.kyawhut.atsycast.eporner.ui.player

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.kyawhut.atsycast.eporner.R
import com.kyawhut.atsycast.eporner.databinding.ActivityWebPlayerBinding
import com.kyawhut.atsycast.eporner.utils.Constants

/**
 * @author kyawhtut
 * @date 9/22/21
 */
internal class WebPlayerActivity : FragmentActivity() {

    private val playerURL: String by lazy {
        intent?.getStringExtra(Constants.EXTRA_PLAYER_URL) ?: ""
    }

    private lateinit var vb: ActivityWebPlayerBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vb = DataBindingUtil.setContentView(this, R.layout.activity_web_player)

        CookieManager.getInstance().setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= 21) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(vb.viewWeb, true)
        }

        vb.viewWeb.apply {
            settings.apply {
                javaScriptEnabled = true
                setAppCacheEnabled(true)
                domStorageEnabled = true
                builtInZoomControls = false
                saveFormData = false
            }
            webViewClient = MyWebViewClient()
            loadUrl(playerURL)
        }
    }

    inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(
            webView: WebView,
            url: String
        ): Boolean {
            return false
        }
    }

    override fun onBackPressed() {
        if (vb.viewWeb.canGoBack()) vb.viewWeb.goBack()
        else super.onBackPressed()
    }
}

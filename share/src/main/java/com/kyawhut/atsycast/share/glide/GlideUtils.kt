package com.kyawhut.atsycast.share.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.kyawhut.atsycast.share.network.utils.UnsafeOkHttpClient
import java.io.InputStream

/**
 * @author kyawhtut
 * @date 11/03/2020
 */
@GlideModule
class GlideUtils : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val client = UnsafeOkHttpClient.getUnsafeOkHttpClient().build()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(client)
        )
    }
}

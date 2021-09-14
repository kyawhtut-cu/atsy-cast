package com.kyawhut.atsycast.share.network.providers

import retrofit2.converter.gson.GsonConverterFactory

internal fun provideGson(): GsonConverterFactory = GsonConverterFactory.create()

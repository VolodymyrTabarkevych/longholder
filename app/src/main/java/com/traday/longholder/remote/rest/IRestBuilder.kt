package com.traday.longholder.remote.rest

import okhttp3.Request
import retrofit2.Retrofit

interface IRestBuilder {

    fun build(
        baseUrl: String,
        requestBuilder: (builder: Request.Builder) -> Unit
    ): Retrofit
}
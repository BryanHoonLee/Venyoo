package com.example.venyoo.networking

import com.example.venyoo.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class RequestInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("client_id", Constants.FOURSQUARE_CLIENT_ID)
                .addQueryParameter("client_secret", Constants.FOURSQUARE_CLIENT_SECRET)
                .build()

        val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

        return chain.proceed(request    )
    }
}
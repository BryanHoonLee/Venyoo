package com.example.venyoo.networking

import com.example.venyoo.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class SetlistFMRequestInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
            .newBuilder()
            .header(Constants.SETLIST_API_KEY_PARAMETER, Constants.SETLIST_API_KEY)
            .header(Constants.SETLIST_ACCEPT_HEADER_PARAMETER, Constants.SETLIST_ACCEPT_HEADER)
            .build()

        return chain.proceed(request)
    }
}
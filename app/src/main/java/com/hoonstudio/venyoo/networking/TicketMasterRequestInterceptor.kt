package com.hoonstudio.venyoo.networking

import com.hoonstudio.venyoo.Constants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TicketMasterRequestInterceptor @Inject constructor(): Interceptor {
    override fun intercept(
            chain: Interceptor.Chain
    ): Response {

        val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter(Constants.TICKETMASTER_CLIENT_ID_PARAMETER, Constants.TICKETMASTER_CLIENT_ID)
                .build()

        val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

        return chain.proceed(request)
    }
}
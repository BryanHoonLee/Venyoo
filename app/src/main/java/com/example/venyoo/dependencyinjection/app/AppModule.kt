package com.example.venyoo.dependencyinjection.app

import android.app.Application
import com.example.venyoo.dependencyinjection.qualifiers.*
import com.example.venyoo.networking.*
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(private val application: Application) {

    @AppScope
    @Provides
    @FourSquareRetrofit
    fun fourSquareRetrofit(
        @FourSquareOkHttpClient okHttpClient: OkHttpClient,
        urlProvider: UrlProvider,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(urlProvider.fourSquareBaseUrl())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @AppScope
    @Provides
    @FourSquareOkHttpClient
    fun okHttpClientFourSquare(
        @BaseOkHttpClient okHttpClient: OkHttpClient,
        requestInterceptor: FourSquareRequestInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
                .addInterceptor(requestInterceptor)
                .build()
    }

    @AppScope
    @Provides
    fun fourSquareApi(
            @FourSquareRetrofit retrofit: Retrofit
    ): FourSquareApi {
        return retrofit.create(FourSquareApi::class.java)
    }

    @AppScope
    @Provides
    @TicketMasterRetrofit
    fun ticketMasterRetrofit(
        @TicketMasterOkHttpClient okHttpClient: OkHttpClient,
        urlProvider: UrlProvider,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(urlProvider.ticketMasterBaseUrl())
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @AppScope
    @Provides
    @TicketMasterOkHttpClient
    fun ticketMasterOkHttpClient(
        @BaseOkHttpClient okHttpClient: OkHttpClient,
        requestInterceptor: TicketMasterRequestInterceptor
    ): OkHttpClient {
        return okHttpClient.newBuilder()
                .addInterceptor(requestInterceptor)
                .build()
    }

    @AppScope
    @Provides
    fun ticketMasterApi(
            @TicketMasterRetrofit retrofit: Retrofit
    ): TicketMasterApi {
        return retrofit.create(TicketMasterApi::class.java)
    }

    /**
     * Want same instance of gsonconverterfactory because a factory holds a Gson instance.
     * The first time you request for gson to serialize/deserialize something, it figures out what object looks like and caches that
     * so next time you request it to serialize/deserialize the same object, it doesnt need to spend a lot of time again figuring out object structure.
     * Therefore, you want to be using the same Gson object so that the cache is shared.
     */
    @AppScope
    @Provides
    fun gsonConverterFactory() = GsonConverterFactory.create()

    /**
     * Want to be using same instance of okhttpclient for all retrofit instances because the client creates
     * expensive things such as threadpools, route dbs, and disk caches.
     *
     * OkHttpClient also has a newBuilder() method that allows creation of a shallow copy of an existing client.
     * ex:
     * var client = OkHttpClient()
     * var clientFoo = client.newBuilder().addInterceptor(FooInterceptor()).build
     * var clientBar = client.newBuilder().readTimeout(30, SECONDS).build()
     * All of these okhttpsclients will share same threadpool, cache, etc, but the methods are client specific.
     */
    @AppScope
    @Provides
    @BaseOkHttpClient
    fun okHttpClientBase(
    ): OkHttpClient {
        return OkHttpClient.Builder()
                .build()
    }


    @Provides
    fun application() = application
}

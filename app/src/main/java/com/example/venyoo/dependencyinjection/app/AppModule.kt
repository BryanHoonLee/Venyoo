package com.example.venyoo.dependencyinjection.app

import com.example.venyoo.networking.FourSquareApi
import com.example.venyoo.networking.RequestInterceptor
import com.example.venyoo.networking.UrlProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class AppModule(){

    /*
     Want to be using same instance of okhttpclient for all retrofit instances because the client creates
     expensive things such as threadpools, route dbs, and disk caches.

     OkHttpClient also has a newBuilder() method that allows creation of a shallow copy of an existing client.
     ex:
     var client = OkHttpClient()
     var clientFoo = client.newBuilder().addInterceptor(FooInterceptor()).build
     var clientBar = client.newBuilder().readTimeout(30, SECONDS).build()
     All of these okhttpsclients will share same threadpool, cache, etc, but the methods are client specific.
     */
    @AppScope
    @Provides
    fun okHttpClient(requestInterceptor: RequestInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
    }

    /*
    Want same instance of gsonconverterfactory because a factory holds a Gson instance.
    The first time you request for gson to serialize/deserialize something, it figures out what object looks like and caches that
    so next time you request it to serialize/deserialize the same object, it doesnt need to spend a lot of time again figuring out object structure.
    Therefore, you want to be using the same Gson object so that the cache is shared.
     */
    @AppScope
    @Provides
    fun gsonConverterFactory() = GsonConverterFactory.create()

    @AppScope
    @Provides
    fun retrofit(okHttpClient: OkHttpClient, urlProvider: UrlProvider, gsonConverterFactory: GsonConverterFactory): Retrofit{
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(urlProvider.baseUrl())
                .addConverterFactory(gsonConverterFactory)
                .build()
    }

    @AppScope
    @Provides
    fun fourSquareApi(retrofit: Retrofit): FourSquareApi{
        return retrofit.create(FourSquareApi::class.java)
    }

}
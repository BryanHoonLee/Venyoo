package com.example.venyoo.networking

import com.example.venyoo.Constants
import javax.inject.Inject


class UrlProvider @Inject constructor(){

    fun baseUrl(): String{
        return Constants.BASE_URL
    }
}
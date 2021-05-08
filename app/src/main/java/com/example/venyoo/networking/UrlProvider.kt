package com.example.venyoo.networking

import com.example.venyoo.Constants
import javax.inject.Inject


class UrlProvider @Inject constructor(){

    fun fourSquareBaseUrl(
    ): String{
        return Constants.FOURSQUARE_BASE_URL
    }

    fun ticketMasterBaseUrl(
    ): String{
        return Constants.TICKETMASTER_BASE_URL
    }

    fun ticketMasterWebsiteUrl(
    ): String{
        return Constants.TICKETMASTER_WEBSITE_URL
    }
}
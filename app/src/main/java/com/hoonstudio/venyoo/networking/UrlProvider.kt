package com.hoonstudio.venyoo.networking

import com.hoonstudio.venyoo.Constants
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

    fun setlistFMBaseUrl(
    ): String{
        return Constants.SETLIST_BASE_URL
    }
}
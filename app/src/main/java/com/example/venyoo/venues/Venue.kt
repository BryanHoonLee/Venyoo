package com.example.venyoo.venues

import com.google.gson.annotations.SerializedName

data class Venue(
        @SerializedName("name") val name: String
)
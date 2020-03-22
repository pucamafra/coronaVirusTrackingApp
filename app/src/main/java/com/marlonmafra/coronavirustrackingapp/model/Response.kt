package com.marlonmafra.coronavirustrackingapp.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Latest(
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long
)

data class Coordinate(
    val latitude:String,
    val longitude:String
)

data class Location(
    val coordinates: Coordinate,
    val country: String,
    val province: String,
    @SerializedName("country_code") val countryCode: String,
    val id: Int,
    val last_updated: Date,
    val latest: Latest
)

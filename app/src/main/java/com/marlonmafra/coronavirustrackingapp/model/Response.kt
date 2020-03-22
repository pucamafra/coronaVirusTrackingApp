package com.marlonmafra.coronavirustrackingapp.model

import com.google.gson.annotations.SerializedName
import java.util.Date

class Latest(
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long
)

class Coordinate(
    latitude:String,
    longitude:String
)

class Location(
    val coordinates: Coordinate,
    val country: String,
    @SerializedName("country_code") val countryCode: String,
    val id: Int,
    val last_updated: Date,
    val latest: Latest
)

package com.marlonmafra.coronavirustrackingapp.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.Date

data class Latest(
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long
) : Serializable

data class Coordinate(
    val latitude: String,
    val longitude: String
) : Serializable

data class Location(
    val coordinates: Coordinate,
    val country: String,
    val province: String,
    @SerializedName("country_code") val countryCode: String,
    val id: Int,
    @SerializedName("last_updated") val lastUpdated: Date,
    val latest: Latest,
    val timelines: Timeline
) : Serializable

data class Timeline(
    val confirmed: Situation,
    val deaths: Situation,
    val recovered: Situation
) : Serializable

data class Situation(
    val latest: Long,
    val timeline: Map<Date, Long>
) : Serializable


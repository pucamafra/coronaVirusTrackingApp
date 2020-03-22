package com.marlonmafra.coronavirustrackingapp.network

import io.reactivex.Single
import retrofit2.http.GET

interface TrackingApiService {

    @GET("locations")
    fun getTrackingLocations(): Single<TrackingResponse>
}
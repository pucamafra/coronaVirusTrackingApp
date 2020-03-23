package com.marlonmafra.coronavirustrackingapp.network

import com.marlonmafra.coronavirustrackingapp.model.Location
import java.io.Serializable

class TimelineResponse(
    val location: Location
) : Serializable
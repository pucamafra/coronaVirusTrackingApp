package com.marlonmafra.coronavirustrackingapp.network

import com.marlonmafra.coronavirustrackingapp.model.Latest
import com.marlonmafra.coronavirustrackingapp.model.Location
import java.io.Serializable

class TrackingResponse(
    val latest: Latest,
    val locations: List<Location>
) : Serializable
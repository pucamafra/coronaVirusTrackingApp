package com.marlonmafra.coronavirustrackingapp.extensions

import com.marlonmafra.coronavirustrackingapp.model.Location

fun Location.formatCountryName(): String {
    return when {
        province.isEmpty() -> country
        else -> "${country}/${province}"
    }
}
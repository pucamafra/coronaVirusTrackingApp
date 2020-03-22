package com.marlonmafra.coronavirustrackingapp.segmentedTab

import android.content.Context

class LeftTabView(
    context: Context,
    layout: Int
) : TabView(context, layout) {
    override fun getCornerRadii(roundRadius: Float): FloatArray = floatArrayOf(
        roundRadius,
        roundRadius,
        0f,
        0f,
        0f,
        0f,
        roundRadius,
        roundRadius
    )
}
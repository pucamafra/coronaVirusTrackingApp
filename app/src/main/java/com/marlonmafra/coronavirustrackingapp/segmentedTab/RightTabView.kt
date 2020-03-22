package com.marlonmafra.coronavirustrackingapp.segmentedTab

import android.content.Context

class RightTabView(
    context: Context,
    layout: Int
) : TabView(context, layout) {
    override fun getCornerRadii(roundRadius: Float): FloatArray =
        floatArrayOf(0f, 0f, roundRadius, roundRadius, roundRadius, roundRadius, 0f, 0f)
}
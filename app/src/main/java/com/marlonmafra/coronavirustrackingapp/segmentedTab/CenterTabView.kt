package com.marlonmafra.coronavirustrackingapp.segmentedTab

import android.content.Context

class CenterTabView(context: Context, layout: Int) : TabView(context, layout) {

    override fun getCornerRadii(roundRadius: Float): FloatArray =
        floatArrayOf(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f)
}
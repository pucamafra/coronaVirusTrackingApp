package com.marlonmafra.coronavirustrackingapp.features.details.linechart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.marlonmafra.coronavirustrackingapp.extensions.formatTo
import java.util.Date

class TimelineValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        return Date(value.toLong()).formatTo("MMM dd, yyyy")
    }
}
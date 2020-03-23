package com.marlonmafra.coronavirustrackingapp.features.details

import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.model.Location
import com.marlonmafra.coronavirustrackingapp.model.Situation
import kotlinx.android.synthetic.main.fragment_line_chart.chart

const val LINE_CHART_LOCATION_EXTRA = "location"

class LineChartFragment : Fragment() {

    private val location: Location by lazy { arguments?.getSerializable(LINE_CHART_LOCATION_EXTRA) as Location }

    companion object {
        fun newInstance(location: Location) = LineChartFragment().apply {
            arguments = Bundle().apply {
                putSerializable(LINE_CHART_LOCATION_EXTRA, location)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_line_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
        setData()
    }

    private fun setupChart() {
        chart.description.isEnabled = false
        chart.setTouchEnabled(false)
        chart.dragDecelerationFrictionCoef = 0.9f
        chart.isDragEnabled = false
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.isHighlightPerDragEnabled = true
        chart.setPinchZoom(true)
        chart.setBackgroundColor(Color.WHITE)
        chart.animateX(1500)

        val l = chart.legend
        l.form = LegendForm.CIRCLE
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)

        val xAxis: XAxis = chart.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(true)
        xAxis.setDrawAxisLine(true)
        xAxis.labelRotationAngle = -50f
        xAxis.valueFormatter = TimelineValueFormatter()
        xAxis.setDrawGridLines(true)
        xAxis.setLabelCount(15,true)
    }

    private fun setData() = with(location.timelines) {
        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(
            setupData(
                confirmed,
                ContextCompat.getColor(requireContext(), R.color.orange),
                R.string.confirmed
            )
        ) // add the data sets
        dataSets.add(
            setupData(
                deaths,
                ContextCompat.getColor(requireContext(), R.color.red),
                R.string.dead
            )
        ) // add the data sets
        dataSets.add(
            setupData(
                recovered,
                ContextCompat.getColor(requireContext(), R.color.green),
                R.string.recovered
            )
        ) // add the data sets
        val data = LineData(dataSets)
        data.setDrawValues(false)
        chart.data = data
        chart.invalidate()
    }

    private fun setupData(situation: Situation, color: Int, label: Int): LineDataSet {
        val labelString = getString(label)
        println("Label: $labelString")
        val entries: ArrayList<Entry> = ArrayList()
        for ((key, value) in situation.timeline) {
            println("Key: ${key.time} , Value:${value.toFloat()}")
            entries.add(
                Entry(
                    key.time.toFloat(),
                    value.toFloat(),
                    value
                )
            )
        }


        return LineDataSet(entries, labelString).apply {
            this.color = color
            setCircleColor(color)
            lineWidth = 5f
            circleRadius = 5f
            setDrawCircleHole(true)
            formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            formSize = 15f
            valueTextSize = 9f
            enableDashedHighlightLine(10f, 5f, 0f)
            setDrawFilled(false)
        }
    }
}

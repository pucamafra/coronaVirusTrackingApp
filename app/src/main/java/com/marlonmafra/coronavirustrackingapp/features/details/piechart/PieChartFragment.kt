package com.marlonmafra.coronavirustrackingapp.features.details.piechart

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.features.details.CountryDetailsViewModel
import com.marlonmafra.coronavirustrackingapp.model.Location
import kotlinx.android.synthetic.main.fragment_pie_chart.chart

class PieChartFragment : Fragment() {

    companion object {
        fun newInstance() =
            PieChartFragment()
    }

    private val countryDetailsViewModel: CountryDetailsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_pie_chart, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupChart()
        countryDetailsViewModel.location.observe(requireActivity(), Observer {
            setData(it)
        })
    }

    private fun setupChart() {
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.setExtraOffsets(5f, 10f, 5f, 5f)
        chart.dragDecelerationFrictionCoef = 0.95f

        chart.isDrawHoleEnabled = true
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 50f
        chart.transparentCircleRadius = 61f

        chart.setDrawCenterText(true)
        chart.setHoleColor(Color.WHITE)

        chart.setTransparentCircleColor(Color.WHITE)
        chart.setTransparentCircleAlpha(110)

        chart.holeRadius = 58f
        chart.transparentCircleRadius = 61f
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true

        chart.rotationAngle = 0.0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.setDrawEntryLabels(true)
        chart.setExtraOffsets(20f, 0f, 20f, 0f)
        chart.animateY(1400, Easing.EaseInOutQuad)

        // chart.spin(2000, 0, 360);
        val l = chart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = false

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE)
        chart.setEntryLabelTextSize(12f)
    }

    private fun setData(location: Location) = with(location.latest) {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(confirmed.toFloat(), getString(R.string.confirmed)))
        entries.add(PieEntry(deaths.toFloat(), getString(R.string.dead)))
        entries.add(PieEntry(recovered.toFloat(), getString(R.string.recovered)))

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 3f
        dataSet.valueLinePart1OffsetPercentage = 80f
        dataSet.valueLinePart1Length = 0.2f
        dataSet.valueLinePart2Length = 0.4f
        dataSet.selectionShift = 5f
        val data = PieData(dataSet)

        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        val colorFirst = ContextCompat.getColor(requireContext(), R.color.orange)
        val colorSecond = ContextCompat.getColor(requireContext(), R.color.red)
        val colorThird = ContextCompat.getColor(requireContext(), R.color.green)
        dataSet.colors = mutableListOf(colorFirst, colorSecond, colorThird)

        data.setValueFormatter(PercentFormatter(chart))
        data.setValueTextSize(12f)
        data.setValueTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.chart_outside_legend_text_color
            )
        )
        data.isHighlightEnabled = true
        chart.data = data
        chart.invalidate()
    }
}

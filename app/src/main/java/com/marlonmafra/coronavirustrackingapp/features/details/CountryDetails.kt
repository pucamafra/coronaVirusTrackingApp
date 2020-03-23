package com.marlonmafra.coronavirustrackingapp.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.blongho.country_data.World
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.format
import com.marlonmafra.coronavirustrackingapp.extensions.formatTo
import com.marlonmafra.coronavirustrackingapp.features.home.SubTabAdapter
import com.marlonmafra.coronavirustrackingapp.model.Location
import kotlinx.android.synthetic.main.activity_country_details.countryName
import kotlinx.android.synthetic.main.activity_country_details.flag
import kotlinx.android.synthetic.main.activity_country_details.latestUpdateValue
import kotlinx.android.synthetic.main.activity_country_details.scrollView
import kotlinx.android.synthetic.main.activity_main.segmentedTab
import kotlinx.android.synthetic.main.activity_main.viewPager
import kotlinx.android.synthetic.main.fragment_overview.localConfirmedView
import kotlinx.android.synthetic.main.fragment_overview.localDeadView
import kotlinx.android.synthetic.main.fragment_overview.localRecoveredView

const val LOCATION_EXTRA = "location"

class CountryDetails : AppCompatActivity() {

    private val location: Location by lazy { intent.extras?.getSerializable(LOCATION_EXTRA) as Location }

    companion object {
        fun newInstance(context: Context, location: Location) =
            Intent(context, CountryDetails::class.java).apply {
                putExtra(LOCATION_EXTRA, location)
            }
    }

    private lateinit var subTabAdapter: SubTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        setupLayout()
        setup()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finishAndSlideRight()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupLayout() = with(location) {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        flag.setImageResource(World.getFlagOf(country))
        countryName.text = country
        latestUpdateValue.text = lastUpdated.formatTo("MMM dd, HH:mm a")
        localConfirmedView.setValue(latest.confirmed.format())
        localDeadView.setValue(latest.deaths.format())
        localRecoveredView.setValue(latest.recovered.format())
    }

    private fun setup() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titles: MutableList<Int> = ArrayList()
        titles.add(R.string.pie_chart)
        titles.add(R.string.line_chart)

        fragmentList.add(PieChartFragment.newInstance(location))
        fragmentList.add(LineChartFragment.newInstance(location))

        subTabAdapter = SubTabAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = subTabAdapter
        segmentedTab.setupWithViewPager(viewPager)
        segmentedTab.setup(titles)
        scrollView.isFillViewport = true
    }

    private fun finishAndSlideRight() {
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }
}

package com.marlonmafra.coronavirustrackingapp.features.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blongho.country_data.World
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.changeVisibility
import com.marlonmafra.coronavirustrackingapp.extensions.format
import com.marlonmafra.coronavirustrackingapp.extensions.formatCountryName
import com.marlonmafra.coronavirustrackingapp.extensions.formatTo
import com.marlonmafra.coronavirustrackingapp.extensions.showSnackBar
import com.marlonmafra.coronavirustrackingapp.features.details.linechart.LineChartFragment
import com.marlonmafra.coronavirustrackingapp.features.details.piechart.PieChartFragment
import com.marlonmafra.coronavirustrackingapp.features.home.SubTabAdapter
import com.marlonmafra.coronavirustrackingapp.model.Location
import kotlinx.android.synthetic.main.activity_country_details.countryName
import kotlinx.android.synthetic.main.activity_country_details.flag
import kotlinx.android.synthetic.main.activity_country_details.latestUpdateValue
import kotlinx.android.synthetic.main.activity_country_details.localConfirmedView
import kotlinx.android.synthetic.main.activity_country_details.localDeadView
import kotlinx.android.synthetic.main.activity_country_details.localRecoveredView
import kotlinx.android.synthetic.main.activity_country_details.rootLayout
import kotlinx.android.synthetic.main.activity_country_details.segmentedTab
import kotlinx.android.synthetic.main.activity_country_details.viewPager
import kotlinx.android.synthetic.main.loading_progress_bar.loadingProgressBar
import javax.inject.Inject

const val LOCATION_EXTRA = "location"

class CountryDetails : AppCompatActivity() {

    private val location: Location by lazy { intent.extras?.getSerializable(LOCATION_EXTRA) as Location }

    companion object {
        fun newInstance(context: Context, location: Location) =
            Intent(context, CountryDetails::class.java).apply {
                putExtra(LOCATION_EXTRA, location)
            }
    }

    @Inject
    lateinit var factory: CountryDetailsViewModelFactory
    private val countryDetailsViewModel: CountryDetailsViewModel by lazy {
        ViewModelProvider(this, factory).get(
            CountryDetailsViewModel::class.java
        )
    }

    private lateinit var subTabAdapter: SubTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)
        CoronaTrackingApplication.appComponent.inject(this)
        setupLayout()
        setupTabs()
        bindObservables()
        countryDetailsViewModel.loadTimeline(location.id)
    }

    private fun bindObservables() {
        countryDetailsViewModel.progressBar.observe(this, Observer {
            loadingProgressBar.changeVisibility(it)
        })

        countryDetailsViewModel.error.observe(this, Observer { showErrorMessage() })
    }

    private fun showErrorMessage() {
        showSnackBar(rootLayout, R.string.error_try_again)
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
        countryName.text = formatCountryName()
        latestUpdateValue.text = lastUpdated.formatTo("MMM dd, HH:mm a")
        localConfirmedView.setValue(latest.confirmed.format())
        localDeadView.setValue(latest.deaths.format())
        localRecoveredView.setValue(latest.recovered.format())
    }

    private fun setupTabs() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titles: MutableList<Int> = ArrayList()
        titles.add(R.string.pie_chart)
        titles.add(R.string.line_chart)

        fragmentList.add(PieChartFragment.newInstance())
        fragmentList.add(LineChartFragment.newInstance())

        subTabAdapter = SubTabAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = subTabAdapter
        segmentedTab.setupWithViewPager(viewPager)
        segmentedTab.setup(titles)
    }

    private fun finishAndSlideRight() {
        finish()
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out)
    }
}

package com.marlonmafra.coronavirustrackingapp.features.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.showSnackBar
import com.marlonmafra.coronavirustrackingapp.features.home.countries.CountriesFragment
import com.marlonmafra.coronavirustrackingapp.features.home.overview.OverviewFragment
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import kotlinx.android.synthetic.main.activity_main.adView
import kotlinx.android.synthetic.main.activity_main.segmentedTab
import kotlinx.android.synthetic.main.activity_main.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.viewPager
import javax.inject.Inject

const val TRACKING_DATA = "TRACKING_DATA"

class MainActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context, trackingResponse: TrackingResponse) =
            Intent(context, MainActivity::class.java).apply {
                putExtra(TRACKING_DATA, trackingResponse)
            }
    }

    private val trackingResponse: TrackingResponse by lazy {
        intent.extras?.getSerializable(
            TRACKING_DATA
        ) as TrackingResponse
    }

    @Inject
    lateinit var factory: HomeViewModelFactory
    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProvider(this, factory).get(
            HomeViewModel::class.java
        )
    }
    private lateinit var subTabAdapter: SubTabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoronaTrackingApplication.appComponent.inject(this)
        setup()
        bindObservables()
    }

    private fun bindObservables() {
        homeViewModel.handleResponse(trackingResponse)
        homeViewModel.progressBar.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
        homeViewModel.error.observe(this, Observer {
            showErrorMessage()
        })
    }

    private fun showErrorMessage() {
        showSnackBar(swipeRefreshLayout, R.string.error_try_again)
    }

    private fun setup() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titles: MutableList<Int> = ArrayList()
        titles.add(R.string.overview)
        titles.add(R.string.countries)

        fragmentList.add(OverviewFragment.newInstance())
        fragmentList.add(CountriesFragment.newInstance())

        subTabAdapter = SubTabAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = this.subTabAdapter
        segmentedTab.setupWithViewPager(viewPager)
        segmentedTab.setup(titles)

        with(swipeRefreshLayout) {
            setOnRefreshListener { homeViewModel.load() }
            setColorSchemeColors(*resources.getIntArray(R.array.swipe_refresh_colors))
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }
}

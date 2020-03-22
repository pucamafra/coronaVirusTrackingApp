package com.marlonmafra.coronavirustrackingapp.feafures.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.formatTo
import com.marlonmafra.coronavirustrackingapp.feafures.home.overview.OverviewFragment
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import kotlinx.android.synthetic.main.activity_main.latestUpdateValue
import kotlinx.android.synthetic.main.activity_main.segmentedTab
import kotlinx.android.synthetic.main.activity_main.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.viewPager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

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


        homeViewModel.load()
        homeViewModel.locations.observe(this, Observer { response ->
            setupLayout(response)
            swipeRefreshLayout.isRefreshing = false
        })
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.load() }
        setup()
    }

    private fun setup() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        val titles: MutableList<String> = ArrayList()
        titles.add("Overview")
        titles.add("Table")

        fragmentList.add(OverviewFragment.newInstance())
        fragmentList.add(OverviewFragment.newInstance())

        subTabAdapter = SubTabAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = this.subTabAdapter
        segmentedTab.setupWithViewPager(viewPager)
        segmentedTab.setup(titles)
    }

    private fun setupLayout(response: TrackingResponse) = with(response) {
        latestUpdateValue.text = System.currentTimeMillis().formatTo("MMM dd, HH:mm a")
    }
}

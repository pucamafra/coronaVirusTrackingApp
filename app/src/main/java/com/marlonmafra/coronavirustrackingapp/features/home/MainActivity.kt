package com.marlonmafra.coronavirustrackingapp.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.features.home.countries.CountriesFragment
import com.marlonmafra.coronavirustrackingapp.features.home.overview.OverviewFragment
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
            swipeRefreshLayout.isRefreshing = false
        })
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.load() }
        setup()
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
    }
}

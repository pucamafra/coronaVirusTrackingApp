package com.marlonmafra.coronavirustrackingapp.feafures.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.marlonmafra.coronavirustrackingapp.CoronaTrackingApplication
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.format
import com.marlonmafra.coronavirustrackingapp.extensions.formatTo
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import kotlinx.android.synthetic.main.activity_main.confirmedView
import kotlinx.android.synthetic.main.activity_main.deadView
import kotlinx.android.synthetic.main.activity_main.latestUpdateValue
import kotlinx.android.synthetic.main.activity_main.recoveredView
import kotlinx.android.synthetic.main.activity_main.swipeRefreshLayout
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: HomeViewModelFactory
    lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CoronaTrackingApplication.appComponent.inject(this)
        homeViewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        homeViewModel.load()
        homeViewModel.locations.observe(this, Observer { response ->
            setupLayout(response)
            swipeRefreshLayout.isRefreshing = false
        })
        swipeRefreshLayout.setOnRefreshListener { homeViewModel.load() }
    }

    private fun setupLayout(response: TrackingResponse) = with(response) {
        confirmedView.setValue(latest.confirmed.format())
        deadView.setValue(latest.deaths.format())
        recoveredView.setValue(latest.recovered.format())
        //yyyy-MM-dd'T'HH:mm:ss.000'Z'
        latestUpdateValue.text = System.currentTimeMillis().formatTo("MMM dd, HH:mm a")
    }
}

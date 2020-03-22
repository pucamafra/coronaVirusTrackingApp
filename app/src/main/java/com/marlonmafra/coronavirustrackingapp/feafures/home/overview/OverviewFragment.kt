package com.marlonmafra.coronavirustrackingapp.feafures.home.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.marlonmafra.coronavirustrackingapp.R
import com.marlonmafra.coronavirustrackingapp.extensions.format
import com.marlonmafra.coronavirustrackingapp.feafures.home.HomeViewModel
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import kotlinx.android.synthetic.main.fragment_overview.confirmedView
import kotlinx.android.synthetic.main.fragment_overview.deadView
import kotlinx.android.synthetic.main.fragment_overview.recoveredView

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_overview, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.locations.observe(requireActivity(), Observer { response ->
            setupLayout(response)
        })
    }

    private fun setupLayout(response: TrackingResponse) = with(response) {
        confirmedView.setValue(latest.confirmed.format())
        deadView.setValue(latest.deaths.format())
        recoveredView.setValue(latest.recovered.format())
    }
}

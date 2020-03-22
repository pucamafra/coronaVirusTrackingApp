package com.marlonmafra.coronavirustrackingapp.feafures.home.overview

import android.content.Context
import android.os.Bundle
import android.telephony.TelephonyManager
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
import kotlinx.android.synthetic.main.fragment_overview.globalConfirmedView
import kotlinx.android.synthetic.main.fragment_overview.globalDeadView
import kotlinx.android.synthetic.main.fragment_overview.globalRecoveredView
import kotlinx.android.synthetic.main.fragment_overview.localConfirmedView
import kotlinx.android.synthetic.main.fragment_overview.localDeadView
import kotlinx.android.synthetic.main.fragment_overview.localRecoveredView
import kotlinx.android.synthetic.main.fragment_overview.yourLocationLabel

class OverviewFragment : Fragment() {

    companion object {
        fun newInstance() = OverviewFragment()
    }

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val telephonyManager: TelephonyManager by lazy {
        requireActivity().getSystemService(
            Context.TELEPHONY_SERVICE
        ) as TelephonyManager
    }

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
        globalConfirmedView.setValue(latest.confirmed.format())
        globalDeadView.setValue(latest.deaths.format())
        globalRecoveredView.setValue(latest.recovered.format())

        val countryCodeValue = telephonyManager.networkCountryIso
        response.locations.find { it.countryCode.equals(countryCodeValue, true) }?.let {
            yourLocationLabel.text = when {
                it.province.isEmpty() -> it.country
                else -> "${it.country}/${it.province}"
            }

            localConfirmedView.setValue(it.latest.confirmed.format())
            localDeadView.setValue(it.latest.deaths.format())
            localRecoveredView.setValue(it.latest.recovered.format())
        }
    }
}

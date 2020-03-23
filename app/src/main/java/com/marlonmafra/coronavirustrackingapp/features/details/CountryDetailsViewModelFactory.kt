package com.marlonmafra.coronavirustrackingapp.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import javax.inject.Inject

class CountryDetailsViewModelFactory @Inject constructor(
    private val dataSource: CoronaTrackingDataSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(CountryDetailsViewModel::class.java) -> CountryDetailsViewModel(
                dataSource
            )
            else ->
                throw IllegalArgumentException("UnKnown class")
        }
    } as T
}
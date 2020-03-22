package com.marlonmafra.coronavirustrackingapp.feafures.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import javax.inject.Inject

class HomeViewModelFactory @Inject constructor(
    private val dataSource: CoronaTrackingDataSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(dataSource)
            else ->
                throw IllegalArgumentException("UnKnown class")
        }
    } as T
}
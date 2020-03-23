package com.marlonmafra.coronavirustrackingapp.features.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import javax.inject.Inject

class SplashViewModelFactory @Inject constructor(
    private val dataSource: CoronaTrackingDataSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = with(modelClass) {
        when {
            isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel(dataSource)
            else ->
                throw IllegalArgumentException("UnKnown class")
        }
    } as T
}
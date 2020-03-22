package com.marlonmafra.coronavirustrackingapp.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val dataSource: CoronaTrackingDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val locations: MutableLiveData<TrackingResponse> by lazy { MutableLiveData<TrackingResponse>() }

    fun load() {
        compositeDisposable.add(
            dataSource.getTrackingLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    locations.value = it
                }, {
                    println()
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
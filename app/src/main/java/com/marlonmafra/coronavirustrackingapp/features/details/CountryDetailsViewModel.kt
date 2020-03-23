package com.marlonmafra.coronavirustrackingapp.features.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import com.marlonmafra.coronavirustrackingapp.model.Location
import com.marlonmafra.coronavirustrackingapp.network.TimelineResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CountryDetailsViewModel(
    private val dataSource: CoronaTrackingDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val location: MutableLiveData<Location> by lazy { MutableLiveData<Location>() }
    val progressBar: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun loadTimeline(id: Int) {
        compositeDisposable.add(
            dataSource.getTimeline(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { progressBar.value = true }
                .doFinally { progressBar.value = false }
                .subscribe({ handleResponse(it) }, {
                    println()
                })
        )
    }

    private fun handleResponse(response: TimelineResponse) {
        location.value = response.location
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
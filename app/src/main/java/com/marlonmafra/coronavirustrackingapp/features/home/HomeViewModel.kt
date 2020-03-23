package com.marlonmafra.coronavirustrackingapp.features.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.marlonmafra.coronavirustrackingapp.data.CoronaTrackingDataSource
import com.marlonmafra.coronavirustrackingapp.features.home.countries.CountryListItem
import com.marlonmafra.coronavirustrackingapp.model.Location
import com.marlonmafra.coronavirustrackingapp.network.TrackingResponse
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel(
    private val dataSource: CoronaTrackingDataSource
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val error: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    val progressBar: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val locations: MutableLiveData<TrackingResponse> by lazy { MutableLiveData<TrackingResponse>() }
    val countryList: MutableLiveData<List<AbstractFlexibleItem<*>>> by lazy { MutableLiveData<List<AbstractFlexibleItem<*>>>() }
    val selectedLocation: MutableLiveData<Location> by lazy { MutableLiveData<Location>() }

    fun load() {
        compositeDisposable.add(
            dataSource.getTrackingLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { progressBar.value = true }
                .doFinally { progressBar.value = false }
                .subscribe({ handleResponse(it) }, {
                    error.value = Unit
                })
        )
    }

    fun handleResponse(response: TrackingResponse) {
        locations.value = response
        val items = mutableListOf<AbstractFlexibleItem<*>>()
        response.locations.forEach { location ->
            items.add(CountryListItem(location, selectedLocation))
        }
        countryList.value = items
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
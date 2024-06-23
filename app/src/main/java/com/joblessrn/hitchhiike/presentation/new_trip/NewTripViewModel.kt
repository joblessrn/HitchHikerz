package com.joblessrn.hitchhiike.presentation.new_trip

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joblessrn.hitchhiike.data.remote.RetrofitInstanceGeocoder
import com.joblessrn.hitchhiike.data.remote.RetrofitInstanceSuggester
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.TripToPost
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NewTripViewModel : ViewModel() {

    var trip = TripToPost()

    private val _suggestState = mutableStateOf(Suggests(emptyList()))
    val suggestState: State<Suggests> = _suggestState
    private var suggestsJob: Job? = null
    private val _query = MutableStateFlow("")

    private val _coordinateState = mutableStateOf(Point(43.115111, 131.885376))
    val coordinateState: State<Point> = _coordinateState
    private val placeToSearch = MutableStateFlow("")
    private var coordinatesJob: Job? = null

    fun observeSuggestions() {
        suggestsJob?.cancel()
        suggestsJob = viewModelScope.launch {
            _query
                .debounce(300)
                .distinctUntilChanged()
                .collect { prompt ->
                    if (prompt.isNotEmpty()) {
                        _suggestState.value =
                            RetrofitInstanceSuggester.retrofit.getCitySuggests(place = prompt)
                    } else _suggestState.value = Suggests(emptyList())
                }
        }
    }

    fun stopObservingQuery() {
        suggestsJob?.cancel()
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun observeCoordinates() {
        coordinatesJob?.cancel()
        coordinatesJob = viewModelScope.launch {
            placeToSearch.collect { prompt ->
                if (prompt.isNotEmpty()) {
                    val response = RetrofitInstanceGeocoder.retrofit.getCityCoordinates(place = prompt)
                    response?.let {
                        _coordinateState.value = it

                    }
                }
            }
        }
    }

    fun updatePlace(place: String) {
        placeToSearch.value = place
    }
}


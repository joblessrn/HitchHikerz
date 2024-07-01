package com.joblessrn.hitchhiike.presentation.new_trip

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joblessrn.hitchhiike.data.remote.RetrofitInstanceGeocoder
import com.joblessrn.hitchhiike.data.remote.RetrofitInstanceSuggester
import com.joblessrn.hitchhiike.data.remote.models.Suggest
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.TripToPost
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class NewTripViewModel : ViewModel() {

    val tripToPost = mutableStateOf(TripToPost())

    private val _suggestState = mutableStateOf(Suggests(emptyList()))
    val suggestState: State<Suggests> = _suggestState
    private var suggestsJob: Job? = null
    private val _query = MutableStateFlow("")

    private val _coordinateState = mutableStateOf(Point(55.755799, 37.617617))
    val coordinateState: State<Point> = _coordinateState
    private val placeToSearch = MutableStateFlow("")
    private var coordinatesJob: Job? = null

    fun observeSuggestions(whatToObserve: ObservingDestination) {
        suggestsJob?.cancel()
        suggestsJob = viewModelScope.launch {
            _query.collect { prompt ->
                if (prompt.isNotBlank()) {
                    when (whatToObserve) {
                        ObservingDestination.FROM_LOCALITY, ObservingDestination.TO_LOCALITY -> {
                            _suggestState.value =
                                RetrofitInstanceSuggester.retrofit.getCitySuggests(place = prompt)
                            Log.d("suggests", "suggests = ${suggestState.value}")
                        }

                        ObservingDestination.FROM_ADDRESS -> {
                            _suggestState.value =
                                RetrofitInstanceSuggester.retrofit.getAddressSuggests(place = "${tripToPost.value.fromCity} $prompt}")
                            Log.d("suggests", "suggests = ${suggestState.value}")
                        }

                        ObservingDestination.TO_ADDRESS -> {
                            _suggestState.value =
                                RetrofitInstanceSuggester.retrofit.getAddressSuggests(place = "${tripToPost.value.toCity} $prompt}")
                            Log.d("suggests", "suggests = ${suggestState.value}")
                        }
                    }
                } else _suggestState.value = Suggests(emptyList())
            }
        }
    }

    fun clearSuggestionsStopObserving() {
        _suggestState.value = Suggests(emptyList())
        _query.value = ""
        suggestsJob?.cancel()
        Log.d("suggests", "suggests = ${suggestState.value}")
    }

    fun stopObservingQuery() {
        suggestsJob?.cancel()
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

    fun onSuggestionSelected(
        suggestion: Suggest,
        destination: ObservingDestination,
        onCompletion: () -> Unit
    ) {
        when (destination) {
            ObservingDestination.FROM_LOCALITY -> {
                viewModelScope.launch {
                    val response =
                        RetrofitInstanceGeocoder.retrofit.getCityCoordinates(place = suggestion.place)
                    response.let {
                        tripToPost.value.fromGeoTag = it
                        tripToPost.value.fromCountry = suggestion.country
                        tripToPost.value.fromCity = suggestion.place
                        Log.d("taggz","${tripToPost.value.fromGeoTag?.longitude},${tripToPost.value.fromGeoTag?.latitude}")
                        onCompletion()
                    }
                }
            }

            ObservingDestination.TO_LOCALITY -> {
                viewModelScope.launch {
                    val response =
                        RetrofitInstanceGeocoder.retrofit.getCityCoordinates(place = suggestion.place)
                    response.let {
                        tripToPost.value.toCountry = suggestion.country
                        tripToPost.value.toGeoTag = it
                        tripToPost.value.toCity = suggestion.place
                        Log.d("taggz","${tripToPost.value.fromGeoTag?.longitude},${tripToPost.value.fromGeoTag?.latitude}")
                        onCompletion()
                    }
                }
            }

            ObservingDestination.FROM_ADDRESS -> {
                tripToPost.value.fromAddress = suggestion.place
                Log.d("taggz","${tripToPost.value.fromGeoTag?.longitude},${tripToPost.value.fromGeoTag?.latitude}")
                onCompletion()
            }

            ObservingDestination.TO_ADDRESS -> {
                tripToPost.value.toAddress = suggestion.place
                Log.d("taggz","${tripToPost.value.fromGeoTag?.longitude},${tripToPost.value.fromGeoTag?.latitude}")
                onCompletion()
            }
        }
    }

    fun observeCoordinates() {
        coordinatesJob?.cancel()
        coordinatesJob = viewModelScope.launch {
            placeToSearch.collect { prompt ->
                if (prompt.isNotEmpty()) {
                    val response =
                        RetrofitInstanceGeocoder.retrofit.getCityCoordinates(place = prompt)
                    response?.let {
                        _coordinateState.value = it.toPoint()
                    }
                }
            }
        }
    }

    fun updatePlace(place: String) {
        placeToSearch.value = place
    }
}

enum class ObservingDestination() {
    FROM_LOCALITY,
    TO_LOCALITY,
    FROM_ADDRESS,
    TO_ADDRESS
}




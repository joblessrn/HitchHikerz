package com.joblessrn.hitchhiike.presentation.new_trip

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joblessrn.hitchhiike.data.remote.RetrofitInstanceSuggester
import com.joblessrn.hitchhiike.data.remote.models.Suggests
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NewTripViewModel : ViewModel() {


    private val _suggestState = mutableStateOf(Suggests(emptyList()))
    val suggestState: State<Suggests> = _suggestState

    private var debounceJob: Job? = null

    private val _query = MutableStateFlow("")
    fun getSuggests() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            _query
                //.debounce(300)
                //.distinctUntilChanged()
                .collect { prompt ->
                    if (prompt.isNotEmpty()) {
                        _suggestState.value =
                            RetrofitInstanceSuggester.retrofit.getSuggests(place = prompt)
                    } else _suggestState.value = Suggests(emptyList())
                }
        }
    }
    fun stopObservingQuery() {
        debounceJob?.cancel()
    }

    fun updateQuery(query: String) {
        _query.value = query
    }

}


package com.joblessrn.hitchhiike.presentation.new_trip.as_passenger

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TripsList(){
    LazyColumn(modifier = Modifier.fillMaxSize()) {

    }
}


@Composable
@Preview(showBackground = true,
         showSystemUi = true)
fun TripsListPreview(){
    TripsList()
}
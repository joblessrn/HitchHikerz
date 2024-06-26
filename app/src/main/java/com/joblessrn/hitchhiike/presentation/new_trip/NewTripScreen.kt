package com.joblessrn.hitchhiike.presentation.new_trip

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceTextButton
import com.joblessrn.hitchhiike.presentation.navgraph.Route

@Composable
fun NewTripScreen(nav: NavController,
                  vm:NewTripViewModel){
    Box(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier.align(Alignment.Center)) {
            NiceTextButton(text = "Как пассажир") {
                nav.navigate(Route.NewTripTab.FindTrip.route)
            }
            NiceTextButton(text = "Как водитель") {
                nav.navigate(Route.NewTripTab.NewTripForm.route)
            }

        }
    }
}
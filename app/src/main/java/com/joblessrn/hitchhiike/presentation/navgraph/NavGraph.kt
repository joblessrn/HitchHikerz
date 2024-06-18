package com.joblessrn.hitchhiike.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.joblessrn.hitchhiike.presentation.account.AccountScreen
import com.joblessrn.hitchhiike.presentation.archived_trips.ArchivedTripsScreen
import com.joblessrn.hitchhiike.presentation.dialogs.DialogsScreen
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripScreen
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.PostTripDriver
import com.joblessrn.hitchhiike.presentation.new_trip.as_passenger.FindNewTripPassenger

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Route.NewTripTab.route) {
        navigation(
            route = Route.NewTripTab.route,
            startDestination = Route.NewTripTab.NewTripScreen.route
        ) {
            composable(route = Route.NewTripTab.NewTripScreen.route) {
                NewTripScreen(nav = navController)
            }
            composable(route = Route.NewTripTab.NewTripPassenger.route) {
                FindNewTripPassenger()
            }
            composable(route = Route.NewTripTab.NewTripDriver.route) {
                PostTripDriver()
            }
        }

        navigation(route = Route.DialogsTab.route, startDestination = Route.DialogsTab.DialogsScreen.route){
            composable(route = Route.DialogsTab.DialogsScreen.route){
                DialogsScreen()
            }
        }

        navigation(route = Route.ArchivedTripsTab.route, startDestination = Route.ArchivedTripsTab.ArchivedTripsScreen.route){
            composable(route = Route.ArchivedTripsTab.ArchivedTripsScreen.route){
                ArchivedTripsScreen()
            }
        }
        navigation(route = Route.AccountTab.route, startDestination = Route.AccountTab.AccountScreen.route){
            composable(route = Route.AccountTab.AccountScreen.route){
                AccountScreen()
            }
        }
    }
}


@Preview
@Composable
fun NavGraphPreview() {

}
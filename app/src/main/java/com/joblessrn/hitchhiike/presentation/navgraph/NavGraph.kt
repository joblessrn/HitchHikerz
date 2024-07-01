package com.joblessrn.hitchhiike.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.joblessrn.hitchhiike.presentation.account.AccountScreen
import com.joblessrn.hitchhiike.presentation.archived_trips.ArchivedTripsScreen
import com.joblessrn.hitchhiike.presentation.dialogs.DialogsScreen
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripScreen
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingDestination
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.CheckScreen
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.MapScreen
import com.joblessrn.hitchhiike.presentation.new_trip.as_driver.NewTripForm
import com.joblessrn.hitchhiike.presentation.new_trip.as_passenger.FindTrip
import com.joblessrn.hitchhiike.presentation.new_trip.as_passenger.TripsList
import com.joblessrn.hitchhiike.presentation.new_trip.components.TextInputScreen

@Composable
fun NavGraph(navController: NavHostController,modifier: Modifier) {

    NavHost(navController = navController,
        startDestination = Route.NewTripTab.route,
        modifier = modifier) {
        navigation(
            route = Route.NewTripTab.route,
            startDestination = Route.NewTripTab.NewTripScreen.route
        ) {

            composable(route = Route.NewTripTab.NewTripScreen.route) {
                NewTripScreen(nav = navController)
            }
            composable(route = Route.NewTripTab.FindTripScreen.route) {
                val newTripViewModel = it.sharedVM<NewTripViewModel>(navController = navController)
                FindTrip {
                    navController.navigate(Route.NewTripTab.TripsListScreen.route)
                }
            }
            composable(
                route = "${Route.NewTripTab.MapScreen.route}/{searchtype}",
                arguments = listOf(navArgument("searchtype") {
                    type = NavType.StringType
                })
            ) {
                val newTripViewModel = it.sharedVM<NewTripViewModel>(navController = navController)
                val type = it.arguments?.getString("searchtype")
                MapScreen(
                    nav = navController,
                    vm = newTripViewModel,
                    searchType = ObservingDestination.valueOf(type!!)
                )
            }
            composable(route = Route.NewTripTab.TripsListScreen.route) {
                TripsList()
            }
            composable(route = Route.NewTripTab.NewTripForm.route) {
                val newTripViewModel = it.sharedVM<NewTripViewModel>(navController = navController)
                NewTripForm(vm = newTripViewModel,nav = navController)
            }
            composable(route = "${Route.NewTripTab.TextInputScreen.route}/{searchtype}",
                arguments = listOf(navArgument("searchtype"){
                    type = NavType.StringType
                })) {
                val newTripViewModel = it.sharedVM<NewTripViewModel>(navController = navController)
                val type = it.arguments?.getString("searchtype")
                TextInputScreen(vm = newTripViewModel,nav = navController, destination = ObservingDestination.valueOf(type!!))
            }
            composable(route = Route.NewTripTab.CheckScreen.route) {
                val newTripViewModel = it.sharedVM<NewTripViewModel>(navController = navController)
                CheckScreen(vm = newTripViewModel,nav = navController)
            }
        }

        navigation(
            route = Route.DialogsTab.route,
            startDestination = Route.DialogsTab.DialogsScreen.route
        ) {
            composable(route = Route.DialogsTab.DialogsScreen.route) {
                DialogsScreen()
            }
        }

        navigation(
            route = Route.ArchivedTripsTab.route,
            startDestination = Route.ArchivedTripsTab.ArchivedTripsScreen.route
        ) {
            composable(route = Route.ArchivedTripsTab.ArchivedTripsScreen.route) {
                ArchivedTripsScreen()
            }
        }
        navigation(
            route = Route.AccountTab.route,
            startDestination = Route.AccountTab.AccountScreen.route
        ) {
            composable(route = Route.AccountTab.AccountScreen.route) {
                AccountScreen()
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedVM(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return viewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return viewModel(parentEntry)
}


@Preview
@Composable
fun NavGraphPreview() {

}
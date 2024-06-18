package com.joblessrn.hitchhiike.presentation.app_screen

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joblessrn.hitchhiike.R
import com.joblessrn.hitchhiike.presentation.app_screen.components.BottomNavigation
import com.joblessrn.hitchhiike.presentation.app_screen.components.BottomNavigationItem
import com.joblessrn.hitchhiike.presentation.navgraph.NavGraph
import com.joblessrn.hitchhiike.presentation.navgraph.Route

@Composable
fun AppScreen() {
    val navController:NavHostController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    val currentScreenState = navController.currentDestination
    val isBottomBarVisible = remember(key1 = currentScreenState) {
        currentScreenState?.route == Route.NewTripTab.NewTripScreen.route ||
        currentScreenState?.route == Route.DialogsTab.DialogsScreen.route||
        currentScreenState?.route == Route.ArchivedTripsTab.ArchivedTripsScreen.route ||
        currentScreenState?.route == Route.AccountTab.AccountScreen.route
    }

    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.NewTripTab.NewTripScreen.route -> 0
        Route.ArchivedTripsTab.ArchivedTripsScreen.route -> 1
        Route.DialogsTab.DialogsScreen.route -> 2
        Route.AccountTab.AccountScreen.route -> 3
        else -> 0
    }
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.trip_icon, text = "New trip"),
            BottomNavigationItem(icon = R.drawable.archived_trips_icon, text = "Archive"),
            BottomNavigationItem(icon = R.drawable.dialogs_icon, text = "Dialogs"),
            BottomNavigationItem(icon = R.drawable.account_icon, text = "Account"),
        )
    }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(
                    items = bottomNavigationItems,
                    selectedItem = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.NewTripTab.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.ArchivedTripsTab.route
                            )

                            2 -> navigateToTab(
                                navController = navController,
                                route = Route.DialogsTab.route
                            )
                            3 -> navigateToTab(
                                navController = navController,
                                route = Route.AccountTab.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavGraph(navController = navController)
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        //restoreState = true
    }
}
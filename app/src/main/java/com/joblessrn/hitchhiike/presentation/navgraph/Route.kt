package com.joblessrn.hitchhiike.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object NewTripTab : Route(route = "findTripTab"){
        data object NewTripScreen : Route(route = "newTripScreen")
        data object FindTripScreen : Route(route = "newTripPassengerScreen")
        data object TripsListScreen : Route(route = "tripsListScreen")
        data object NewTripForm : Route(route = "newTripScreen")
        data object MapScreen : Route(route = "mapScreen")
        data object TextInputScreen :Route(route = "textInputScreen")
        data object CheckScreen : Route(route = "checkScreen")
    }

    data object AccountTab : Route(route = "accountTab"){
        data object AccountScreen : Route(route = "accountScreen")
    }

    data object DialogsTab : Route(route = "dialogsTab"){
        data object DialogsScreen : Route(route = "dialogsScreen")
    }

    data object ArchivedTripsTab : Route(route = "archivedTripsTab"){
        data object ArchivedTripsScreen : Route(route = "archivedTripsScreen")
    }
}
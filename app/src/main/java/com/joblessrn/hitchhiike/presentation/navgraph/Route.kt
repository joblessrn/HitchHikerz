package com.joblessrn.hitchhiike.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object NewTripTab : Route(route = "findTripTab"){
        data object NewTripScreen : Route(route = "newTripScreen")
        data object FindTrip : Route(route = "newTripPassengerScreen")
        data object PostTrip : Route(route = "newTripDriverScreen")
        data object TripsList : Route(route = "tripsListScreen")
        data object NewTripForm : Route(route = "newTripScreen")
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
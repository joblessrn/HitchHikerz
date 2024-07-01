package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.joblessrn.hitchhiike.R
import com.joblessrn.hitchhiike.data.remote.Coordinate
import com.joblessrn.hitchhiike.presentation.app_screen.components.TextFieldWithSuggestions
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingDestination
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.Address
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session
import com.yandex.mapkit.search.ToponymObjectMetadata
import com.yandex.runtime.Error

@Composable
fun MapScreen(
    vm: NewTripViewModel,
    nav: NavController,
    searchType: ObservingDestination
) {

    Log.d("tagg", "searchtype = ${searchType}")
    LaunchedEffect(Unit) {
        vm.observeSuggestions(searchType)
        vm.observeCoordinates()
    }

    DisposableEffect(Unit) {
        onDispose {
            vm.clearSuggestionsStopObserving()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var textForTextField = remember { mutableStateOf(TextFieldValue("")) }
        var tripInfo by remember {
            mutableStateOf(TripToPost())
        }
        val context = LocalContext.current
        var mapView by remember { mutableStateOf(MapView(context)) }
        val searchManager =
            remember { SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED) }
        val positionState = vm.coordinateState
        var showDoneButton by remember { mutableStateOf(false) }

        val inputListener = remember {
            object : InputListener {
                override fun onMapTap(map: Map, point: Point) {
                    Log.d("TAGG", "lat = ${point.latitude}, long = ${point.longitude}")

                    val searchManager =
                        SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE)
                    searchManager.createSuggestSession()
                    val searchOptions = SearchOptions().apply {
                        searchTypes = SearchType.GEO.value
                        geometry = true
                    }

                    searchManager.submit(
                        point,
                        0,
                        searchOptions,
                        object : Session.SearchListener {
                            override fun onSearchResponse(response: Response) {
                                val topLevelAddress =
                                    response.collection.children.firstOrNull()?.obj?.metadataContainer?.getItem(
                                        ToponymObjectMetadata::class.java
                                    )?.address

                                val addressComponents = topLevelAddress?.components

                                Log.d("tagg", "${addressComponents}")
                                var country: String? = null
                                var province: String? = null
                                var region: String? = null
                                var area: String? = null
                                var city: String? = null
                                var street: String? = null
                                var house: String? = null
                                var metroStation: String? = null


                                addressComponents?.forEach { component ->
                                    val kind = component.kinds.firstOrNull() ?: return@forEach
                                    when (kind) {
                                        Address.Component.Kind.UNKNOWN -> {}
                                        Address.Component.Kind.COUNTRY -> country = component.name
                                        Address.Component.Kind.REGION -> region = component.name
                                        Address.Component.Kind.PROVINCE -> province = component.name
                                        Address.Component.Kind.AREA -> area = component.name
                                        Address.Component.Kind.LOCALITY -> city = component.name
                                        Address.Component.Kind.DISTRICT -> {}
                                        Address.Component.Kind.STREET -> street = component.name
                                        Address.Component.Kind.HOUSE -> house = component.name
                                        Address.Component.Kind.ENTRANCE -> {}
                                        Address.Component.Kind.LEVEL -> {}
                                        Address.Component.Kind.APARTMENT -> {}
                                        Address.Component.Kind.ROUTE -> {}
                                        Address.Component.Kind.STATION -> {}
                                        Address.Component.Kind.METRO_STATION -> metroStation =
                                            component.name

                                        Address.Component.Kind.RAILWAY_STATION -> {}
                                        Address.Component.Kind.VEGETATION -> {}
                                        Address.Component.Kind.HYDRO -> {}
                                        Address.Component.Kind.AIRPORT -> {}
                                        Address.Component.Kind.OTHER -> {}
                                    }
                                }

                                Log.d(
                                    "tagg",
                                    "adres = ${country}, ${area}, ${region}, ${province}, $city, $street, $house"
                                )

                            }

                            override fun onSearchError(error: Error) {
                                Log.e("TAGG", "Error: $error")
                            }
                        }
                    )
                }

                override fun onMapLongTap(map: Map, point: Point) {}
            }
        }


        val listener = remember{
            object : CameraListener {
                override fun onCameraPositionChanged(
                    p0: Map,
                    p1: CameraPosition,
                    p2: CameraUpdateReason,
                    p3: Boolean
                ) {
                    Log.d("tagg", "campos = ${p1.target.latitude}, ${p1.target.longitude}")
                    if (p3 && p2 == CameraUpdateReason.GESTURES) {
                        //Log.d("tagg", "campos = ${p1.target.latitude}, ${p1.target.longitude}")
                        val searchOptions = SearchOptions().apply {
                            searchTypes = SearchType.GEO.value
                            geometry = true
                        }
                        searchManager.submit(
                            Point(p1.target.latitude, p1.target.longitude),
                            0,
                            searchOptions,
                            object : Session.SearchListener {
                                override fun onSearchResponse(response: Response) {
                                    val topLevelAddress =
                                        response.collection.children.firstOrNull()?.obj?.metadataContainer?.getItem(
                                            ToponymObjectMetadata::class.java
                                        )?.address

                                    val addressComponents = topLevelAddress?.components

                                    var country: String? = null
                                    var province: String? = null
                                    var region: String? = null
                                    var area: String? = null
                                    var city: String? = null
                                    var street: String? = null
                                    var house: String? = null
                                    var metroStation: String? = null
                                    var airport: String? = null
                                    var railwayStation: String? = null

                                    addressComponents?.forEach { component ->
                                        val kind = component.kinds.firstOrNull() ?: return@forEach
                                        when (kind) {
                                            Address.Component.Kind.UNKNOWN -> {}
                                            Address.Component.Kind.COUNTRY -> country = component.name
                                            Address.Component.Kind.REGION -> region = component.name
                                            Address.Component.Kind.PROVINCE -> province = component.name
                                            Address.Component.Kind.AREA -> area = component.name
                                            Address.Component.Kind.LOCALITY -> city = component.name
                                            Address.Component.Kind.DISTRICT -> {}
                                            Address.Component.Kind.STREET -> street = component.name
                                            Address.Component.Kind.HOUSE -> house = component.name
                                            Address.Component.Kind.ENTRANCE -> {}
                                            Address.Component.Kind.LEVEL -> {}
                                            Address.Component.Kind.APARTMENT -> {}
                                            Address.Component.Kind.ROUTE -> {}
                                            Address.Component.Kind.STATION -> {}
                                            Address.Component.Kind.METRO_STATION -> metroStation =
                                                component.name

                                            Address.Component.Kind.RAILWAY_STATION -> railwayStation =
                                                component.name

                                            Address.Component.Kind.VEGETATION -> {}
                                            Address.Component.Kind.HYDRO -> {}
                                            Address.Component.Kind.AIRPORT -> airport = component.name
                                            Address.Component.Kind.OTHER -> {}
                                        }
                                    }

                                    val subjectOfCounty = province ?: region ?: area
                                    val place = metroStation ?: railwayStation ?: street + house

                                    when (searchType) {
                                        ObservingDestination.FROM_LOCALITY -> {
                                            Log.d("tagg", "gorod = $subjectOfCounty, $city")
                                            if (!airport.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    fromCity = airport!!,
                                                    fromGeoTag = Coordinate(p1.target.latitude, p1.target.longitude)
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = airport!!,
                                                    selection = TextRange(airport!!.length)
                                                )
                                                showDoneButton = true
                                            } else if (!country.isNullOrBlank() && !subjectOfCounty.isNullOrBlank() && !city.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    fromCountry = country!!,
                                                    fromCity = city!!,
                                                    fromGeoTag = Coordinate(p1.target.latitude, p1.target.longitude)
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = city!!,
                                                    selection = TextRange(city!!.length)
                                                )
                                                showDoneButton = true
                                            } else {
                                                showDoneButton = false
                                                textForTextField.value = TextFieldValue(
                                                    text = ""
                                                )
                                            }
                                        }

                                        ObservingDestination.TO_LOCALITY -> {
                                            Log.d("tagg", "gorod = $subjectOfCounty, $city")
                                            if (!airport.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    toCity = airport!!,
                                                    toGeoTag = Coordinate(p1.target.latitude, p1.target.longitude)
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = airport!!,
                                                    selection = TextRange(airport!!.length)
                                                )
                                                showDoneButton = true
                                            } else if (!country.isNullOrBlank() && !subjectOfCounty.isNullOrBlank() && !city.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    toCountry = country!!,
                                                    toCity = city!!,
                                                    toGeoTag = Coordinate(p1.target.latitude, p1.target.longitude)
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = city!!,
                                                    selection = TextRange(city!!.length)
                                                )
                                                showDoneButton = true
                                            } else {
                                                showDoneButton = false
                                                textForTextField.value = TextFieldValue(
                                                    text = ""
                                                )
                                            }
                                        }

                                        ObservingDestination.FROM_ADDRESS -> {
                                            Log.d("tagg", "from adress = ${street} $house")
                                            if (!street.isNullOrBlank() && !house.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    fromAddress = "${street}, $house",
                                                    fromGeoTag = Coordinate(
                                                        p1.target.latitude,
                                                        p1.target.longitude
                                                    )
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = "${street}, $house",
                                                    selection = TextRange("${street}, $house".length)
                                                )
                                                showDoneButton = true
                                            } else {
                                                showDoneButton = false
                                                textForTextField.value = TextFieldValue(
                                                    text = ""
                                                )
                                            }
                                        }

                                        ObservingDestination.TO_ADDRESS -> {
                                            Log.d("tagg", "from adress = ${street} $house")
                                            if (!street.isNullOrBlank() && !house.isNullOrBlank()) {
                                                tripInfo = tripInfo.copy(
                                                    toAddress = "${street}, $house",
                                                    fromGeoTag = Coordinate(
                                                        p1.target.latitude,
                                                        p1.target.longitude
                                                    )
                                                )
                                                textForTextField.value = TextFieldValue(
                                                    text = "${street}, $house",
                                                    selection = TextRange("${street}, $house".length)
                                                )
                                                showDoneButton = true
                                            } else {
                                                showDoneButton = false
                                                textForTextField.value = TextFieldValue(
                                                    text = ""
                                                )
                                            }
                                        }
                                    }
                                }

                                override fun onSearchError(error: Error) {
                                    Log.e("TAGG", "Error: $error")
                                }
                            }
                        )
                    }
                }
            }
        }
        MapKitFactory.initialize(context)
        AndroidView(
            factory = {
                MapKitFactory.getInstance().onStart()
                mapView = MapView(context)
                mapView.apply {
                    mapView = this
                    map.move(CameraPosition(positionState.value, 6.0f, 0.0f, 0.0f))
                    //map.addInputListener(inputListener)
                    map.addCameraListener(listener)
                }

            },
            modifier = Modifier.fillMaxSize()
        )

        LaunchedEffect(positionState.value) {
            mapView.map?.move(CameraPosition(positionState.value, 12.0f, 0.0f, 0.0f))
        }

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextFieldWithSuggestions(
                placeholder = "Откуда планируется поездка",
                textFieldModifier = Modifier
                    .padding(10.dp),
                suggestionsModifier = Modifier
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp))
                    .border(
                        2.dp,
                        Color.Black,
                        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp)
                    )
                    .background(
                        Color.White,
                        shape = RoundedCornerShape(bottomStart = 25.dp, bottomEnd = 25.dp),
                    ),
                suggestionsState = vm.suggestState,
                getSuggestions = {
                    vm.updateQuery(it)
                },
                onSuggestionClick = {
                    //vm.updatePlace(it)
                    vm.tripToPost.value = vm.tripToPost.value.copy(fromCity = it)
                },
                textFieldState = textForTextField,
            )
        }
        Box(
            modifier = Modifier.align(Alignment.Center).offset((-20).dp),
            contentAlignment = Alignment.Center
        ) {
            // Your custom marker
            androidx.compose.foundation.Image(
                painter = painterResource(id = R.drawable.map_tag),
                contentDescription = null
            )
        }
        if (showDoneButton) {
            IconButton(
                onClick = {
                    when (searchType) {
                        ObservingDestination.FROM_LOCALITY -> {
                            vm.tripToPost.value = vm.tripToPost.value.copy(
                                fromCountry = tripInfo.fromCountry,
                                fromCity = tripInfo.fromCity
                            )
                        }

                        ObservingDestination.TO_LOCALITY -> {
                            vm.tripToPost.value = vm.tripToPost.value.copy(
                                toCountry = tripInfo.toCountry,
                                toCity = tripInfo.toCity
                            )
                        }

                        ObservingDestination.FROM_ADDRESS -> {
                            vm.tripToPost.value = vm.tripToPost.value.copy(
                                fromAddress = tripInfo.fromAddress,
                                fromGeoTag = tripInfo.fromGeoTag
                            )
                        }

                        ObservingDestination.TO_ADDRESS -> {
                            vm.tripToPost.value = vm.tripToPost.value.copy(
                                toAddress = tripInfo.toAddress,
                                toGeoTag = tripInfo.toGeoTag
                            )
                        }
                    }
                    nav.popBackStack()
                }, modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .padding(bottom = 20.dp)
                    .fillMaxWidth(0.9F)
                    .border(2.dp, Color.Black, RoundedCornerShape(20.dp))
            ) {
                Text(text = "Готово", fontSize = 30.sp)
            }
        }
    }
}
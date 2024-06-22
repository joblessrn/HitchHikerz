package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.TextFieldWithSuggestions
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.VisibleRegion
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType

@Composable
fun PostTripFrom(
    vm: NewTripViewModel,
    onNextClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        vm.getSuggests()
    }

    // Stop observing when the composable leaves the composition
    DisposableEffect(Unit) {
        onDispose {
            vm.stopObservingQuery()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        var city by remember {
            mutableStateOf("")
        }
        val context1 = LocalContext.current
        var mapView by remember { mutableStateOf<MapView>(MapView(context1)) }
        val searchManager =
            remember { SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED) }
        val startingPoint = remember { mutableStateOf(Point(0.0, 0.0)) }
        val region = remember { mutableStateOf<VisibleRegion?>(null) }
        var cityName by remember {
            mutableStateOf("")
        }
        MapKitFactory.initialize(context1)
        AndroidView(
            factory = { contexto ->
                MapKitFactory.getInstance().onStart()
                mapView = MapView(contexto)
                mapView.apply {
                    mapView = this
                    map.move(CameraPosition(Point(54.901171, 52.297230), 4.0f, 0.0f, 0.0f))
                    //map.addInputListener(inputListener)
                }

            },
            modifier = Modifier.fillMaxSize()
        )

        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onNextClick() },
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(5.dp)),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            TextFieldWithSuggestions(
                placeholder = "Введите название города",
                textFieldModifier = Modifier
                    .fillMaxWidth()
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
                }
            )
            NiceButton(text = "Дальше") {
                cityName = city
            }
        }
    }
}
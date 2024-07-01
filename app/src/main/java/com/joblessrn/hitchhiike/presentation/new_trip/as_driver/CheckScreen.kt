package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceTextButton
import com.joblessrn.hitchhiike.presentation.navgraph.Route
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

val testTrip = TripToPost(
    fromCountry = "Россия",
    fromCity = "Кинишма",
    fromAddress = "Пушкина Колотушкина 228",
    fromGeoTag = null,
    toCountry = "Эстония",
    toCity = "Нарва",
    toAddress = "ул Пуджа 27",
    toGeoTag = null,
    date = LocalDate.now(),
    time = "6:00",
    seats = 3,
    takenSeats = 0,
    twoPassengersBack = true,
    autoBrand = "Мицубики приора",
    animalsAllowed = true,
    description = "Какие то буквы текст описание verv rvr vwr v rvw rvw ervwervw  vwervwervwervwer werv werv we rvwe rv wrv",
    driverID = "zzzzzzzzzzz"
)

@Composable
fun CheckScreen(
    vm: NewTripViewModel,
    nav: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "назад")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Поездка", fontSize = 20.sp)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    "Из", fontSize = 18.sp,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 15.dp)
                )
                Column() {
                    Text(
                        text = vm.tripToPost.value.fromCity,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    vm.tripToPost.value.fromAddress?.let { Text(text = it) }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    "В", fontSize = 17.sp,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 15.dp)
                )
                Column() {
                    Text(
                        text = vm.tripToPost.value.toCity,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    vm.tripToPost.value.toAddress?.let { Text(text = it) }
                }
            }
            val date = vm.tripToPost.value.date
                .format(DateTimeFormatter.ofPattern(("d MMMM"), Locale("ru")))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = date.toString(), fontSize = 18.sp)
                Text(text = vm.tripToPost.value.time, fontSize = 17.sp)
            }
            Text(
                text = "${vm.tripToPost.value.seats} мест(а)",
                fontSize = 17.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            if (vm.tripToPost.value.twoPassengersBack) {
                Text(
                    text = "Максимум два пассажира сзади",
                    fontSize = 17.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    "Авто", fontSize = 17.sp,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(end = 15.dp)
                )
                Column() {
                    Text(
                        text = vm.tripToPost.value.autoBrand,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = vm.tripToPost.value.autoBrand,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                }
            }
            Text(text = "Краткое описание",
                fontSize = 19.sp,
                modifier = Modifier.padding(vertical = 10.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .border(
                    width = 2.dp,
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Black
                )) {
                Text(
                    text = vm.tripToPost.value.description,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 10.dp)
                        .align(Alignment.TopCenter)
                )
            }
            NiceTextButton(text = "Все верно, опубликовать") {
                //vm.postNewTripToFB()
                nav.navigate(Route.ArchivedTripsTab.ArchivedTripsScreen.route)
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun CheckScreenPreview() {
    val nav = rememberNavController()
    CheckScreen(NewTripViewModel(), nav)
}
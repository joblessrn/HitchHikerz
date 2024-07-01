package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.joblessrn.hitchhiike.R
import com.joblessrn.hitchhiike.checkEmptyFields
import com.joblessrn.hitchhiike.getCurrentTime
import com.joblessrn.hitchhiike.presentation.app_screen.components.DateButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceIconButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceTextField
import com.joblessrn.hitchhiike.presentation.app_screen.components.TextInput
import com.joblessrn.hitchhiike.presentation.navgraph.Route
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingDestination
import com.joblessrn.hitchhiike.presentation.new_trip.as_passenger.toEpochMilli
import com.joblessrn.hitchhiike.ui.theme.Pink40
import com.joblessrn.hitchhiike.ui.theme.Purple40
import com.joblessrn.hitchhiike.ui.theme.Purple70
import com.joblessrn.hitchhiike.ui.theme.Purple80
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTripForm(
    vm: NewTripViewModel,
    nav: NavController
) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    var seats by remember { mutableIntStateOf(1) }
    var max2back by remember { mutableStateOf(false) }
    var fromAddressFieldEnabled by remember { mutableStateOf(false) }
    var toAddressFieldEnabled by remember { mutableStateOf(false) }
    var animalsAllowed by remember { mutableStateOf(false) }
    val timeFormat = remember { SimpleDateFormat("HH:mm", Locale.getDefault()) }
    var timeValue by remember { mutableStateOf(getCurrentTime(timeFormat)) }
    val density = LocalDensity.current

    var car by remember { mutableStateOf("") }
    var fromCityField by remember {
        mutableStateOf(vm.tripToPost.value.fromCity)
    }
    var fromAddressField by remember {
        mutableStateOf(vm.tripToPost.value.fromAddress ?: "")
    }
    var toCityField by remember {
        mutableStateOf(vm.tripToPost.value.toCity)
    }
    var toAddressField by remember {
        mutableStateOf(vm.tripToPost.value.toAddress ?: "")
    }

    var fromAddressFieldActive by remember {
        mutableStateOf(
            vm.tripToPost.value.fromCity.isNotBlank()
                    && vm.tripToPost.value.fromAddress != null
        )
    }
    var toAddressFieldActive by remember {
        mutableStateOf(
            vm.tripToPost.value.toCity.isNotBlank()
                    && vm.tripToPost.value.toAddress != null
        )
    }
    val yOffset by animateDpAsState(
        targetValue = with(density) {
            offsetY.toDp()
        },
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )
    var description by remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()
    LaunchedEffect(Unit) {
        fromAddressFieldEnabled = vm.tripToPost.value.fromCity.isNotBlank()
        toAddressFieldEnabled = vm.tripToPost.value.toCity.isNotBlank()
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .scrollable(state = scrollState, orientation = Orientation.Vertical)
        .pointerInput(Unit) {
            detectTapGestures {
                focusManager.clearFocus()
            }
        }) {
        val datePickerState = rememberDatePickerState(
            yearRange = (LocalDate.now().year..LocalDate.now().year + 1)
        )
        var showDatePicker by remember { mutableStateOf(false) }
        var date by remember { mutableStateOf(LocalDate.now()) }
        val context = LocalContext.current
        Box(
            modifier = Modifier
                .offset(y = -yOffset)
                .fillMaxWidth(0.9f)
                .align(Alignment.Center)
                .border(2.dp, Color.Black, RoundedCornerShape(25.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 70.dp)
                    .align(Alignment.Center)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextInput(
                        value = fromCityField,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(end = 10.dp),
                        enabled = true,
                        placeHolder = "Населенный пункт") {
                        nav.navigate("${Route.NewTripTab.TextInputScreen.route}/${ObservingDestination.FROM_LOCALITY.name}")
                    }
                    NiceIconButton(
                        onClick = { nav.navigate("${Route.NewTripTab.MapScreen.route}/${ObservingDestination.FROM_LOCALITY.name}") },
                        icon = painterResource(id = R.drawable.map_icon)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextInput(
                        value = fromAddressField,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(end = 10.dp),
                        enabled = fromAddressFieldEnabled,
                        placeHolder = "Адрес"
                    ) {
                        nav.navigate("${Route.NewTripTab.TextInputScreen.route}/${ObservingDestination.FROM_ADDRESS.name}")
                    }
                    NiceIconButton(
                        onClick = { nav.navigate("${Route.NewTripTab.MapScreen.route}/${ObservingDestination.FROM_ADDRESS.name}") },
                        icon = painterResource(id = R.drawable.map_icon),
                        iconInactive = painterResource(id = R.drawable.map_icon_inactive),
                        enabled = fromAddressFieldActive
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_down_icon),
                    contentDescription = "to",
                    modifier = Modifier.padding(10.dp)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextInput(
                        value = toCityField,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(end = 10.dp),
                        enabled = true,
                        placeHolder = "Населенный пункт"
                    ) {
                        nav.navigate("${Route.NewTripTab.TextInputScreen.route}/${ObservingDestination.TO_LOCALITY.name}")
                    }
                    NiceIconButton(
                        onClick = { nav.navigate("${Route.NewTripTab.MapScreen.route}/${ObservingDestination.TO_LOCALITY.name}") },
                        icon = painterResource(id = R.drawable.map_icon)
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    TextInput(
                        value = toAddressField,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(end = 10.dp),
                        enabled = toAddressFieldEnabled,
                        placeHolder = "Адрес"
                    ) {
                        nav.navigate("${Route.NewTripTab.TextInputScreen.route}/${ObservingDestination.TO_ADDRESS.name}")
                    }
                    NiceIconButton(
                        onClick = { nav.navigate("${Route.NewTripTab.MapScreen.route}/${ObservingDestination.TO_ADDRESS.name}") },
                        icon = painterResource(id = R.drawable.map_icon),
                        iconInactive = painterResource(id = R.drawable.map_icon_inactive),
                        enabled = toAddressFieldActive
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.seat_icon),
                        contentDescription = "seat",
                        modifier = Modifier.size(25.dp)
                    )

                    Text(
                        text = seats.toString(), fontSize = 20.sp,
                        modifier = Modifier.weight(1F)
                    )
                    IconButton(onClick = {
                        if (seats in 1..7) {
                            seats++
                            vm.tripToPost.value.seats = seats
                        }

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.plus_icon),
                            contentDescription = "plus",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    IconButton(onClick = {
                        if (seats in 2..8)
                            seats--
                        vm.tripToPost.value.seats = seats
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.minus_icon),
                            contentDescription = "minus",
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DateButton(
                        onClick = { showDatePicker = true },
                        date = date
                    )
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .border(BorderStroke(2.dp, Color.Black), RoundedCornerShape(10.dp))
                    ) {
                        BasicTextField(
                            value = timeValue,
                            onValueChange = {
                                timeValue = it
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .onFocusChanged {
                                    vm.tripToPost.value.time = timeValue
                                },
                            textStyle = TextStyle(
                                fontSize = 15.sp
                            )
                        )
                    }

                    IconButton(
                        onClick = {
                            animalsAllowed = !animalsAllowed
                            vm.tripToPost.value.animalsAllowed = animalsAllowed
                        },
                        modifier = Modifier
                    ) {

                        Image(
                            painter = painterResource(
                                id = if(animalsAllowed) R.drawable.paw_icon
                                else R.drawable.paw_icon_inactive),
                            contentDescription = if (animalsAllowed) "animals allowed" else "animals not allowed"
                        )
                    }
                }
                NiceTextField(value = car, onValueChange = {
                     car = it
                }, placeholder = "Машина", modifier = Modifier.fillMaxWidth(0.9f)
                    .padding(vertical = 10.dp).onFocusChanged {
                        if(!it.isFocused){
                            vm.tripToPost.value.autoBrand = car
                        }
                    })
                Row(
                    modifier = Modifier.fillMaxWidth(0.9f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Максимум 2 на заднем сиденье")
                    IconButton(
                        onClick = {
                            max2back = !max2back
                            vm.tripToPost.value.twoPassengersBack = max2back
                        },
                        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                    ) {
                        if (max2back) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = "max 2 in the back"
                            )
                        }
                    }
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    val selectedDate = Instant.ofEpochMilli(
                                        datePickerState.selectedDateMillis ?: LocalDate.now()
                                            .toEpochMilli()
                                    )
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                    vm.tripToPost.value.date = selectedDate
                                    if (selectedDate < LocalDate.now()) {
                                        Toast.makeText(
                                            context,
                                            "Нужно выбрать дату не раньше сегодняшнего дня",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        date = selectedDate
                                        showDatePicker = false
                                    }
                                }
                            ) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    showDatePicker = false
                                }
                            ) { Text("Cancel") }
                        }
                    )
                    {
                        DatePicker(
                            state = datePickerState,
                            colors = DatePickerDefaults.colors(
                                todayContentColor = Pink40,
                                todayDateBorderColor = Purple40,
                                selectedDayContentColor = Purple80,
                                dayContentColor = Purple70,
                                selectedDayContainerColor = Purple70
                            )
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                            vm.tripToPost.value.description = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((6 * 17 * 1.5).dp),
                        textStyle = TextStyle(fontSize = 17.sp),
                        shape = RoundedCornerShape(20.dp),
                        placeholder = { Text(text = "Краткое описание", color = Color.LightGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent
                        ),
                        maxLines = 6
                    )
                }
            }
            Button(
                onClick = {
                    Log.d("tagg", "final trip ${vm.tripToPost.value}")
                    if (checkEmptyFields(vm.tripToPost.value)) {
                        Log.d("tagg", "ВСЕ ГУД")
                        nav.navigate(Route.NewTripTab.CheckScreen.route)
                    } else Log.d("tagg", "ВСЕ НЕ ГУД")
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                    .padding(top = 20.dp)
                    .background(Color.Black)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                )
            ) {
                Text("Готово", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun NewTripFormPreview() {
    val context = LocalContext.current
    NewTripForm(NewTripViewModel(), NavController(context))
}
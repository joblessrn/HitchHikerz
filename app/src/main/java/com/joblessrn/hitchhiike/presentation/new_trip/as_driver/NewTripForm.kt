package com.joblessrn.hitchhiike.presentation.new_trip.as_driver

import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joblessrn.hitchhiike.R
import com.joblessrn.hitchhiike.presentation.app_screen.components.DateButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceIconButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.TextFieldWithSuggestions
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingObject
import com.joblessrn.hitchhiike.presentation.new_trip.as_passenger.toEpochMilli
import com.joblessrn.hitchhiike.ui.theme.Pink40
import com.joblessrn.hitchhiike.ui.theme.Purple40
import com.joblessrn.hitchhiike.ui.theme.Purple70
import com.joblessrn.hitchhiike.ui.theme.Purple80
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTripForm(
    vm: NewTripViewModel,

) {
    var offsetY by remember { mutableFloatStateOf(0f) }
    val density = LocalDensity.current
    val yOffset by animateDpAsState(
        targetValue = with(density){
            offsetY.toDp()
        },
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier
        .offset(y = -yOffset)
        .fillMaxSize()
        .pointerInput(Unit){
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
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .border(2.dp, Color.Black, RoundedCornerShape(25.dp))
                .padding(16.dp)

        ) {
            Text("Из")
            TextFieldWithSuggestions(
                placeholder = "Город",
                textFieldModifier = Modifier.padding(10.dp),
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
                onSuggestionClick = {
                    vm.tripToPost = vm.tripToPost.copy(fromCity = it)
                },
                getSuggestions = {
                    vm.updateQuery(it)
                },
                suggestionsState = vm.suggestState,
                onFocusChange = {
                    if (it.isFocused) {
                        vm.observeSuggestions(ObservingObject.LOCALITY)
                    } else {
                        vm.clearSuggestionsStopObserving()
                    }
                },
                initialValue = vm.tripToPost.fromCity
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextFieldWithSuggestions(
                    placeholder = "Адрес",
                    textFieldModifier = Modifier.padding(10.dp),
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
                    onSuggestionClick = {
                        vm.tripToPost = vm.tripToPost.copy(fromAddress = it)
                    },
                    getSuggestions = {
                        vm.updateQuery("${vm.tripToPost.fromCity} $it")
                    },
                    suggestionsState = vm.suggestState,
                    onFocusChange = {
                        if (it.isFocused) {
                            vm.observeSuggestions(ObservingObject.ADDRESS)
                        } else {
                            vm.clearSuggestionsStopObserving()
                        }
                    },
                    initialValue = vm.tripToPost.fromAddress
                )
                NiceIconButton(
                    onClick = { /*TODO*/ },
                    icon = painterResource(id = R.drawable.map_icon)
                )
            }

            Text("В")
            TextFieldWithSuggestions(
                placeholder = "Город",
                textFieldModifier = Modifier.padding(10.dp),
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
                onSuggestionClick = {
                    vm.tripToPost = vm.tripToPost.copy(toCity = it)
                },
                getSuggestions = {
                    vm.updateQuery(it)
                },
                suggestionsState = vm.suggestState,
                onFocusChange = {
                    if (it.isFocused) {
                        vm.observeSuggestions(ObservingObject.LOCALITY)
                        offsetY = 100F
                    } else {
                        vm.clearSuggestionsStopObserving()
                        offsetY = 0F
                    }
                },
                initialValue = vm.tripToPost.toCity
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                TextFieldWithSuggestions(
                    placeholder = "Адрес",
                    textFieldModifier = Modifier.padding(10.dp),
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
                    onSuggestionClick = {
                        vm.tripToPost = vm.tripToPost.copy(toAddress = it)
                    },
                    getSuggestions = {
                        vm.updateQuery("${vm.tripToPost.toCity} $it")
                    },
                    suggestionsState = vm.suggestState,
                    onFocusChange = {
                        if (it.isFocused) {
                            vm.observeSuggestions(ObservingObject.ADDRESS)
                            offsetY = 150F
                        } else {
                            vm.clearSuggestionsStopObserving()
                            offsetY = 0F
                        }
                    },
                    initialValue = vm.tripToPost.toAddress
                )
                NiceIconButton(
                    onClick = { /*TODO*/ },
                    icon = painterResource(id = R.drawable.map_icon)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                DateButton(
                    onClick = { showDatePicker = true },
                    date = date
                )
                NiceIconButton(
                    onClick = { /*TODO*/ },
                    icon = painterResource(id = R.drawable.paw_icon_inactive)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(0.9f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Максимум 2 на заднем сиденье")
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "max 2 in the back"
                    )
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
        }
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun NewTripFormPreview() {
    NewTripForm(NewTripViewModel())
}
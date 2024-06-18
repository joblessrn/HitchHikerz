package com.joblessrn.hitchhiike.presentation.new_trip.as_passenger

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joblessrn.hitchhiike.R
import com.joblessrn.hitchhiike.presentation.app_screen.components.DateButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceButton
import com.joblessrn.hitchhiike.presentation.app_screen.components.NiceTextField
import com.joblessrn.hitchhiike.ui.theme.Pink40
import com.joblessrn.hitchhiike.ui.theme.Purple40
import com.joblessrn.hitchhiike.ui.theme.Purple70
import com.joblessrn.hitchhiike.ui.theme.Purple80
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindNewTripPassenger() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .border(2.dp, Color.LightGray, RoundedCornerShape(25.dp))
            .fillMaxHeight(0.5f)
            .fillMaxWidth(0.8f)
            .align(Alignment.Center)
            .padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally) {
            val context = LocalContext.current
            val datePickerState = rememberDatePickerState(
                yearRange = (LocalDate.now().year..LocalDate.now().year + 1)
            )
            var showDatePicker by remember { mutableStateOf(false) }
            var from by remember { mutableStateOf("") }
            var to by remember { mutableStateOf("") }
            var seats by remember { mutableIntStateOf(1) }
            var date by remember { mutableStateOf(LocalDate.now()) }

            NiceTextField(value = from,
                onValueChange = { from = it },
                placeholder = "Откуда")
            NiceTextField(value = to,
                onValueChange = { to = it },
                placeholder = "Куда")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.seat_icon),
                    contentDescription = "seat",
                    modifier = Modifier.size(25.dp)
                )

                Text(text = seats.toString(), fontSize = 20.sp,
                    modifier = Modifier.weight(1F))
                IconButton(onClick = {
                    if (seats in 1..7) seats++
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.plus_icon),
                        contentDescription = "plus",
                        modifier = Modifier.size(25.dp)
                    )
                }
                IconButton(onClick = { if (seats in 2..8) seats-- }) {
                    Icon(
                        painter = painterResource(id = R.drawable.minus_icon),
                        contentDescription = "minus",
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround) {
                DateButton(onClick = { showDatePicker = true},
                           date = date)

                NiceButton(onClick = { /*TODO*/ },text = "Найти")
            }

            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { /*TODO*/ },
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
                                    Log.d("Tagger", "${date}")
                                }else{
                                    date = selectedDate
                                    Log.d("Tagger", "${date}")
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

fun LocalDate.toEpochMilli(): Long {
    return this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun findTripPreview(){
    FindNewTripPassenger()
}

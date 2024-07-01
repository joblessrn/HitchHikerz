package com.joblessrn.hitchhiike.presentation.new_trip.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.joblessrn.hitchhiike.presentation.new_trip.NewTripViewModel
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingDestination

@Composable
fun TextInputScreen(
    vm: NewTripViewModel,
    nav: NavController,
    destination: ObservingDestination
) {
    var text by remember {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        vm.observeSuggestions(destination)
        focusRequester.requestFocus()
    }
    DisposableEffect(Unit) {
        onDispose {
            vm.clearSuggestionsStopObserving()
        }
    }
    Box() {
        IconButton(
            onClick = { nav.popBackStack() },
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "назад")
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)) {
                when (destination) {
                    ObservingDestination.FROM_LOCALITY -> {
                        Text(
                            text = "Из какого населенного пункта выезжаете",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    ObservingDestination.TO_LOCALITY -> {
                        Text(
                            text = "В какой населенный пункт едете",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    ObservingDestination.FROM_ADDRESS -> {
                        Text(
                            text = "Уточните адрес",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    ObservingDestination.TO_ADDRESS -> {
                        Text(
                            text = "Уточните адрес",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    vm.updateQuery(it)
                },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .focusRequester(focusRequester),
                //shape = RectangleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    disabledTextColor = Color.Gray,
                    disabledBorderColor = Color.LightGray,
                    disabledLabelColor = Color.LightGray,
                    disabledPlaceholderColor = Color.Gray
                )
            )
            LazyColumn() {
                items(vm.suggestState.value.hints.size) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            vm.onSuggestionSelected(
                                vm.suggestState.value.hints[it],
                                destination
                            ) {
                                nav.popBackStack()
                            }
                        }) {
                        Text(
                            text = vm.suggestState.value.hints[it].place,
                            fontSize = 19.sp,
                            modifier = Modifier
                                .padding(5.dp)
                        )
                        val region = if(vm.suggestState.value.hints[it].region.isNotBlank()) {
                            "${vm.suggestState.value.hints[it].country}, ${vm.suggestState.value.hints[it].region}"
                        } else {
                            vm.suggestState.value.hints[it].country
                        }

                        Text(text = region,
                            fontSize = 17.sp,
                            color = Color.LightGray,
                            modifier = Modifier
                                .padding(5.dp))
                        Divider(
                            modifier = Modifier.fillMaxWidth(0.9f),
                            color = Color.LightGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun TextInputScreenPreview() {
    val nav = rememberNavController()
    TextInputScreen(NewTripViewModel(), nav = nav, ObservingDestination.TO_ADDRESS)
}
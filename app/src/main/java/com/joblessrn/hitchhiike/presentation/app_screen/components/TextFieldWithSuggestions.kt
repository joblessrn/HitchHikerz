package com.joblessrn.hitchhiike.presentation.app_screen.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Popup
import com.joblessrn.hitchhiike.data.remote.models.Suggest
import com.joblessrn.hitchhiike.data.remote.models.Suggests

@Composable
fun TextFieldWithSuggestions(
    placeholder: String,
    textFieldModifier: Modifier,
    textFieldState:MutableState<TextFieldValue>,
    suggestionsModifier: Modifier,
    onSuggestionClick: (String) -> Unit,
    getSuggestions: (String) -> Unit,
    suggestionsState: State<Suggests>,
    onFocusChange: (FocusState) -> Unit = {},
    enabled:Boolean = true
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(suggestionsState.value.hints) {
        //Log.d("suggestss","suggestionsState = ${suggestionsState.value.hints}")
        expanded = suggestionsState.value.hints.isNotEmpty() && isFocused
    }
    Column {
        OutlinedTextField(
            shape = if (expanded && isFocused) RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            else RoundedCornerShape(20.dp),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = { Text(text = placeholder, color = Color.LightGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                disabledTextColor = Color.Gray,
                disabledBorderColor = Color.LightGray,
                disabledLabelColor = Color.LightGray,
                disabledPlaceholderColor = Color.Gray
            ),
            value = textFieldState.value,
            onValueChange = { query:TextFieldValue ->
                textFieldState.value = query
                //if (query.text.isNotBlank()) {
                    getSuggestions(query.text)
                //}

            },
            modifier = textFieldModifier
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                }
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                    onFocusChange(focusState)
                },
            enabled = enabled,
            maxLines = 1)
        if (expanded && isFocused) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(0, textFieldSize.height.toInt())
            ) {
                LazyColumn(
                    modifier = suggestionsModifier
                        .width(with(LocalDensity.current) {
                            textFieldSize.width.toDp()
                        })
                ) {
                    items(suggestionsState.value.hints.size) {
                        Text(
                            text = suggestionsState.value.hints[it].place,
                            modifier = Modifier
                                .padding(8.dp)
                                .width(textFieldSize.width.toInt().dp)
                                .clickable {
                                    val newText = "${suggestionsState.value.hints[it].place} "
                                    textFieldState.value = TextFieldValue(
                                        text = newText,
                                        selection = TextRange(newText.length)
                                    )
                                    expanded = false
                                    onSuggestionClick(suggestionsState.value.hints[it].place)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun textFieldPreview() {
    val text by remember {
        mutableStateOf(TextFieldValue("234567"))
    }
    TextFieldWithSuggestions(
        placeholder = "1111111",
        textFieldModifier = Modifier.width(180.dp),
        suggestionsModifier = Modifier,
        getSuggestions = {},
        suggestionsState = remember {
            mutableStateOf(Suggests(listOf(Suggest("Kenia", "Monkey Town", "Ooga Booga"))))
        },
        onSuggestionClick = {},
        textFieldState = remember{mutableStateOf(text)}
    )
}
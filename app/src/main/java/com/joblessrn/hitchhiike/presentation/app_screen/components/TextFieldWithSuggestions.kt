package com.joblessrn.hitchhiike.presentation.app_screen.components

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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.joblessrn.hitchhiike.data.remote.models.Suggests

@Composable
fun TextFieldWithSuggestions(
    placeholder: String,
    textFieldModifier: Modifier,
    suggestionsModifier: Modifier,
    onSuggestionClick:(String)->Unit,
    getSuggestions: (String) -> Unit,
    suggestionsState: State<Suggests>
) {
    var textFieldSize by remember { mutableStateOf(Size.Zero) }
    var prompt by remember { mutableStateOf(TextFieldValue("")) }
    var expanded by remember { mutableStateOf(false) }

    Column {
        OutlinedTextField(
            shape = if (expanded) RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            else RoundedCornerShape(20.dp),
            textStyle = TextStyle(fontSize = 18.sp),
            placeholder = { Text(text = placeholder, color = Color.LightGray) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White
            ),
            value = prompt,
            onValueChange = { query ->
                prompt = query
                getSuggestions(query.text)
                expanded = suggestionsState.value.hints.isNotEmpty()
            },
            modifier = textFieldModifier
                .onGloballyPositioned {
                    textFieldSize = it.size.toSize()
                })
        if (expanded) {
            Popup(
                alignment = Alignment.TopStart,
                offset = IntOffset(0, textFieldSize.height.toInt())
            ) {
                LazyColumn(
                    modifier = suggestionsModifier
                        .width(with(LocalDensity.current){
                            textFieldSize.width.toDp()
                        })
                ) {
                    items(suggestionsState.value.hints.size) {
                        Text(
                            text = suggestionsState.value.hints[it],
                            modifier = Modifier
                                .padding(8.dp)
                                .width(textFieldSize.width.toInt().dp)
                                .clickable {
                                    val newText = "${suggestionsState.value.hints[it]} "
                                    prompt = TextFieldValue(
                                        text = newText,
                                        selection = TextRange(newText.length)
                                    )
                                    expanded = false
                                    onSuggestionClick(suggestionsState.value.hints[it])
                                }
                        )
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun textFieldPreview(){
    TextFieldWithSuggestions(
        placeholder = "",
        textFieldModifier = Modifier,
        suggestionsModifier = Modifier ,
        getSuggestions ={} ,
        suggestionsState = remember {
            mutableStateOf(Suggests(listOf("1324","123432","4567")))
        },
        onSuggestionClick = {}
    )
}
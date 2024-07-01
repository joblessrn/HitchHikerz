package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joblessrn.hitchhiike.R

@Composable
fun BigTextField(
    textFieldState: MutableState<TextFieldValue>,
    onValueChange: (String) -> Unit,
    placeholder: String,
    maxLines:Int = 1
) {
    OutlinedTextField(
        value = textFieldState.value,
        onValueChange = {
            onValueChange(it.text)
        },
        modifier = Modifier
            .height((maxLines*17*1.5).dp),
        textStyle = TextStyle(fontSize = 17.sp),
        shape = RoundedCornerShape(20.dp),
        placeholder = { Text(text = placeholder, color = Color.LightGray) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        maxLines = maxLines
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BigTextFieldPreview() {
    var text = remember{ mutableStateOf(TextFieldValue("rveetbet")) }
    BigTextField(
        textFieldState = text,
        onValueChange = {},
        placeholder = "тут надо город",
        6
    )
}
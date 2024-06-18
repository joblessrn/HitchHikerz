package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NiceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        modifier = Modifier,
        textStyle = TextStyle(fontSize = 18.sp),
        shape = RoundedCornerShape(20.dp),
        placeholder = { Text(text = placeholder, color = Color.LightGray) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        )
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Preview() {
    NiceTextField("", {},"тут надо город")
}
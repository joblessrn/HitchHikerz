package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joblessrn.hitchhiike.R

@Composable
fun NiceTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier,
    trailingIcon: @Composable() (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value, onValueChange = onValueChange,
        modifier = modifier,
        textStyle = TextStyle(fontSize = 18.sp),
        shape = RoundedCornerShape(20.dp),
        placeholder = { Text(text = placeholder, color = Color.LightGray) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent
        ),
        trailingIcon = {
            if (trailingIcon != null) {
                trailingIcon()
            }
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun Preview() {
    NiceTextField("", {},"тут надо город",Modifier,{
        IconButton(onClick = {  }) {
            Icon(painter = painterResource(id = R.drawable.map_icon),
                contentDescription = "map")
        }
    })
}
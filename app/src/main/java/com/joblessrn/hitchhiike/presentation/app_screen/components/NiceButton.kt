package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NiceTextButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick:()->Unit
) {
    Button(onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContainerColor =Color.Cyan,
            disabledContentColor= Color.Red
        ),
        border = BorderStroke(2.dp, Color.LightGray)
    )
    {
        Text(text)
    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun NiceButtonPreview() {
    NiceTextButton("text", onClick = {})
}
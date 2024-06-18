package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DateButton(
    date: LocalDate,
    onClick:()->Unit
){
    val formatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
    Button(onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            disabledContainerColor = Color.Cyan,
            disabledContentColor= Color.Red
        ),
        border = BorderStroke(2.dp, Color.LightGray),
        shape = RoundedCornerShape(5.dp)
    )
    {
        Text(date.format(formatter).toString())
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun DateButtonPreview(){
    DateButton(LocalDate.now(),{})
}


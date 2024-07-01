package com.joblessrn.hitchhiike.presentation.app_screen.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joblessrn.hitchhiike.presentation.new_trip.ObservingDestination

@Composable
fun TextInput(
    value: String,
    placeHolder:String,
    modifier: Modifier,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(modifier = modifier) {
        if(value.isNotBlank()){
            Text(text = value,
                fontSize = 19.sp,
                maxLines = 1,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick()
                    }
                    .border(width = 2.dp,
                        shape = RoundedCornerShape(25.dp),
                        color = if(enabled) Color.Black
                    else Color.LightGray)
                    .padding(12.dp)
            )
        }else{
            Text(text = placeHolder,
                fontSize = 19.sp,
                maxLines = 1,
                color = Color.LightGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        enabled = enabled
                    ) {
                        onClick()
                    }
                    .border(width = 2.dp,
                        shape = RoundedCornerShape(25.dp),
                        color = if(enabled) Color.Black
                        else Color.LightGray)
                    .padding(12.dp)
            )
        }

    }
}

@Composable
@Preview(
    showSystemUi = true,
    showBackground = true
)
fun TextInputPreview() {
    val enabled = true
    TextInput(value = "", modifier = Modifier, enabled = enabled,placeHolder = "Город") {}
}
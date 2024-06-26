package com.joblessrn.hitchhiike.presentation.app_screen.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joblessrn.hitchhiike.R

@Composable
fun NiceIconButton(
    onClick:()->Unit,
    icon:Painter
){
    IconButton(onClick = { onClick() },
        modifier = Modifier.border(2.dp, Color.Black, RoundedCornerShape(12.dp))) {
        Icon(painter = icon,contentDescription = "")
    }
}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun NiceIconButtonPreview(){
    NiceIconButton({},
        painterResource(id = R.drawable.map_icon))
}

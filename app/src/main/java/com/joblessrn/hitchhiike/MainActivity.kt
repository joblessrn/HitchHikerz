package com.joblessrn.hitchhiike

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.joblessrn.hitchhiike.presentation.app_screen.AppScreen
import com.joblessrn.hitchhiike.ui.theme.HitchHiikeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HitchHiikeTheme {
                AppScreen()
            }
        }
    }
}
//test if git works

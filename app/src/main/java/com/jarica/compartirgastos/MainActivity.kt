package com.jarica.compartirgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.presentation.ui.theme.CompartirGastosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompartirGastosTheme {
                NavigationWrapper()
            }
        }
    }
}
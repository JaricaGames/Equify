package com.jarica.compartirgastos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.presentation.newGroup.NewGroupViewModel
import com.jarica.compartirgastos.presentation.ui.theme.CompartirGastosTheme

class MainActivity : ComponentActivity() {

    private val newGroupViewModel:NewGroupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompartirGastosTheme {
                NavigationWrapper(newGroupViewModel)
            }
        }
    }
}
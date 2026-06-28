package com.jarica.compartirgastos.core.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import com.jarica.compartirgastos.core.forceUpdate.presentation.ForceUpdateDialog
import com.jarica.compartirgastos.core.forceUpdate.presentation.ForceUpdateViewModel
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.core.presentation.ui.theme.CompartirGastosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // Los ViewModels de cada pantalla se obtienen con hiltViewModel() en NavigationWrapper,
    // con el ciclo de vida del destino. Aquí solo vive el VM de ámbito de la Activity.
    private val forceUpdateViewModel: ForceUpdateViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightNavigationBars =
            true
        setContent {
            CompartirGastosTheme {
                NavigationWrapper()

                val forceUpdate by forceUpdateViewModel.forceUpdate.collectAsState()
                if (forceUpdate) {
                    ForceUpdateDialog()
                }
            }
        }
    }
}
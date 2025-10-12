package com.jarica.compartirgastos.presentation.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.jarica.compartirgastos.core.SPLASHSCREEN_DURATION
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel,
    navigateToGroupsScreen: () -> Unit)
{

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue),
        contentAlignment = Alignment.Center) {
        Text("Hola")
    }

    // Cuando cambie a true, navegamos
    LaunchedEffect(true) {
        delay (SPLASHSCREEN_DURATION)
        navigateToGroupsScreen()
    }
}

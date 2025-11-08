package com.jarica.compartirgastos.presentation.SplashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.SPLASHSCREEN_DURATION
import com.jarica.compartirgastos.presentation.ui.initalPhrase
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundSplashScreenColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashScreen(
    splashScreenViewModel: SplashScreenViewModel,
    navigateToGroupsScreen: () -> Unit)
{

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundSplashScreenColorGradient)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.30f))
        Image(
            painter = painterResource(R.drawable.equifylgo) ,
            contentDescription = "logo",

            modifier = Modifier
                .fillMaxWidth(0.75f)
                .size(100.dp)
        )
        Spacer(modifier = Modifier.weight(0.05f))

        Text(initalPhrase,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.ExtraBold)

        Spacer(modifier = Modifier.weight(1f))

    }

    // Cuando cambie a true, navegamos
    LaunchedEffect(true) {
        delay (SPLASHSCREEN_DURATION)
        navigateToGroupsScreen()
    }
}

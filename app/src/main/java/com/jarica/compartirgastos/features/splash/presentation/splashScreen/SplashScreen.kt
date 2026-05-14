package com.jarica.compartirgastos.features.splash.presentation.splashScreen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.appName
import com.jarica.compartirgastos.core.presentation.ui.buttonPhrase
import com.jarica.compartirgastos.core.presentation.ui.initalPhrase
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.SPLASHSCREEN_DURATION
import kotlinx.coroutines.delay

private val Orange = Color(0xFFE45637)
private val Navy = Color(0xFF35526A)

@Composable
fun SplashScreen(navigateToGroupsScreen: () -> Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height
            val rightY = (h - w) / 2f  // intersection on right edge
            val leftY  = (w + h) / 2f  // intersection on left edge

            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(w, 0f)
                    lineTo(w, rightY)
                    lineTo(0f, leftY)
                    close()
                },
                color = Orange
            )
            drawPath(
                path = Path().apply {
                    moveTo(w, rightY)
                    lineTo(w, h)
                    lineTo(0f, h)
                    lineTo(0f, leftY)
                    close()
                },
                color = Navy
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 24.dp, shape = RoundedCornerShape(30.dp))
                    .size(132.dp)
                    .clip(RoundedCornerShape(30.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_splash_icon),
                    contentDescription = "",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = appName,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = (-0.035).em,
                fontFamily = parkinsans
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = initalPhrase,
                fontSize = 15.sp,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
        }

        // Footer
        Text(
            text = buttonPhrase,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 38.dp),
            fontSize = 10.sp,
            color = Color.White.copy(alpha = 0.4f),
            letterSpacing = 0.18.em,
            fontFamily = parkinsans
        )
    }

    LaunchedEffect(true) {
        delay(SPLASHSCREEN_DURATION)
        navigateToGroupsScreen()
    }
}

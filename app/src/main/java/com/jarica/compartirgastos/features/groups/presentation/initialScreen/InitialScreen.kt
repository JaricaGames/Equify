package com.jarica.compartirgastos.features.groups.presentation.initialScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.appName
import com.jarica.compartirgastos.core.presentation.ui.initalPhrase
import com.jarica.compartirgastos.core.presentation.ui.mainText
import com.jarica.compartirgastos.core.presentation.ui.newGroupText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

@Composable
fun InitialScreen(
    navigateToNewGroup: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.pexelsfotos),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark gradient overlay for legibility
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f   to Color(0x8C141C24),
                            0.4f to Color(0x73141C24),
                            1f   to Color(0xD9141C24)
                        )
                    )
                )
        )

        // Top: icon + wordmark
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(top = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(10.dp))
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_splash_icon),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = appName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = White.copy(alpha = 0.9f),
                fontFamily = parkinsans,
                letterSpacing = (-0.01).em
            )
        }

        // Center: title + tagline
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = mainText,
                fontSize = 44.sp,
                fontWeight = FontWeight.Bold,
                color = DarkOrange,
                fontFamily = parkinsans,
                letterSpacing = (-0.03).em,
                textAlign = TextAlign.Center
            )
            Text(
                text = initalPhrase,
                fontSize = 16.sp,
                color = White.copy(alpha = 0.92f),
                fontFamily = parkinsans,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }

        // Bottom: CTA button
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 22.dp)
                .padding(bottom = 30.dp)
        ) {
            Button(
                onClick = navigateToNewGroup,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkOrange,
                    contentColor = White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    text = newGroupText,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

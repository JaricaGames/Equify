package com.jarica.compartirgastos.presentation.aboutEquify

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.EMAIL_DIRECTION
import com.jarica.compartirgastos.core.EMAIL_SUBJECT
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.presentation.ui.aboutScreenLongText
import com.jarica.compartirgastos.presentation.ui.aboutScreenLongText2
import com.jarica.compartirgastos.presentation.ui.aboutScreenText
import com.jarica.compartirgastos.presentation.ui.buttonAboutScreenText
import com.jarica.compartirgastos.presentation.ui.feedbackText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun AboutEquifyScreen(
    navigateBack: () -> Unit,
    aboutScreenViewModel: AboutEquifyScreenViewModel
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        aboutScreenViewModel.event.collect { event ->
            when (event) {

                AboutEquifyScreenViewModel.UiEvent.SendEmail -> {
                    withContext(Dispatchers.Main) {
                        val email = EMAIL_DIRECTION

                        val intentGmail = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
                            putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
                            setPackage("com.google.android.gm") // Paquete oficial de Gmail
                        }
                        context.startActivity(intentGmail)

                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(color = DarkBlue)
                .padding(bottom = 20.dp)
                .weight(HEADER_WEIGHT),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(start = 16.dp)
                    .size(24.dp) // un poco más pequeño
                    .offset(x = 2.dp), // ajusta visualmente el centrado fino,
                onClick =
                    { navigateBack() }
            ) {
                Icon(

                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint = White
                )
            }
            Text(
                aboutScreenText,
                fontSize = 16.sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp).fillMaxWidth()
                .weight(1f - HEADER_WEIGHT),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color = DarkBlue),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.equifylgo),
                    contentDescription = "logo",

                    modifier = Modifier
                        .fillMaxWidth()
                        .size(75.dp)
                )
            }
            Spacer(Modifier.size(16.dp))
            Text(
                aboutScreenLongText,
                fontSize = 11.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.W200,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(16.dp))

            Button(
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {

                }) {
                Text(
                    buttonAboutScreenText,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
            Spacer(Modifier.size(16.dp))
            Text(
                aboutScreenLongText2,
                fontSize = 11.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.W200,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.size(16.dp))
            Button(
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    aboutScreenViewModel.onFeedbackClicked()
                }) {
                Text(
                    feedbackText,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
            Spacer(Modifier.weight(1f))
        }

    }
}

package com.jarica.compartirgastos.features.groups.presentation.initialScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.mainText
import com.jarica.compartirgastos.core.presentation.ui.newGroupText
import com.jarica.compartirgastos.core.presentation.ui.secondaryText
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun InitialScreen(
    navigateToNewGroup: () -> Unit,
) {

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.pexelsfotos),
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .blur(
                    radiusX = 8.dp,
                    radiusY = 8.dp,
                ),
            colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0.2f) }),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = mainText,
                fontSize = 30.sp,
                color = DarkOrange,
                fontFamily = parkinsans,
                fontWeight = FontWeight.ExtraBold,
            )

            Spacer(Modifier.weight(0.05f))

            Text(
                text = secondaryText,
                fontSize = 12.sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
            )

            Spacer(Modifier.weight(0.05f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = { navigateToNewGroup() }) {
                Text(
                    text = newGroupText,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }

            Spacer(modifier = Modifier.weight(1f))


        }
    }
}


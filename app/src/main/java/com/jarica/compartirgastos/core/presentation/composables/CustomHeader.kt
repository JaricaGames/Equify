package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

@Composable
fun CustomHeader(
    navigate: () -> Unit,
    modifier: Modifier,
    text: String,
    icon: Int? = null){

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(color = DarkBlue)
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        IconButton(
            modifier = Modifier
                .align(alignment = Alignment.BottomStart)
                .padding(start = 16.dp)
                .size(24.dp) // un poco más pequeño
                .offset(x = 2.dp), // ajusta visualmente el centrado fino,
            onClick =
                { navigate() }
        ) {
            icon?.let{
                Icon(
                    painter = painterResource(icon),
                    contentDescription = "",
                    tint = White
                )
            }
        }

        Text(
            text,
            fontSize = 16.sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center
        )
    }

}
package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val EmptyBg    = Color(0xFFE6E4DE)
private val EmptyInk   = Color(0xFF1F2A33)
private val EmptyMuted = Color(0xFF6B7A86)

@Composable
fun EmptyState(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(0.28f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(EmptyBg.copy(alpha = 0.6f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_splash_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .alpha(0.5f),
                    tint = Color.Unspecified
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                title,
                fontSize = 17.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                color = EmptyInk
            )
            Spacer(Modifier.height(6.dp))
            Text(
                subtitle,
                fontSize = 13.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
                color = EmptyMuted,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.weight(0.72f))
    }
}

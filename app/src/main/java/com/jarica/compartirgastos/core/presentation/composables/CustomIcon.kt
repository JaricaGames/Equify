package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val avatarPalette = listOf(
    Color(0xFFE45637) to Color.White,
    Color(0xFF35526A) to Color.White,
    Color(0xFF6F8DAB) to Color.White,
    Color(0xFFF2C14E) to Color(0xFF523C0A),
    Color(0xFF9FD8C7) to Color(0xFF234F44),
)

@Composable
fun CustomIcon(
    name: String,
    size: Dp = 36.dp
) {
    val index = name.hashCode().and(Int.MAX_VALUE) % avatarPalette.size
    val (bg, textColor) = avatarPalette[index]
    val initial = name.firstOrNull()?.uppercaseChar()?.toString() ?: "?"

    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(bg),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = textColor,
            fontSize = (size.value * 0.36f).sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = parkinsans
        )
    }
}

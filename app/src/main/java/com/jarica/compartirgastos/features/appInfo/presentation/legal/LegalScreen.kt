package com.jarica.compartirgastos.features.appInfo.presentation.legal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val MutedColor = Color(0xFF6B7A86)
private val InkColor   = Color(0xFF1F2A33)

/** Una sección de un documento legal: encabezado + cuerpo. */
data class LegalSection(val heading: String, val body: String)

/**
 * Pantalla genérica para mostrar textos legales (privacidad, términos…).
 * Reutilizable: recibe el título, la fecha de actualización y las secciones.
 */
@Composable
fun LegalScreen(
    title: String,
    lastUpdated: String,
    sections: List<LegalSection>,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        LegalHeader(title = title, onBack = onBack)

        Text(
            text       = lastUpdated,
            fontSize   = 12.sp,
            color      = MutedColor,
            fontFamily = parkinsans,
            modifier   = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp, bottom = 4.dp)
        )

        sections.forEach { section ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text          = section.heading,
                    fontSize      = 16.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = InkColor,
                    fontFamily    = parkinsans,
                    letterSpacing = (-0.01).em
                )
                Text(
                    text       = section.body,
                    fontSize   = 14.sp,
                    lineHeight = 21.sp,
                    color      = InkColor.copy(alpha = 0.85f),
                    fontFamily = parkinsans
                )
            }
        }

        Spacer(Modifier.height(28.dp))
        Spacer(Modifier.navigationBarsPadding())
    }
}

@Composable
private fun LegalHeader(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .background(DarkBlue)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp, bottom = 22.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onBack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter            = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint               = White,
                    modifier           = Modifier.size(22.dp)
                )
            }
            Text(
                text          = title,
                fontSize      = 22.sp,
                fontWeight    = FontWeight.Bold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.02).em
            )
        }
    }
}

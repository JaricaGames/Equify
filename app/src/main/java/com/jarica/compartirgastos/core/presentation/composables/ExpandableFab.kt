package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.ui.addCost
import com.jarica.compartirgastos.core.presentation.ui.addPay
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

@Composable
fun Scrim(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f   to Color(0x33141C24),
                        0.4f to Color(0xAA141C24),
                        1f   to Color(0xE0141C24)
                    )
                )
            )
            .clickable(
                indication        = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onDismiss() }
    )
}

@Composable
fun ExpandableFab(
    expanded: Boolean,
    onFabClick: () -> Unit,
    onAddCost: () -> Unit,
    onAddPayment: () -> Unit,
    onAddPerson: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter   = fadeIn() + slideInVertically { it / 2 },
            exit    = fadeOut() + slideOutVertically { it / 2 }
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                FabActionRow(
                    label   = addCost,
                    icon    = R.drawable.moneycash,
                    onClick = onAddCost
                )
                FabActionRow(
                    label   = addPay,
                    icon    = R.drawable.addpay,
                    onClick = onAddPayment
                )
                FabActionRow(
                    label   = "Invitar persona",
                    icon    = R.drawable.people_add,
                    onClick = onAddPerson
                )
                Spacer(Modifier.height(4.dp))
            }
        }

        // Primary FAB — orange when closed, navy when open
        Box(
            modifier         = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(if (expanded) DarkBlue else DarkOrange)
                .clickable { onFabClick() },
            contentAlignment = Alignment.Center
        ) {
            if (expanded) {
                Icon(
                    painter            = painterResource(R.drawable.cancel_close),
                    contentDescription = null,
                    tint               = White,
                    modifier           = Modifier.size(22.dp)
                )
            } else {
                Icon(
                    imageVector        = Icons.Default.Add,
                    contentDescription = null,
                    tint               = White,
                    modifier           = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
private fun FabActionRow(
    label: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Glass label pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(100.dp))
                .background(Color.White.copy(alpha = 0.18f))
                .border(1.dp, Color.White.copy(alpha = 0.25f), RoundedCornerShape(100.dp))
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 8.dp)
        ) {
            Text(
                text          = label,
                fontSize      = 14.sp,
                fontWeight    = FontWeight.SemiBold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.01).em
            )
        }

        // Mini FAB
        Box(
            modifier         = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(DarkOrange)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter            = painterResource(icon),
                contentDescription = label,
                tint               = White,
                modifier           = Modifier.size(22.dp)
            )
        }
    }
}

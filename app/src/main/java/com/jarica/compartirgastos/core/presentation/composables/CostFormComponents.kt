package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

internal val CostInk    = Color(0xFF1F2A33)
internal val CostMuted  = Color(0xFF6B7A86)
internal val CostBorder = Color(0xFFD0D5D9)

@Composable
fun CostFormHeader(
    title: String,
    subtitle: String = "",
    groupName: String = "",
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = 140.dp.toPx()
                val half = side / 2f
                val cx   = size.width - 40.dp.toPx()
                val cy   = size.height - 40.dp.toPx()
                withTransform({ rotate(degrees = 45f, pivot = Offset(cx, cy)) }) {
                    drawRoundRect(
                        color        = DarkOrange,
                        topLeft      = Offset(cx - half, cy - half),
                        size         = Size(side, side),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha        = 0.95f
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp, bottom = 22.dp)
        ) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
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
                        contentDescription = null,
                        tint               = White,
                        modifier           = Modifier.size(22.dp)
                    )
                }
                if (groupName.isNotEmpty()) {
                    Text(
                        text       = groupName,
                        fontSize   = 13.sp,
                        color      = White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Medium,
                        fontFamily = parkinsans
                    )
                }
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text          = title,
                fontSize      = 30.sp,
                fontWeight    = FontWeight.Bold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.02).em
            )

            if (subtitle.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text       = subtitle,
                    fontSize   = 13.sp,
                    color      = White.copy(alpha = 0.75f),
                    fontFamily = parkinsans
                )
            }
        }
    }
}

@Composable
fun FormSection(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text          = label.uppercase(),
            fontSize      = 11.sp,
            fontFamily    = parkinsans,
            fontWeight    = FontWeight.SemiBold,
            color         = CostMuted,
            letterSpacing = 0.08.em
        )
        content()
    }
}

@Composable
fun DescriptionField(value: String, onValueChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        value         = value,
        onValueChange = onValueChange,
        singleLine    = true,
        textStyle     = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            fontSize   = 12.sp,
            color      = CostInk
        ),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isFocused) 1.dp else 0.dp,
                color = if (isFocused) DarkOrange else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 14.dp, vertical = 12.dp),
        decorationBox = { innerTextField ->
            Box {
                if (value.isEmpty()) {
                    Text(
                        stringResource(R.string.description_placeholder),
                        style = TextStyle(
                            fontFamily = parkinsans,
                            fontWeight = FontWeight.Normal,
                            fontSize   = 12.sp,
                            color      = CostMuted.copy(alpha = 0.5f)
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
fun AmountField(value: String, onValueChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    val hasCost = value.isNotEmpty()
    BasicTextField(
        value           = value,
        onValueChange   = onValueChange,
        singleLine      = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        textStyle       = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            fontSize   = 42.sp,
            color      = CostInk
        ),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused }
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = if (isFocused) 1.dp else 0.dp,
                color = if (isFocused) DarkOrange else Color.Transparent,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 14.dp, vertical = 8.dp),
        decorationBox = { innerTextField ->
            Row(verticalAlignment = Alignment.Bottom) {
                Box(modifier = Modifier.weight(1f)) {
                    if (!hasCost) {
                        Text(
                            "0,00",
                            style = TextStyle(
                                fontFamily = parkinsans,
                                fontWeight = FontWeight.Bold,
                                fontSize   = 42.sp,
                                color      = CostMuted.copy(alpha = 0.25f)
                            )
                        )
                    }
                    innerTextField()
                }
                Text(
                    "€",
                    style = TextStyle(
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize   = 22.sp,
                        color      = if (hasCost) CostMuted else CostMuted.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }
        }
    )
}

@Composable
fun PersonChip(name: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor     = if (selected) DarkBlue else Color.Transparent
    val borderColor = if (selected) DarkBlue else CostBorder
    val textColor   = if (selected) White else CostInk
    val avatarColor = if (selected) DarkOrange else DarkBlue

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(start = 6.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(avatarColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text       = name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                fontSize   = 9.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                color      = White
            )
        }
        Text(
            text       = name,
            fontSize   = 12.sp,
            fontFamily = parkinsans,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = textColor
        )
    }
}

@Composable
fun ParticipantsDropdown(
    anchorText: String,
    people: List<PersonModel>,
    selectedIds: Set<String>,
    onToggle: (PersonModel) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = if (expanded) DarkOrange else CostBorder,
                    shape = RoundedCornerShape(10.dp)
                )
                .clickable { expanded = true }
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text       = anchorText,
                fontSize   = 12.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Medium,
                color      = CostInk
            )
            Icon(
                painter            = painterResource(R.drawable.right_arrow),
                contentDescription = null,
                tint               = CostMuted,
                modifier           = Modifier
                    .size(16.dp)
                    .rotate(if (expanded) -90f else 90f)
            )
        }

        DropdownMenu(
            expanded         = expanded,
            onDismissRequest = { expanded = false },
            modifier         = Modifier.background(White)
        ) {
            people.forEach { person ->
                val checked = person.idPerson in selectedIds
                DropdownMenuItem(
                    onClick = { onToggle(person) },
                    text = {
                        Text(
                            text       = person.name,
                            fontSize   = 13.sp,
                            fontFamily = parkinsans,
                            fontWeight = if (checked) FontWeight.SemiBold else FontWeight.Normal,
                            color      = CostInk
                        )
                    },
                    leadingIcon = {
                        Checkbox(
                            checked         = checked,
                            onCheckedChange = { onToggle(person) },
                            colors          = CheckboxDefaults.colors(
                                checkedColor   = DarkOrange,
                                uncheckedColor = CostBorder,
                                checkmarkColor = White
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SplitChip(label: String, selected: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val bgColor     = if (!enabled) Color(0xFFF4F4F4) else if (selected) DarkOrange else Color.Transparent
    val textColor   = if (!enabled) Color(0xFFB0B8BF) else if (selected) White else CostInk
    val borderColor = if (!enabled) Color(0xFFE0E4E7) else if (selected) DarkOrange else CostBorder

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .then(if (enabled) Modifier.clickable { onClick() } else Modifier)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text       = label,
            fontSize   = 12.sp,
            fontFamily = parkinsans,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = textColor
        )
    }
}

package com.jarica.compartirgastos.features.people.presentation.addPeopleScreenFromMain

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.addPeopleFieldLabel
import com.jarica.compartirgastos.core.presentation.ui.addPeopleFromMainSubtitle
import com.jarica.compartirgastos.core.presentation.ui.addPeoplePlaceholder
import com.jarica.compartirgastos.core.presentation.ui.addPeopleText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val LineColor  = Color(0xFFE6E4DE)
private val MutedColor = Color(0xFF6B7A86)
private val InkColor   = Color(0xFF1F2A33)

@Composable
fun AddPeopleScreenFromMain(
    iDGroupName: String,
    addPeopleFromMainViewModel: AddPeopleScreenFromMainViewModel,
    navigateToMainScreen: () -> Unit,
) {
    val inputText: String by addPeopleFromMainViewModel.addNameToGroup.observeAsState("")

    fun addAndGoBack() {
        if (inputText.isNotBlank()) {
            addPeopleFromMainViewModel.insertPeople(
                PersonModel(name = inputText.trim(), idGroupName = iDGroupName)
            )
            navigateToMainScreen()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AddFromMainHeader(
                onBack = {
                    addPeopleFromMainViewModel.onBackPressed()
                    navigateToMainScreen()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text          = addPeopleFieldLabel.uppercase(),
                    fontSize      = 11.sp,
                    letterSpacing = 0.06.em,
                    color         = MutedColor,
                    fontWeight    = FontWeight.SemiBold,
                    fontFamily    = parkinsans
                )
                PersonInputRow(
                    value         = inputText,
                    onValueChange = { addPeopleFromMainViewModel.onValueTextFieldChange(it) },
                    onAdd         = { addAndGoBack() }
                )
                Text(
                    text = buildAnnotatedString {
                        append("Pulsa ")
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold, color = InkColor)) {
                            append("+")
                        }
                        append(" para añadir al grupo.")
                    },
                    fontSize   = 11.sp,
                    color      = MutedColor,
                    fontFamily = parkinsans,
                    modifier   = Modifier.padding(start = 2.dp)
                )
            }
        }

        // Bottom action bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f    to Color.Transparent,
                            0.35f to White,
                            1f    to White
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp, bottom = 22.dp)
        ) {
            Button(
                onClick   = { addAndGoBack() },
                enabled   = inputText.isNotBlank(),
                modifier  = Modifier.fillMaxWidth(),
                shape     = RoundedCornerShape(18.dp),
                colors    = ButtonDefaults.buttonColors(
                    containerColor         = DarkOrange,
                    contentColor           = White,
                    disabledContainerColor = LineColor,
                    disabledContentColor   = MutedColor
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text       = addPeopleText,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp,
                    modifier   = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun AddFromMainHeader(onBack: () -> Unit) {
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
                        contentDescription = "",
                        tint               = White,
                        modifier           = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text          = addPeopleText,
                fontSize      = 30.sp,
                fontWeight    = FontWeight.Bold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text       = addPeopleFromMainSubtitle,
                fontSize   = 13.sp,
                color      = White.copy(alpha = 0.75f),
                fontFamily = parkinsans
            )
        }
    }
}

@Composable
private fun PersonInputRow(
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused         by interactionSource.collectIsFocusedAsState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment     = Alignment.CenterVertically
    ) {
        BasicTextField(
            value             = value,
            onValueChange     = onValueChange,
            singleLine        = true,
            textStyle         = TextStyle(
                fontFamily = parkinsans,
                fontWeight = FontWeight.Medium,
                fontSize   = 15.sp,
                color      = InkColor
            ),
            cursorBrush       = SolidColor(DarkBlue),
            keyboardOptions   = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            interactionSource = interactionSource,
            modifier          = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(14.dp))
                .border(
                    width = if (isFocused) 1.5.dp else 1.dp,
                    color = if (isFocused) DarkBlue else LineColor,
                    shape = RoundedCornerShape(14.dp)
                )
                .background(White),
            decorationBox     = { innerTextField ->
                Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                    if (value.isEmpty()) {
                        Text(
                            text       = addPeoplePlaceholder,
                            fontSize   = 15.sp,
                            color      = MutedColor.copy(alpha = 0.5f),
                            fontFamily = parkinsans
                        )
                    }
                    innerTextField()
                }
            }
        )

        Box(
            modifier = Modifier
                .size(width = 52.dp, height = 52.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(if (value.isNotBlank()) DarkOrange else LineColor)
                .clickable(enabled = value.isNotBlank()) { onAdd() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = Icons.Default.Add,
                contentDescription = null,
                tint               = if (value.isNotBlank()) White else MutedColor,
                modifier           = Modifier.size(24.dp)
            )
        }
    }
}

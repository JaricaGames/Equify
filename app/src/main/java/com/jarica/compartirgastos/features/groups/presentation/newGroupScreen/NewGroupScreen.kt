package com.jarica.compartirgastos.features.groups.presentation.newGroupScreen

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.presentation.ui.groupNameExample
import com.jarica.compartirgastos.core.presentation.ui.labelTextFieldNewGroupScreen
import com.jarica.compartirgastos.core.presentation.ui.newGroupNextButton
import com.jarica.compartirgastos.core.presentation.ui.newGroupStepLabel
import com.jarica.compartirgastos.core.presentation.ui.newGroupSubtitle
import com.jarica.compartirgastos.core.presentation.ui.newGroupText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val LineColor  = Color(0xFFE6E4DE)
private val MutedColor = Color(0xFF6B7A86)
private val InkColor   = Color(0xFF1F2A33)
private const val MAX_GROUP_NAME_LENGTH = 40

@Composable
fun NewGroupScreen(
    newGroupViewModel: NewGroupViewModel,
    navigateToAddPeople: (String, String) -> Unit,
    navigateBack: () -> Unit
) {
    val groupName by newGroupViewModel.groupName.observeAsState("")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            NewGroupHeader(onBack = { navigateBack() })

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = labelTextFieldNewGroupScreen.uppercase(),
                        fontSize = 11.sp,
                        letterSpacing = 0.06.em,
                        color = MutedColor,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = parkinsans
                    )
                    GroupNameInput(
                        value = groupName,
                        onValueChange = {
                            if (it.length <= MAX_GROUP_NAME_LENGTH) {
                                newGroupViewModel.onValueTextFieldChange(it)
                            }
                        }
                    )
                }
            }
        }

        // Bottom action bar with gradient
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
                onClick = {
                    val groupModel = GroupModel(groupName = groupName)
                    navigateToAddPeople(groupModel.idGroupName, groupModel.groupName)
                    newGroupViewModel.onNextSelected()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = groupName.isNotEmpty(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = LineColor,
                    disabledContentColor = MutedColor
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = newGroupNextButton,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun NewGroupHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = 140.dp.toPx()
                val half = side / 2f
                val cx = size.width - 40.dp.toPx()
                val cy = size.height - 40.dp.toPx()
                withTransform({ rotate(degrees = 45f, pivot = Offset(cx, cy)) }) {
                    drawRoundRect(
                        color = DarkOrange,
                        topLeft = Offset(cx - half, cy - half),
                        size = Size(side, side),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha = 0.95f
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
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
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "",
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = newGroupStepLabel,
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = newGroupText,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                fontFamily = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = newGroupSubtitle,
                fontSize = 13.sp,
                color = White.copy(alpha = 0.75f),
                fontFamily = parkinsans
            )
        }
    }
}

@Composable
private fun GroupNameInput(
    value: String,
    onValueChange: (String) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            color = InkColor
        ),
        cursorBrush = SolidColor(DarkBlue),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
        interactionSource = interactionSource,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(
                width = if (isFocused) 1.5.dp else 1.dp,
                color = if (isFocused) DarkBlue else LineColor,
                shape = RoundedCornerShape(14.dp)
            )
            .background(White),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = groupNameExample,
                            fontSize = 15.sp,
                            color = MutedColor.copy(alpha = 0.5f),
                            fontFamily = parkinsans
                        )
                    }
                    innerTextField()
                }
                Text(
                    text = "${value.length}/$MAX_GROUP_NAME_LENGTH",
                    fontSize = 11.sp,
                    color = MutedColor,
                    fontFamily = parkinsans
                )
            }
        }
    )
}

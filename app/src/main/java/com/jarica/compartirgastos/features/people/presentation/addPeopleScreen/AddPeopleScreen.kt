package com.jarica.compartirgastos.features.people.presentation.addPeopleScreen

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.presentation.composables.CustomIcon
import com.jarica.compartirgastos.core.presentation.composables.EmptyState
import com.jarica.compartirgastos.core.presentation.ui.addPeopleEmptySubtitle
import com.jarica.compartirgastos.core.presentation.ui.addPeopleEmptyTitle
import com.jarica.compartirgastos.core.presentation.ui.addPeopleFieldLabel
import com.jarica.compartirgastos.core.presentation.ui.addPeopleHint
import com.jarica.compartirgastos.core.presentation.ui.addPeopleMemberRole
import com.jarica.compartirgastos.core.presentation.ui.addPeopleMembersLabel
import com.jarica.compartirgastos.core.presentation.ui.addPeopleMinButton
import com.jarica.compartirgastos.core.presentation.ui.addPeopleOtherFieldLabel
import com.jarica.compartirgastos.core.presentation.ui.addPeoplePlaceholder
import com.jarica.compartirgastos.core.presentation.ui.addPeopleStepLabel
import com.jarica.compartirgastos.core.presentation.ui.addPeopleTitle
import com.jarica.compartirgastos.core.presentation.ui.createText
import com.jarica.compartirgastos.core.presentation.ui.deleteContentDescription
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.GroupDetailsViewModel

private val LineColor  = Color(0xFFE6E4DE)
private val MutedColor = Color(0xFF6B7A86)
private val InkColor   = Color(0xFF1F2A33)

@Composable
fun AddPeopleScreen(
    idGroupName: String,
    groupName: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
    navigateToNewGroupScreen: () -> Unit,
    navigateToMainScreen: (String) -> Unit,
    mainScreenViewModel: GroupDetailsViewModel,
) {
    val peopleList    = addPeopleViewModel.personList
    val inputText: String by addPeopleViewModel.addNameToGroup.observeAsState("")
    val hasMembers    = peopleList.isNotEmpty()
    val scrollState   = rememberScrollState()

    val subtitle = if (hasMembers)
        stringResource(R.string.add_people_count_subtitle, peopleList.size, groupName)
    else
        stringResource(R.string.add_people_who_subtitle, groupName)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            AddPeopleHeader(
                subtitle = subtitle,
                onBack   = {
                    addPeopleViewModel.onBackPressed()
                    navigateToNewGroupScreen()
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(top = 18.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Input section
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = (if (hasMembers) addPeopleOtherFieldLabel else addPeopleFieldLabel).uppercase(),
                        fontSize = 11.sp,
                        letterSpacing = 0.06.em,
                        color = MutedColor,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = parkinsans
                    )
                    PersonInputRow(
                        value         = inputText,
                        onValueChange = { addPeopleViewModel.onValueTextFieldChange(it) },
                        onAdd         = {
                            if (inputText.isNotBlank()) {
                                addPeopleViewModel.insertNameOnList(inputText)
                            }
                        }
                    )
                    if (!hasMembers) {
                        Text(
                            text = addPeopleHint,
                            fontSize = 11.sp,
                            color = MutedColor,
                            fontFamily = parkinsans,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }

                // Empty state or members list
                if (!hasMembers) {
                    EmptyState(
                        title    = addPeopleEmptyTitle,
                        subtitle = addPeopleEmptySubtitle
                    )
                } else {
                    MembersSection(
                        people   = peopleList,
                        onDelete = { addPeopleViewModel.removePerson(it) }
                    )
                }
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
            if (hasMembers) {
                Button(
                    onClick = {
                        val group = GroupModel(idGroupName = idGroupName, groupName = groupName)
                        mainScreenViewModel.setGroupId(group.idGroupName)
                        addPeopleViewModel.saveGroupData(group, peopleList) {
                            navigateToMainScreen(group.idGroupName)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled  = inputText.isEmpty(),
                    shape    = RoundedCornerShape(18.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = DarkOrange,
                        contentColor           = White,
                        disabledContainerColor = LineColor,
                        disabledContentColor   = MutedColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text       = createText,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.SemiBold,
                        fontSize   = 15.sp,
                        modifier   = Modifier.padding(vertical = 4.dp)
                    )
                }
            } else {
                Button(
                    onClick  = {},
                    enabled  = false,
                    modifier = Modifier.fillMaxWidth(),
                    shape    = RoundedCornerShape(18.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor         = LineColor,
                        contentColor           = MutedColor,
                        disabledContainerColor = LineColor,
                        disabledContentColor   = MutedColor
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        text       = addPeopleMinButton,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Medium,
                        fontSize   = 15.sp,
                        modifier   = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun AddPeopleHeader(subtitle: String, onBack: () -> Unit) {
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
                Text(
                    text       = addPeopleStepLabel,
                    fontSize   = 13.sp,
                    color      = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text          = addPeopleTitle,
                fontSize      = 30.sp,
                fontWeight    = FontWeight.Bold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.02).em
            )

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

@Composable
private fun PersonInputRow(
    value: String,
    onValueChange: (String) -> Unit,
    onAdd: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

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
                Box(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                ) {
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
                contentDescription = addPeopleTitle,
                tint               = if (value.isNotBlank()) White else MutedColor,
                modifier           = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun MembersSection(
    people: List<String>,
    onDelete: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Text(
                text          = addPeopleMembersLabel.uppercase(),
                fontSize      = 11.sp,
                letterSpacing = 0.06.em,
                color         = MutedColor,
                fontWeight    = FontWeight.SemiBold,
                fontFamily    = parkinsans
            )
            Text(
                text       = "${people.size}",
                fontSize   = 11.sp,
                color      = MutedColor,
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .border(1.dp, LineColor, RoundedCornerShape(14.dp))
        ) {
            people.forEachIndexed { index, name ->
                if (index > 0) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(LineColor)
                    )
                }
                MemberRow(
                    name     = name,
                    onDelete = { onDelete(name) }
                )
            }
        }
    }
}

@Composable
private fun MemberRow(name: String, onDelete: () -> Unit) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 14.dp, vertical = 5.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomIcon(name = name, size = 26.dp)

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text       = name,
                fontSize   = 12.sp,
                fontWeight = FontWeight.Medium,
                color      = InkColor,
                fontFamily = parkinsans
            )
            Text(
                text       = addPeopleMemberRole,
                fontSize   = 9.sp,
                color      = MutedColor,
                fontFamily = parkinsans
            )
        }

        Box(
            modifier = Modifier
                .size(20.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(LineColor.copy(alpha = 0.6f))
                .clickable { onDelete() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter            = painterResource(R.drawable.cancel_close),
                contentDescription = deleteContentDescription,
                tint               = MutedColor,
                modifier           = Modifier.size(11.dp)
            )
        }
    }
}

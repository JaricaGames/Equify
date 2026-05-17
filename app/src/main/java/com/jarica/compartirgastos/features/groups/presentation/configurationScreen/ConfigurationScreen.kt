package com.jarica.compartirgastos.features.groups.presentation.configurationScreen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.aboutText
import com.jarica.compartirgastos.core.presentation.ui.addPeopleConfigurationText
import com.jarica.compartirgastos.core.presentation.ui.configAjustesTitle
import com.jarica.compartirgastos.core.presentation.ui.configDangerEyebrow
import com.jarica.compartirgastos.core.presentation.ui.configDeleteSubLabel
import com.jarica.compartirgastos.core.presentation.ui.configGrupoLabel
import com.jarica.compartirgastos.core.presentation.ui.configInfoGroupEyebrow
import com.jarica.compartirgastos.core.presentation.ui.configMiembroRole
import com.jarica.compartirgastos.core.presentation.ui.configMiembrosEyebrow
import com.jarica.compartirgastos.core.presentation.ui.configMiembrosLabel
import com.jarica.compartirgastos.core.presentation.ui.configNombreGrupoLabel
import com.jarica.compartirgastos.core.presentation.ui.deleteGroupText
import com.jarica.compartirgastos.core.presentation.ui.feedbackText
import com.jarica.compartirgastos.core.presentation.ui.informationText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.GroupsAvatarColors
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.EMAIL_DIRECTION
import com.jarica.compartirgastos.core.utils.EMAIL_SUBJECT
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.AlertDialogs.AlertDialogConfirm
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.AlertDialogs.AlertDialogErrorClear
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val NavySoft     = Color(0xFFF0F3F6)
private val OrangeSoft   = Color(0xFFFFE9DD)
private val OrangeDeep   = Color(0xFFB73A1E)
private val DangerRed    = Color(0xFFC0533D)
private val DangerRedSoft = Color(0xFFFBE5E0)
private val LineColor    = Color(0xFFE6E4DE)
private val MutedColor   = Color(0xFF6B7A86)
private val InkColor     = Color(0xFF1F2A33)
private val ActionRowBg  = Color(0xFFFFF5EF)

@Composable
fun ConfigurationScreen(
    configurationScreenViewModel: ConfigurationScreenViewModel,
    iDGroupName: String,
    navigateToGroupScreen: () -> Unit,
    navigateToAddPeopleScreen: () -> Unit,
    navigateToAboutScreen: () -> Unit,
    navigateToCustomizeGroup: (String) -> Unit,
    navigateToGroupsList: () -> Unit,
) {
    LaunchedEffect(iDGroupName) {
        configurationScreenViewModel.setGroup(iDGroupName)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiConfigurationState by produceState<ConfigurationScreenUiState>(
        initialValue = ConfigurationScreenUiState.Loading,
        key1 = lifecycle,
        key2 = configurationScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            configurationScreenViewModel.uiStatePeopleList.collect { value = it }
        }
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        configurationScreenViewModel.event.collect { event ->
            when (event) {
                ConfigurationScreenViewModel.UiEvent.SendEmail -> {
                    withContext(Dispatchers.Main) {
                        val intentGmail = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_DIRECTION))
                            putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
                            setPackage("com.google.android.gm")
                        }
                        context.startActivity(intentGmail)
                    }
                }
            }
        }
    }

    val nameOfGroup: String by configurationScreenViewModel.nameOfGroup.observeAsState("")
    val showDialogError: Boolean by configurationScreenViewModel.showDialogError.observeAsState(false)
    val showDialogConfirm: Boolean by configurationScreenViewModel.showDialogConfirm.observeAsState(false)
    val personSelected: String by configurationScreenViewModel.personSelected.observeAsState("")

    when (uiConfigurationState) {
        is ConfigurationScreenUiState.Error -> {}
        is ConfigurationScreenUiState.Loading -> {}
        is ConfigurationScreenUiState.Success -> {
            MainConfigurationScreen(
                iDGroupName = iDGroupName,
                configurationScreenViewModel = configurationScreenViewModel,
                nameOfGroup = nameOfGroup,
                peopleList = (uiConfigurationState as ConfigurationScreenUiState.Success).listOfPeople,
                navigateToCustomizeGroup = { navigateToCustomizeGroup(iDGroupName) },
                navigateToMainScreen = navigateToGroupScreen,
                navigateToAddPeopleScreen = navigateToAddPeopleScreen,
                navigateToAboutScreen = navigateToAboutScreen,
                navigateToGroupsList = navigateToGroupsList
            )

            if (showDialogError) {
                AlertDialogErrorClear(
                    personSelected,
                    onDismiss = { configurationScreenViewModel.onDismiss() }
                )
            }

            if (showDialogConfirm) {
                AlertDialogConfirm(
                    personSelected,
                    onDismiss = { configurationScreenViewModel.onDismiss() },
                    onConfirm = { configurationScreenViewModel.onConfirmDeletePerson() }
                )
            }
        }
    }
}

@Composable
private fun MainConfigurationScreen(
    iDGroupName: String,
    configurationScreenViewModel: ConfigurationScreenViewModel,
    nameOfGroup: String,
    peopleList: List<PersonModel>,
    navigateToCustomizeGroup: () -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToAddPeopleScreen: () -> Unit,
    navigateToAboutScreen: () -> Unit,
    navigateToGroupsList: () -> Unit,
) {
    configurationScreenViewModel.getGroupNameById(iDGroupName)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        ConfigurationHeader(
            groupName = nameOfGroup,
            memberCount = peopleList.size,
            onBack = navigateToMainScreen
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // ── Información del grupo ──────────────────────────────────
            item {
                SectionEyebrow(configInfoGroupEyebrow)
                SettingsCard {
                    SettingsRow(
                        leadingIcon = R.drawable.list_paper,
                        leadingBg = NavySoft,
                        leadingTint = DarkBlue,
                        label = configNombreGrupoLabel,
                        value = nameOfGroup,
                        showDivider = false,
                        onClick = navigateToCustomizeGroup
                    )
                }
            }

            // ── Miembros ──────────────────────────────────────────────
            item {
                SectionEyebrow("$configMiembrosEyebrow (${peopleList.size})")
                SettingsCard {
                    SettingsRow(
                        leadingIcon = R.drawable.people_add,
                        leadingBg = OrangeSoft,
                        leadingTint = OrangeDeep,
                        label = "Añadir",
                        value = addPeopleConfigurationText,
                        valueColor = OrangeDeep,
                        valueFontWeight = FontWeight.SemiBold,
                        trailTint = DarkOrange,
                        rowBg = ActionRowBg,
                        showDivider = peopleList.isNotEmpty(),
                        onClick = navigateToAddPeopleScreen
                    )
                    peopleList.forEachIndexed { index, person ->
                        MemberRow(
                            person = person,
                            colorIndex = index,
                            showDivider = index < peopleList.size - 1,
                            onDelete = { configurationScreenViewModel.onGroupMemberClicked(person) }
                        )
                    }
                }
            }

            // ── Información ───────────────────────────────────────────
            item {
                SectionEyebrow(informationText)
                SettingsCard {
                    SettingsRow(
                        leadingIcon = R.drawable.information,
                        leadingBg = NavySoft,
                        leadingTint = DarkBlue,
                        label = null,
                        value = aboutText,
                        showDivider = true,
                        onClick = navigateToAboutScreen
                    )
                    SettingsRow(
                        leadingIcon = R.drawable.feedback,
                        leadingBg = NavySoft,
                        leadingTint = DarkBlue,
                        label = null,
                        value = feedbackText.trim(),
                        showDivider = false,
                        onClick = { configurationScreenViewModel.onFeedbackClicked() }
                    )
                }
            }

            // ── Zona de peligro ───────────────────────────────────────
            item {
                SectionEyebrow(configDangerEyebrow, isDanger = true)
                SettingsCard {
                    DangerRow(
                        onClick = {
                            configurationScreenViewModel.deleteGroup(iDGroupName) { navigateToGroupsList() }
                            navigateToMainScreen()
                        }
                    )
                }
            }

            // ── Footer ────────────────────────────────────────────────
            item {
                Text(
                    text = "Equify v1.0",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 22.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    color = MutedColor.copy(alpha = 0.7f),
                    fontFamily = parkinsans
                )
            }
        }
    }
}

@Composable
private fun ConfigurationHeader(
    groupName: String,
    memberCount: Int,
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
                    text = configGrupoLabel,
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = configAjustesTitle,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                fontFamily = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (groupName.isNotEmpty()) {
                Text(
                    text = "$groupName · $memberCount $configMiembrosLabel",
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.75f),
                    fontFamily = parkinsans
                )
            }
        }
    }
}

@Composable
private fun SectionEyebrow(text: String, isDanger: Boolean = false) {
    Text(
        text = text.uppercase(),
        modifier = Modifier.padding(start = 18.dp, end = 18.dp, top = 18.dp, bottom = 8.dp),
        fontSize = 11.sp,
        letterSpacing = 0.1.em,
        color = if (isDanger) DangerRed else MutedColor,
        fontWeight = FontWeight.SemiBold,
        fontFamily = parkinsans
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, LineColor, RoundedCornerShape(16.dp))
            .background(White)
    ) {
        content()
    }
}

@Composable
private fun SettingsRow(
    leadingIcon: Int,
    leadingBg: Color,
    leadingTint: Color,
    label: String?,
    value: String,
    valueColor: Color = InkColor,
    valueFontWeight: FontWeight = FontWeight.Medium,
    trailTint: Color = MutedColor.copy(alpha = 0.5f),
    rowBg: Color = Color.Transparent,
    showDivider: Boolean = true,
    onClick: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(rowBg)
                .clickable { onClick() }
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(leadingBg, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = "",
                    tint = leadingTint,
                    modifier = Modifier.size(16.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                label?.let {
                    Text(
                        text = it,
                        fontSize = 11.sp,
                        color = if (rowBg != Color.Transparent) OrangeDeep else MutedColor,
                        fontFamily = parkinsans,
                        letterSpacing = 0.02.em
                    )
                }
                Text(
                    text = value,
                    fontSize = 14.sp,
                    fontWeight = valueFontWeight,
                    color = valueColor,
                    fontFamily = parkinsans,
                    letterSpacing = (-0.005).em
                )
            }
            Icon(
                painter = painterResource(R.drawable.right_arrow),
                contentDescription = "",
                tint = trailTint,
                modifier = Modifier.size(18.dp)
            )
        }
        if (showDivider) {
            HorizontalDivider(color = LineColor, thickness = 1.dp)
        }
    }
}

@Composable
private fun MemberRow(
    person: PersonModel,
    colorIndex: Int,
    showDivider: Boolean,
    onDelete: () -> Unit,
) {
    val avatarColor = GroupsAvatarColors[colorIndex % GroupsAvatarColors.size]
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 13.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(avatarColor, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = person.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                    color = White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    fontFamily = parkinsans
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = person.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = InkColor,
                    fontFamily = parkinsans
                )
                Text(
                    text = configMiembroRole,
                    fontSize = 11.sp,
                    color = MutedColor,
                    fontFamily = parkinsans
                )
            }
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onDelete() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.cancel_close),
                    contentDescription = "",
                    tint = MutedColor.copy(alpha = 0.5f),
                    modifier = Modifier.size(18.dp)
                )
            }
        }
        if (showDivider) {
            HorizontalDivider(color = LineColor, thickness = 1.dp)
        }
    }
}

@Composable
private fun DangerRow(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(DangerRedSoft, RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.delete),
                contentDescription = "",
                tint = DangerRed,
                modifier = Modifier.size(16.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = deleteGroupText,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = DangerRed,
                fontFamily = parkinsans
            )
            Text(
                text = configDeleteSubLabel,
                fontSize = 11.sp,
                color = DangerRed.copy(alpha = 0.7f),
                fontFamily = parkinsans
            )
        }
        Icon(
            painter = painterResource(R.drawable.right_arrow),
            contentDescription = "",
            tint = DangerRed.copy(alpha = 0.5f),
            modifier = Modifier.size(18.dp)
        )
    }
}

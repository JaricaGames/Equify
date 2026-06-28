package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.composables.AmountField
import com.jarica.compartirgastos.core.presentation.composables.CostFormHeader
import com.jarica.compartirgastos.core.presentation.composables.CostMuted
import com.jarica.compartirgastos.core.presentation.composables.DescriptionField
import com.jarica.compartirgastos.core.presentation.composables.FormSection
import com.jarica.compartirgastos.core.presentation.composables.ParticipantsDropdown
import com.jarica.compartirgastos.core.presentation.composables.PersonChip
import com.jarica.compartirgastos.core.presentation.composables.SplitChip
import com.jarica.compartirgastos.core.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.configDangerEyebrow
import com.jarica.compartirgastos.core.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.editCost
import com.jarica.compartirgastos.core.presentation.ui.editCostDeleteLabel
import com.jarica.compartirgastos.core.presentation.ui.editCostDeleteSub
import com.jarica.compartirgastos.core.presentation.ui.fromText
import com.jarica.compartirgastos.core.presentation.ui.participantsAll
import com.jarica.compartirgastos.core.presentation.ui.participantsLabel
import com.jarica.compartirgastos.core.presentation.ui.saveChangesText
import com.jarica.compartirgastos.core.presentation.ui.splitEqualAlt
import com.jarica.compartirgastos.core.presentation.ui.splitLabel
import com.jarica.compartirgastos.core.presentation.ui.splitPartsShort
import com.jarica.compartirgastos.core.presentation.ui.splitPercentage
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.toCentsOrNull
import com.jarica.compartirgastos.core.utils.toMoneyDisplay
import com.jarica.compartirgastos.core.utils.toMoneyString

private val DangerRed    = Color(0xFFC0533D)
private val DangerBg     = Color(0xFFFBE5E0)
private val DangerBorder = Color(0xFFF5D6CE)

@Composable
fun EditCostScreen(
    idCost: String,
    amount: Long,
    description: String,
    editCostScreenViewModel: EditCostScreenViewModel,
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(idCost) {
        editCostScreenViewModel.setIdCost(idCost)
    }

    val uiState                by editCostScreenViewModel.uiStateEditCost.collectAsState()
    val descriptionText        by editCostScreenViewModel.descriptionCost.observeAsState(description)
    val selectedParticipantIds by editCostScreenViewModel.selectedParticipantIds.collectAsState()

    when (val state = uiState) {
        is EditCostUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkOrange)
            }
        }
        is EditCostUiState.Error -> {}
        is EditCostUiState.Success -> {
            EditCostContent(
                descriptionText        = descriptionText,
                initialAmount          = amount,
                costPaymentsList       = state.listOfCostPaymentsModel,
                groupPeople            = state.groupPeople,
                currentParticipantIds  = state.currentParticipantIds,
                selectedParticipantIds = selectedParticipantIds,
                idCost                 = idCost,
                viewModel              = editCostScreenViewModel,
                navigateBack           = navigateToMainScreen,
                onDescriptionChange    = { editCostScreenViewModel.onDescriptionTextFieldChange(it) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EditCostContent(
    descriptionText: String,
    initialAmount: Long,
    costPaymentsList: List<CostPaymentsModel>,
    groupPeople: List<PersonModel>,
    currentParticipantIds: Set<String>,
    selectedParticipantIds: Set<String>?,
    idCost: String,
    viewModel: EditCostScreenViewModel,
    navigateBack: () -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    var amountText by remember { mutableStateOf(initialAmount.toMoneyString()) }

    // Participantes del reparto: por defecto los actuales del gasto (o todos si no hubiera).
    val baseIds        = currentParticipantIds.ifEmpty { groupPeople.map { it.idPerson }.toSet() }
    val participantIds = selectedParticipantIds ?: baseIds
    val participants   = groupPeople.filter { it.idPerson in participantIds }

    val canSave  = descriptionText.isNotBlank() &&
            amountText.toCentsOrNull() != null &&
            participants.isNotEmpty()
    val perPerson = amountText.toCentsOrNull()
        ?.div(participants.size.coerceAtLeast(1))

    val participantsAnchor = if (groupPeople.isNotEmpty() && participants.size == groupPeople.size) {
        participantsAll
    } else {
        stringResource(R.string.participants_count, participants.size, groupPeople.size)
    }

    val subtitle = if (participants.isNotEmpty())
        "${initialAmount.toMoneyDisplay()} · %d participante%s".format(
            participants.size,
            if (participants.size != 1) "s" else ""
        )
    else ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            CostFormHeader(
                title    = editCost,
                subtitle = subtitle,
                onBack   = navigateBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 100.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                FormSection(label = descriptionPlaceHolder) {
                    DescriptionField(
                        value         = descriptionText,
                        onValueChange = onDescriptionChange
                    )
                }

                FormSection(label = amountPlaceHolder) {
                    AmountField(
                        value         = amountText,
                        onValueChange = { amountText = it }
                    )
                }

                if (costPaymentsList.isNotEmpty()) {
                    FormSection(label = fromText) {
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement   = Arrangement.spacedBy(8.dp)
                        ) {
                            costPaymentsList.forEach { person ->
                                PersonChip(
                                    name     = person.name,
                                    selected = false,
                                    onClick  = {}
                                )
                            }
                        }
                    }
                }

                if (groupPeople.isNotEmpty()) {
                    FormSection(label = participantsLabel) {
                        ParticipantsDropdown(
                            anchorText  = participantsAnchor,
                            people      = groupPeople,
                            selectedIds = participantIds,
                            onToggle    = { viewModel.onParticipantToggled(it, baseIds) }
                        )
                    }
                }

                FormSection(label = splitLabel) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            SplitChip(label = splitEqualAlt,   selected = true,  enabled = true,  onClick = {})
                            SplitChip(label = splitPartsShort, selected = false, enabled = false, onClick = {})
                            SplitChip(label = splitPercentage, selected = false, enabled = false, onClick = {})
                        }
                        if (perPerson != null && participants.isNotEmpty()) {
                            Text(
                                text          = stringResource(R.string.split_each_pays, perPerson.toMoneyString()),
                                fontSize      = 11.sp,
                                color         = CostMuted,
                                fontFamily    = parkinsans,
                                letterSpacing = 0.01.em
                            )
                        }
                    }
                }

                // Zona de peligro
                Text(
                    text          = configDangerEyebrow.uppercase(),
                    fontSize      = 11.sp,
                    letterSpacing = 0.1.em,
                    color         = DangerRed,
                    fontWeight    = FontWeight.SemiBold,
                    fontFamily    = parkinsans,
                    modifier      = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .border(1.dp, DangerBorder, RoundedCornerShape(14.dp))
                        .background(White)
                        .clickable {
                            viewModel.onDeletedSelected(idCost)
                            navigateBack()
                        }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(DangerBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter            = painterResource(R.drawable.delete_svgrepo),
                            contentDescription = null,
                            tint               = DangerRed,
                            modifier           = Modifier.size(16.dp)
                        )
                    }
                    Column(
                        modifier            = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text       = editCostDeleteLabel,
                            fontSize   = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = DangerRed,
                            fontFamily = parkinsans
                        )
                        Text(
                            text       = editCostDeleteSub,
                            fontSize   = 11.sp,
                            color      = CostMuted,
                            fontFamily = parkinsans
                        )
                    }
                    Icon(
                        painter            = painterResource(R.drawable.right_arrow),
                        contentDescription = null,
                        tint               = DangerRed,
                        modifier           = Modifier.size(18.dp)
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
            Button(
                onClick  = {
                    val newAmount = amountText.toCentsOrNull() ?: initialAmount
                    viewModel.updateCost(descriptionText, newAmount, idCost, participants)
                    navigateBack()
                },
                enabled  = canSave,
                modifier = Modifier.fillMaxWidth(),
                shape    = RoundedCornerShape(18.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = DarkOrange,
                    contentColor           = White,
                    disabledContainerColor = Color(0xFFD0D5D9),
                    disabledContentColor   = Color(0xFF6B7A86)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text       = saveChangesText,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 15.sp,
                    modifier   = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

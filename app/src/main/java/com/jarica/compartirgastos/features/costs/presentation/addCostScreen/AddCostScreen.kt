package com.jarica.compartirgastos.features.costs.presentation.addCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.composables.AmountField
import com.jarica.compartirgastos.core.presentation.composables.DescriptionField
import com.jarica.compartirgastos.core.presentation.composables.FormSection
import com.jarica.compartirgastos.core.presentation.composables.PersonChip
import com.jarica.compartirgastos.core.presentation.composables.SplitChip
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import java.util.UUID

@Composable
fun AddCostScreen(
    addCostViewModel: AddCostScreenViewModel,
    navigateToMainScreen: () -> Unit,
    idGroupName: String,
) {
    LaunchedEffect(idGroupName) {
        addCostViewModel.setGroup(idGroupName)
    }

    val descriptionText by addCostViewModel.descriptionText.collectAsState()
    val amountText      by addCostViewModel.amountText.collectAsState()
    val selectedPerson  by addCostViewModel.personToAddCost.collectAsState()
    val uiAddCostState  by addCostViewModel.uiAddCostsUiState.collectAsState()

    when (val state = uiAddCostState) {
        is AddCostsUiState.Error   -> {}
        is AddCostsUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = DarkOrange)
            }
        }
        is AddCostsUiState.Success -> {
            AddCostContent(
                viewModel       = addCostViewModel,
                descriptionText = descriptionText,
                amountText      = amountText,
                selectedPerson  = selectedPerson,
                people          = state.listOfPeople,
                navigateBack    = navigateToMainScreen,
                idGroupName     = idGroupName,
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AddCostContent(
    viewModel: AddCostScreenViewModel,
    descriptionText: String,
    amountText: String,
    selectedPerson: PersonModel?,
    people: List<PersonModel>,
    navigateBack: () -> Unit,
    idGroupName: String,
) {
    val scrollState = rememberScrollState()
    val canSave = descriptionText.isNotEmpty() && amountText.isNotEmpty() && selectedPerson != null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        AddCostHeader(navigateBack = navigateBack)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            FormSection(label = "Descripción") {
                DescriptionField(
                    value         = descriptionText,
                    onValueChange = { viewModel.onDescriptionChange(it) }
                )
            }

            FormSection(label = "Cantidad") {
                AmountField(
                    value         = amountText,
                    onValueChange = { viewModel.onAmountChange(it) }
                )
            }

            FormSection(label = "Pagado por") {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp)
                ) {
                    people.forEach { person ->
                        PersonChip(
                            name     = person.name,
                            selected = selectedPerson?.idPerson == person.idPerson,
                            onClick  = { viewModel.onPersonSelected(person) }
                        )
                    }
                }
            }

            FormSection(label = "Tipo de reparto") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    SplitChip(label = "Igual",      selected = true,  enabled = true,  onClick = {})
                    SplitChip(label = "Por partes", selected = false, enabled = false, onClick = {})
                    SplitChip(label = "Porcentaje", selected = false, enabled = false, onClick = {})
                }
            }

            Spacer(Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled  = canSave,
                shape    = RoundedCornerShape(14.dp),
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = DarkOrange,
                    contentColor           = White,
                    disabledContainerColor = Grey,
                    disabledContentColor   = Color(0xFF6B7A86)
                ),
                onClick = {
                    val amount = amountText.replace(",", ".").toFloatOrNull() ?: return@Button
                    val cost = CostModel(
                        idCost      = UUID.randomUUID().toString(),
                        amount      = amount,
                        description = descriptionText,
                        idGroup     = idGroupName
                    )
                    viewModel.addCost(
                        idGroupName,
                        cost,
                        people,
                        amount,
                        selectedPerson!!.idPerson,
                        selectedPerson.name,
                        { navigateBack() }
                    )
                }
            ) {
                Text(
                    "Guardar",
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize   = 13.sp,
                    modifier   = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun AddCostHeader(navigateBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 22.dp, bottomStart = 22.dp))
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
                .padding(WindowInsets.statusBars.asPaddingValues())
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { navigateBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter            = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint               = White,
                        modifier           = Modifier.size(22.dp)
                    )
                }
                Text(
                    "Añadir Gasto",
                    fontSize      = 18.sp,
                    fontFamily    = parkinsans,
                    fontWeight    = FontWeight.SemiBold,
                    color         = White,
                    letterSpacing = (-0.01).em
                )
                Spacer(Modifier.size(36.dp))
            }
            Spacer(Modifier.height(22.dp))
        }
    }
}

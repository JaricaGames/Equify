package com.jarica.compartirgastos.features.costs.presentation.addCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import java.util.UUID

private val AddCostInk   = Color(0xFF1F2A33)
private val AddCostMuted = Color(0xFF6B7A86)
private val AddCostBorder = Color(0xFFD0D5D9)

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
                viewModel      = addCostViewModel,
                descriptionText = descriptionText,
                amountText     = amountText,
                selectedPerson = selectedPerson,
                people         = state.listOfPeople,
                navigateBack   = navigateToMainScreen,
                idGroupName    = idGroupName,
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
                    SplitChip(label = "Igual",       selected = true,  enabled = true,  onClick = {})
                    SplitChip(label = "Por partes",  selected = false, enabled = false, onClick = {})
                    SplitChip(label = "Porcentaje",  selected = false, enabled = false, onClick = {})
                }
            }

            Spacer(Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = canSave,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = DarkOrange,
                    contentColor           = White,
                    disabledContainerColor = Grey,
                    disabledContentColor   = AddCostMuted
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
                    fontFamily  = parkinsans,
                    fontWeight  = FontWeight.SemiBold,
                    fontSize    = 13.sp,
                    modifier    = Modifier.padding(vertical = 4.dp)
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
                modifier = Modifier.fillMaxWidth(),
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
                        painter           = painterResource(R.drawable.arrow_back),
                        contentDescription = null,
                        tint              = White,
                        modifier          = Modifier.size(22.dp)
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

@Composable
private fun FormSection(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            label.uppercase(),
            fontSize      = 11.sp,
            fontFamily    = parkinsans,
            fontWeight    = FontWeight.SemiBold,
            color         = AddCostMuted,
            letterSpacing = 0.08.em
        )
        content()
    }
}

@Composable
private fun DescriptionField(value: String, onValueChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    BasicTextField(
        value         = value,
        onValueChange = onValueChange,
        singleLine    = true,
        textStyle     = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            fontSize   = 12.sp,
            color      = AddCostInk
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
                        "Descripción del gasto",
                        style = TextStyle(
                            fontFamily = parkinsans,
                            fontWeight = FontWeight.Normal,
                            fontSize   = 12.sp,
                            color      = AddCostMuted.copy(alpha = 0.5f)
                        )
                    )
                }
                innerTextField()
            }
        }
    )
}

@Composable
private fun AmountField(value: String, onValueChange: (String) -> Unit) {
    var isFocused by remember { mutableStateOf(false) }
    val hasCost = value.isNotEmpty()
    BasicTextField(
        value         = value,
        onValueChange = onValueChange,
        singleLine    = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        textStyle     = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            fontSize   = 42.sp,
            color      = AddCostInk
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
                                color      = AddCostMuted.copy(alpha = 0.25f)
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
                        color      = if (hasCost) AddCostMuted else AddCostMuted.copy(alpha = 0.25f)
                    ),
                    modifier = Modifier.padding(bottom = 4.dp, start = 4.dp)
                )
            }
        }
    )
}

@Composable
private fun PersonChip(name: String, selected: Boolean, onClick: () -> Unit) {
    val bgColor     = if (selected) DarkBlue else Color.Transparent
    val borderColor = if (selected) DarkBlue else AddCostBorder
    val textColor   = if (selected) White else AddCostInk
    val avatarColor = if (selected) DarkOrange else DarkBlue

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(start = 6.dp, end = 12.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
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
                name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                fontSize   = 9.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                color      = White
            )
        }
        Text(
            name,
            fontSize   = 12.sp,
            fontFamily = parkinsans,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = textColor
        )
    }
}

@Composable
private fun SplitChip(label: String, selected: Boolean, enabled: Boolean, onClick: () -> Unit) {
    val bgColor     = if (!enabled) Color(0xFFF4F4F4) else if (selected) DarkOrange else Color.Transparent
    val textColor   = if (!enabled) Color(0xFFB0B8BF) else if (selected) White else AddCostInk
    val borderColor = if (!enabled) Color(0xFFE0E4E7) else if (selected) DarkOrange else AddCostBorder

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
            label,
            fontSize   = 12.sp,
            fontFamily = parkinsans,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = textColor
        )
    }
}

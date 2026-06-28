package com.jarica.compartirgastos.features.payments.presentation.addPayScreen

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.addPaymentAmountLabel
import com.jarica.compartirgastos.core.presentation.ui.addPaymentChangePeopleLabel
import com.jarica.compartirgastos.core.presentation.ui.addPaymentFromLabel
import com.jarica.compartirgastos.core.presentation.ui.addPaymentFromTag
import com.jarica.compartirgastos.core.presentation.ui.addPaymentSelectPerson
import com.jarica.compartirgastos.core.presentation.ui.addPaymentSubtitle
import com.jarica.compartirgastos.core.presentation.ui.addPaymentTitle
import com.jarica.compartirgastos.core.presentation.ui.addPaymentToLabel
import com.jarica.compartirgastos.core.presentation.ui.addPaymentToTag
import com.jarica.compartirgastos.core.presentation.ui.registerPaymentText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.GroupsAvatarColors
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val LineColor  = Color(0xFFE6E4DE)
private val MutedColor = Color(0xFF6B7A86)
private val InkColor   = Color(0xFF1F2A33)

@Composable
fun AddPaymentScreen(
    groupId: String,
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    navigateToMainScreen: () -> Unit
) {
    LaunchedEffect(groupId) {
        addPaymentScreenViewModel.clearText()
        addPaymentScreenViewModel.setGroup(groupId)
        addPaymentScreenViewModel.loadGroupName(groupId)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiAddPaymentState by produceState<AddPaymentUiState>(
        initialValue = AddPaymentUiState.Loading,
        key1 = lifecycle,
        key2 = addPaymentScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            addPaymentScreenViewModel.uiStatePeopleList.collect { value = it }
        }
    }

    val groupName        by addPaymentScreenViewModel.groupName.observeAsState("")
    val amountText       by addPaymentScreenViewModel.amountText.observeAsState("")
    val personWhoPayText by addPaymentScreenViewModel.personWhoPayText.observeAsState("")
    val personWhoReceiveText by addPaymentScreenViewModel.personWhoReceiveText.observeAsState("")
    val isPaidForSelected by addPaymentScreenViewModel.isPaidForSelected.observeAsState(false)
    val isPaidToSelected  by addPaymentScreenViewModel.isPaidToSelected.observeAsState(false)
    val personWhoReceive by addPaymentScreenViewModel.personWhoReceive.observeAsState(
        PersonModel(idPerson = "", name = "", idGroupName = "")
    )
    val personWhoPay by addPaymentScreenViewModel.personWhoPay.observeAsState(
        PersonModel(idPerson = "", name = "", idGroupName = "")
    )

    when (uiAddPaymentState) {
        is AddPaymentUiState.Error   -> {}
        is AddPaymentUiState.Loading -> {}
        is AddPaymentUiState.Success -> {
            val listOfPeople = (uiAddPaymentState as AddPaymentUiState.Success).listOfPeople
            MainScreenAddPayment(
                viewModel            = addPaymentScreenViewModel,
                groupName            = groupName,
                groupId              = groupId,
                amountText           = amountText,
                listOfPeople         = listOfPeople,
                isPaidForSelected    = isPaidForSelected,
                isPaidToSelected     = isPaidToSelected,
                personWhoPay         = personWhoPay,
                personWhoReceive     = personWhoReceive,
                personWhoPayText     = personWhoPayText,
                personWhoReceiveText = personWhoReceiveText,
                navigateToMainScreen = navigateToMainScreen
            )
        }
    }
}

@Composable
private fun MainScreenAddPayment(
    viewModel: AddPaymentScreenViewModel,
    groupName: String,
    groupId: String,
    amountText: String,
    listOfPeople: List<PersonModel>,
    isPaidForSelected: Boolean,
    isPaidToSelected: Boolean,
    personWhoPay: PersonModel,
    personWhoReceive: PersonModel,
    personWhoPayText: String,
    personWhoReceiveText: String,
    navigateToMainScreen: () -> Unit,
) {
    val isFormValid = personWhoPay.name.isNotEmpty() &&
            personWhoReceive.name.isNotEmpty() &&
            amountText.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            AddPaymentHeader(groupName = groupName, onBack = navigateToMainScreen)

            // ── Visualización del trasvase ─────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 22.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PersonTransferCard(
                    person = personWhoPay,
                    colorIndex = listOfPeople.indexOfFirst { it.idPerson == personWhoPay.idPerson }.coerceAtLeast(0),
                    tag = addPaymentFromTag
                )
                Spacer(Modifier.width(12.dp))
                TransferArrow()
                Spacer(Modifier.width(12.dp))
                PersonTransferCard(
                    person = personWhoReceive,
                    colorIndex = listOfPeople.indexOfFirst { it.idPerson == personWhoReceive.idPerson }.coerceAtLeast(1),
                    tag = addPaymentToTag
                )
            }

            // ── Formulario ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Campo: Cantidad
                FormField(label = addPaymentAmountLabel) {
                    AmountInput(
                        value = amountText,
                        onValueChange = { viewModel.onAmountChange(it) }
                    )
                }

                // Campo: Cambiar personas
                FormField(label = addPaymentChangePeopleLabel) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        PersonPickerBox(
                            modifier = Modifier.weight(1f),
                            label = addPaymentFromLabel,
                            personName = personWhoPayText,
                            colorIndex = listOfPeople.indexOfFirst { it.idPerson == personWhoPay.idPerson }.coerceAtLeast(0),
                            isExpanded = isPaidForSelected,
                            onClick = { viewModel.onFromSelected(isPaidForSelected) }
                        )
                        PersonPickerBox(
                            modifier = Modifier.weight(1f),
                            label = addPaymentToLabel,
                            personName = personWhoReceiveText,
                            colorIndex = listOfPeople.indexOfFirst { it.idPerson == personWhoReceive.idPerson }.coerceAtLeast(1),
                            isExpanded = isPaidToSelected,
                            onClick = { viewModel.onToSelected(isPaidToSelected) }
                        )
                    }

                    // Dropdown de selección de persona
                    if (isPaidForSelected || isPaidToSelected) {
                        Spacer(Modifier.height(6.dp))
                        val excludeName = if (isPaidForSelected) personWhoReceiveText else personWhoPayText
                        val eligible = listOfPeople.filter {
                            it.idGroupName == groupId && it.name != excludeName
                        }
                        PersonDropdown(
                            people = eligible,
                            listOfPeople = listOfPeople,
                            onSelect = { person ->
                                if (isPaidForSelected) viewModel.onPersonWhoPaySelected(person)
                                else viewModel.onPersonWhoReceiveSelected(person)
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(120.dp))
        }

        // ── Barra de acción ────────────────────────────────────────────
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.35f to White,
                            1f to White
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp, bottom = 22.dp)
        ) {
            Button(
                onClick = {
                    viewModel.addPayment(personWhoPay, personWhoReceive, amountText)
                    viewModel.clearTexts()
                    navigateToMainScreen()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = isFormValid,
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
                    text = registerPaymentText,
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
private fun AddPaymentHeader(groupName: String, onBack: () -> Unit) {
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
                    text = groupName,
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = addPaymentTitle,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                fontFamily = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = addPaymentSubtitle,
                fontSize = 13.sp,
                color = White.copy(alpha = 0.75f),
                fontFamily = parkinsans
            )
        }
    }
}

@Composable
private fun PersonTransferCard(
    person: PersonModel,
    colorIndex: Int,
    tag: String,
) {
    val avatarColor = GroupsAvatarColors[colorIndex % GroupsAvatarColors.size]
    val hasName = person.name.isNotEmpty()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(if (hasName) avatarColor else LineColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (hasName) person.name.first().uppercaseChar().toString() else "?",
                color = if (hasName) White else MutedColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                fontFamily = parkinsans
            )
        }
        Text(
            text = if (hasName) person.name else "—",
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (hasName) InkColor else MutedColor,
            fontFamily = parkinsans
        )
        Text(
            text = tag.uppercase(),
            fontSize = 10.sp,
            color = MutedColor,
            letterSpacing = 0.08.em,
            fontFamily = parkinsans
        )
    }
}

@Composable
private fun TransferArrow() {
    Canvas(modifier = Modifier.size(56.dp, 24.dp)) {
        val stroke = 2.5.dp.toPx()
        val dash = PathEffect.dashPathEffect(floatArrayOf(4.dp.toPx(), 4.dp.toPx()), 0f)

        // Línea punteada
        drawLine(
            color = DarkOrange,
            start = Offset(2.dp.toPx(), 12.dp.toPx()),
            end = Offset(46.dp.toPx(), 12.dp.toPx()),
            strokeWidth = stroke,
            cap = StrokeCap.Round,
            pathEffect = dash
        )

        // Punta de flecha
        val arrowPath = Path().apply {
            moveTo(40.dp.toPx(), 5.dp.toPx())
            lineTo(52.dp.toPx(), 12.dp.toPx())
            lineTo(40.dp.toPx(), 19.dp.toPx())
        }
        drawPath(
            path = arrowPath,
            color = DarkOrange,
            style = Stroke(width = stroke, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Composable
private fun FormField(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label.uppercase(),
            fontSize = 11.sp,
            letterSpacing = 0.06.em,
            color = MutedColor,
            fontWeight = FontWeight.SemiBold,
            fontFamily = parkinsans
        )
        content()
    }
}

@Composable
private fun AmountInput(value: String, onValueChange: (String) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = InkColor,
            letterSpacing = (-0.02).em
        ),
        cursorBrush = SolidColor(DarkBlue),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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
                    .padding(horizontal = 18.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    if (value.isEmpty()) {
                        Text(
                            text = "0",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MutedColor,
                            fontFamily = parkinsans,
                            letterSpacing = (-0.02).em
                        )
                    }
                    innerTextField()
                }
                Text(
                    text = "€",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MutedColor.copy(alpha = 0.7f),
                    fontFamily = parkinsans
                )
            }
        }
    )
}

@Composable
private fun PersonPickerBox(
    modifier: Modifier,
    label: String,
    personName: String,
    colorIndex: Int,
    isExpanded: Boolean,
    onClick: () -> Unit,
) {
    val hasName = personName.isNotEmpty()
    val avatarColor = GroupsAvatarColors[colorIndex % GroupsAvatarColors.size]
    val borderColor = if (isExpanded) DarkBlue else LineColor

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(if (isExpanded) 1.5.dp else 1.dp, borderColor, RoundedCornerShape(14.dp))
            .background(White)
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Avatar pequeño
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(if (hasName) avatarColor else LineColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (hasName) personName.first().uppercaseChar().toString() else "?",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = if (hasName) White else MutedColor,
                fontFamily = parkinsans
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label.uppercase(),
                fontSize = 10.sp,
                color = MutedColor,
                letterSpacing = 0.06.em,
                fontFamily = parkinsans
            )
            Text(
                text = if (hasName) personName else addPaymentSelectPerson,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (hasName) InkColor else MutedColor,
                fontFamily = parkinsans,
                maxLines = 1
            )
        }

        Icon(
            painter = painterResource(R.drawable.right_arrow),
            contentDescription = "",
            tint = MutedColor.copy(alpha = 0.5f),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun PersonDropdown(
    people: List<PersonModel>,
    listOfPeople: List<PersonModel>,
    onSelect: (PersonModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, LineColor, RoundedCornerShape(14.dp))
            .background(White)
    ) {
        people.forEachIndexed { index, person ->
            val colorIndex = listOfPeople.indexOfFirst { it.idPerson == person.idPerson }.coerceAtLeast(0)
            val avatarColor = GroupsAvatarColors[colorIndex % GroupsAvatarColors.size]

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelect(person) }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
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
                        text = person.name.first().uppercaseChar().toString(),
                        color = White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp,
                        fontFamily = parkinsans
                    )
                }
                Text(
                    text = person.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = InkColor,
                    fontFamily = parkinsans,
                    modifier = Modifier.weight(1f)
                )
            }
            if (index < people.size - 1) {
                HorizontalDivider(color = LineColor, thickness = 1.dp)
            }
        }
    }
}

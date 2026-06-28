package com.jarica.compartirgastos.features.payments.presentation.editPaymentScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.composables.CustomTextField
import com.jarica.compartirgastos.core.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.editCost
import com.jarica.compartirgastos.core.presentation.ui.payForPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.payToPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.HEADER_WEIGHT
import com.jarica.compartirgastos.core.utils.toCentsOrNull
import com.jarica.compartirgastos.core.utils.toMoneyString

@Composable
fun EditPaymentScreen(
    idPayment: String,
    amount: Long,
    personWhoPay: String,
    personWhoReceive: String,
    editPaymentsViewModel: EditPaymentViewModel,
    navigateToMainScreen: () -> Unit
) {

    val namePersonWhoPay by produceState(initialValue = "", key1 = personWhoPay) {
        val name = editPaymentsViewModel.getPersonName(personWhoPay)
        value = name
    }

    val namePersonWhoReceive by produceState(initialValue = "", key1 = personWhoReceive) {
        val name = editPaymentsViewModel.getPersonName(personWhoReceive)
        value = name
    }


    MainScreenAddPayment(
        editPaymentsViewModel,
        amount,
        navigateToMainScreen,
        idPayment,
        namePersonWhoPay,
        namePersonWhoReceive
    )
}


@Composable
fun MainScreenAddPayment(
    editPaymentScreenViewModel: EditPaymentViewModel,
    amount: Long,
    navigateToMainScreen: () -> Unit,
    idPayment: String,
    namePersonWhoPay: String,
    namePersonWhoReceive: String,
) {

    var amountText by remember { mutableStateOf(amount.toMoneyString()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 22.dp, bottomStart = 22.dp))
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
                .padding(bottom = 20.dp)
                .weight(HEADER_WEIGHT),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(start = 16.dp)
                    .size(24.dp)
                    .offset(x = 2.dp),
                onClick =
                    { navigateToMainScreen() }
            ) {
                Icon(

                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint = White
                )
            }
            Text(
                editCost,
                fontSize = 16.sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 16.dp)
                    .size(24.dp)
                    .offset(x = 2.dp),
                onClick =
                    {
                        editPaymentScreenViewModel.onDeletedSelected(idPayment)
                        navigateToMainScreen()
                    }
            ) {
                Icon(

                    painter = painterResource(
                        id = R.drawable.delete_svgrepo
                    ),
                    contentDescription = "",
                    tint = White
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT)
        ) {

            Spacer(Modifier.height(20.dp))
            // TEXTFIELD PAGADO POR
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "$payForPlaceHolder:      $namePersonWhoPay",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = DarkOrange.copy(0.2f)
                )

            }
            Spacer(modifier = Modifier.size(20.dp))
            // TEXTFIELD PAGADO A
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "$payToPlaceHolder:      $namePersonWhoReceive",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = DarkOrange.copy(0.2f)
                )

            }
            Spacer(modifier = Modifier.size(20.dp))
            //TEXTFIELD CANTIDAD
            CustomTextField(
                value = amountText,
                enabled = true,
                onValueChange = { amountText = it },
                placeholderText = amountPlaceHolder,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = DarkOrange.copy(0.2f)
            )
            Spacer(Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    editPaymentScreenViewModel.updatePaymentSelected(
                        idPayment,
                        amountText.toCentsOrNull() ?: amount
                    )
                    navigateToMainScreen()
                }) {
                Text(
                    editCost,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }


    }
}


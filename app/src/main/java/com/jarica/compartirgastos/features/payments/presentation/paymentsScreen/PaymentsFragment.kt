package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.core.presentation.ui.amountText
import com.jarica.compartirgastos.core.presentation.ui.payForText
import com.jarica.compartirgastos.core.presentation.ui.payToText
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

@Composable
fun PaymentsFragment(
    idGroup: String?,
    paymentsViewModel: PaymentsScreenViewModel,
    modifier: Modifier
) {

    LaunchedEffect(idGroup) {
        paymentsViewModel.setGroup(idGroup)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePaymentsFragment by produceState<PaymentsScreenUiState>(
        initialValue = PaymentsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = paymentsViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            paymentsViewModel.uiStatePayments.collect { value = it }
        }
    }

    when (uiStatePaymentsFragment) {
        is PaymentsScreenUiState.Error -> {}
        is PaymentsScreenUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PaymentsScreenUiState.Success -> {

            PaymentsList(
                (uiStatePaymentsFragment as PaymentsScreenUiState.Success).paymentsList,
                idGroup
            )

        }
    }

}

@Composable
fun PaymentsList(paymentsList: List<PaymentsModel>, idGroup: String?) {

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(paymentsList) { payment ->
            if (payment.idGroup == idGroup) {
                ItemPaymentName(payment)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun ItemPaymentName(item: PaymentsModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            payForText,
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Text(
            item.idPersonWhoPay,
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            payToText,
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Text(
            item.idPersonWhoReceive,
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            amountText,
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Text(
            item.amount.toString(),
            fontSize = 12.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Text(
            " €",
            fontSize = 12.sp,
            color = DarkOrange,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
    }
}
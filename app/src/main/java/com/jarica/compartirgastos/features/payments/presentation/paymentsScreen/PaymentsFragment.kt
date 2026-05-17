package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.core.presentation.composables.CustomIcon
import com.jarica.compartirgastos.core.presentation.composables.EmptyState
import com.jarica.compartirgastos.core.presentation.ui.emptyPaymentsSubtitle
import com.jarica.compartirgastos.core.presentation.ui.emptyPaymentsTitle
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans

private val PayDivider = Color(0xFFE6E4DE)
private val PayInk     = Color(0xFF1F2A33)
private val PayMuted   = Color(0xFF6B7A86)

@Composable
fun PaymentsFragment(
    idGroup: String?,
    paymentsViewModel: PaymentsScreenViewModel,
    modifier: Modifier,
    navigateToEditPayments: (PaymentsModel) -> Unit
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
                paymentsViewModel,
                navigateToEditPayments,
                modifier
            )
        }
    }
}

@Composable
fun PaymentsList(
    paymentsList: List<PaymentsModel>,
    paymentsViewModel: PaymentsScreenViewModel,
    navigateToEditPayments: (PaymentsModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (paymentsList.isEmpty()) {
        EmptyState(
            title = emptyPaymentsTitle,
            subtitle = emptyPaymentsSubtitle
        )
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(paymentsList, key = { it.idPayment }) { payment ->
                ItemPaymentName(payment, paymentsViewModel, navigateToEditPayments)
            }
        }
    }
}

@Composable
fun ItemPaymentName(
    item: PaymentsModel,
    paymentsViewModel: PaymentsScreenViewModel,
    navigateToEditPayments: (PaymentsModel) -> Unit
) {
    val namePersonWhoPay by produceState(initialValue = "", key1 = item.idPersonWhoPay) {
        value = paymentsViewModel.getPersonName(item.idPersonWhoPay)
    }

    val namePersonWhoReceive by produceState(initialValue = "", key1 = item.idPersonWhoReceive) {
        value = paymentsViewModel.getPersonName(item.idPersonWhoReceive)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToEditPayments(item) }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomIcon(name = namePersonWhoPay.ifEmpty { "?" })

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                namePersonWhoPay,
                fontSize = 14.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Medium,
                color = PayInk
            )
            Text(
                "→ $namePersonWhoReceive",
                fontSize = 12.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
                color = PayMuted
            )
        }

        Spacer(Modifier.weight(1f))

        Text(
            "${"%.2f".format(item.amount)} €",
            fontSize = 15.sp,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            color = DarkOrange
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 18.dp),
        color = PayDivider.copy(alpha = 0.5f),
        thickness = 1.dp
    )
}

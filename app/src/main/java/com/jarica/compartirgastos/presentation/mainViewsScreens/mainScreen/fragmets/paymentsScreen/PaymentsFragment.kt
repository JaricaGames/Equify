package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.PaymentsModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.ui.amountText
import com.jarica.compartirgastos.presentation.ui.payForText
import com.jarica.compartirgastos.presentation.ui.payToText
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@Composable
fun PaymentsFragment(idGroup: Int?, mainScreenViewModel: MainScreenViewModel) {




    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePaymentsFragment by produceState<PaymentsScreenUiState>(
        initialValue = PaymentsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStatePayments.collect { value = it }
        }
    }

    when (uiStatePaymentsFragment) {

        is PaymentsScreenUiState.Error -> {}

        is PaymentsScreenUiState.Loading -> {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PaymentsScreenUiState.Success -> {

            mainScreenViewModel.getGroupNameById(idGroup!!)
            PeopleList((uiStatePaymentsFragment as PaymentsScreenUiState.Success).paymentsList, idGroup)

        }
    }

}

@Composable
fun PeopleList(paymentsList: List<PaymentsModel>, idGroup: Int?) {

    LazyColumn {
        items(paymentsList) { payment ->
            if (payment.idGroup  == idGroup) {
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
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        Text(payToText , color = Black, fontFamily = rubik, fontSize = 10.sp)
        Text(item.namePersonWhoPay , color = Black, fontFamily = rubik)
        Spacer(modifier = Modifier.weight(1f))
        Text(payForText, color = Black, fontFamily = rubik, fontSize = 10.sp)
        Text(item.namePersonWhoReceive , color = Black, fontFamily = rubik)
        Spacer(modifier = Modifier.weight(1f))
        Text(amountText , color = Black, fontFamily = rubik, fontSize = 10.sp)
        Text(item.amount , color = Black, fontFamily = rubik)
        Text(" €", color = Black, fontFamily = rubik)





    }
}
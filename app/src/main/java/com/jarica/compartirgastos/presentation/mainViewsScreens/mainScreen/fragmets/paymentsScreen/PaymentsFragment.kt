package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen

import androidx.compose.runtime.Composable
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel

@Composable
fun PaymentsFragment(idGroup: Int?, mainScreenViewModel: MainScreenViewModel) {




    /*val lifecycle = LocalLifecycleOwner.current.lifecycle
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





    }*/
}
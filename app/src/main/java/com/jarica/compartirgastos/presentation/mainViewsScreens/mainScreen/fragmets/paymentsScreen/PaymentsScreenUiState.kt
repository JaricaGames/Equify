package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen

import com.jarica.compartirgastos.domain.models.PaymentsModel

sealed interface PaymentsScreenUiState {

    data object Loading: PaymentsScreenUiState
    data class Error(val throwable: Throwable): PaymentsScreenUiState
    data class Success(val paymentsList:List<PaymentsModel>): PaymentsScreenUiState
}

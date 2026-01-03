package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import com.jarica.compartirgastos.core.domain.models.PaymentsModel

sealed interface PaymentsScreenUiState {

    data object Loading: PaymentsScreenUiState
    data class Error(val throwable: Throwable): PaymentsScreenUiState
    data class Success(val paymentsList:List<PaymentsModel>): PaymentsScreenUiState
}

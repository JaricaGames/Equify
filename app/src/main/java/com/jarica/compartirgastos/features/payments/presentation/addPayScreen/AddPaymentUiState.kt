package com.jarica.compartirgastos.features.payments.presentation.addPayScreen

import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface AddPaymentUiState {

    data object LoadingAddPayment: AddPaymentUiState
    data class ErrorAddPayment(val throwable: Throwable): AddPaymentUiState
    data class SuccessAddPayment(val listOfPeople: List<PersonModel>): AddPaymentUiState

}
package com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen

import com.jarica.compartirgastos.domain.models.PersonModel

sealed interface AddPaymentUiState {

    data object LoadingAddPayment: AddPaymentUiState
    data class ErrorAddPayment(val throwable: Throwable): AddPaymentUiState
    data class SuccessAddPayment(val listOfPeople: List<PersonModel>): AddPaymentUiState

}
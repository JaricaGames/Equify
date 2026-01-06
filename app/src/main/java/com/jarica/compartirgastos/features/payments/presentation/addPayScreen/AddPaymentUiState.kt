package com.jarica.compartirgastos.features.payments.presentation.addPayScreen

import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface AddPaymentUiState {

    data object Loading: AddPaymentUiState
    data class Error(val throwable: Throwable): AddPaymentUiState
    data class Success(val listOfPeople: List<PersonModel>): AddPaymentUiState

}
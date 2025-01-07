package com.jarica.compartirgastos.presentation.addCostScreen

import com.jarica.compartirgastos.domain.models.PersonModel

sealed interface AddCostsUiState {

    data object LoadingAddCosts: AddCostsUiState
    data class ErrorAddCosts(val throwable: Throwable):AddCostsUiState
    data class SuccessAddCosts(val listOfPeople: List<PersonModel>):AddCostsUiState
}
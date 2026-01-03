package com.jarica.compartirgastos.features.costs.presentation.addCostScreen


import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface AddCostsUiState {

    data object Loading: AddCostsUiState
    data class Error(val throwable: Throwable): AddCostsUiState
    data class Success(val listOfPeople: List<PersonModel>): AddCostsUiState
}
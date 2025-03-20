package com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen


import com.jarica.compartirgastos.domain.models.PersonModel

sealed interface AddCostsUiState {

    data object Loading: AddCostsUiState
    data class Error(val throwable: Throwable): AddCostsUiState
    data class Success(val listOfPeople: List<PersonModel>): AddCostsUiState
}
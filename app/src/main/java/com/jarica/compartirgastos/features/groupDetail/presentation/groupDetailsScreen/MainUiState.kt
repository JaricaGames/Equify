package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface MainUiState {

    data object Loading: MainUiState
    data class Error(val throwable: Throwable): MainUiState
    data class Success(val peopleList:List<PersonModel>): MainUiState

}

sealed class TotalExpensesUiState {
    object Loading : TotalExpensesUiState()
    data class Success(val totalCost: Float) : TotalExpensesUiState()
    data class Error(val throwable: Throwable) : TotalExpensesUiState()
}
package com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen

import com.jarica.compartirgastos.domain.models.CostPaymentsModel

sealed interface EditCostUiState {

    data object Loading: EditCostUiState
    data class Error(val throwable: Throwable): EditCostUiState
    data class Success(val listOfCostPaymentsModel: List<CostPaymentsModel>): EditCostUiState
}
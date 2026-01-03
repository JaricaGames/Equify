package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel

sealed interface EditCostUiState {

    data object Loading: EditCostUiState
    data class Error(val throwable: Throwable): EditCostUiState
    data class Success(val listOfCostPaymentsModel: List<CostPaymentsModel>): EditCostUiState
}
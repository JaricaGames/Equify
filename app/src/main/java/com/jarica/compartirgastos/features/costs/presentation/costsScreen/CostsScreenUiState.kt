package com.jarica.compartirgastos.features.costs.presentation.costsScreen

import com.jarica.compartirgastos.core.domain.models.CostModel

sealed interface CostsScreenUiState {

    data object Loading: CostsScreenUiState
    data class Error(val throwable: Throwable): CostsScreenUiState
    data class Success(val costsList:List<CostModel>): CostsScreenUiState
}
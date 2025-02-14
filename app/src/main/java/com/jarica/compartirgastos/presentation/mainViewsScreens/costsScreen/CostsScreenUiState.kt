package com.jarica.compartirgastos.presentation.mainViewsScreens.costsScreen


import com.jarica.compartirgastos.domain.models.CostModel

sealed interface CostsScreenUiState {

    data object Loading: CostsScreenUiState
    data class Error(val throwable: Throwable): CostsScreenUiState
    data class Success(val costsList:List<CostModel>): CostsScreenUiState
}
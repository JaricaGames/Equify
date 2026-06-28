package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface EditCostUiState {

    data object Loading: EditCostUiState
    data class Error(val throwable: Throwable): EditCostUiState
    data class Success(
        val listOfCostPaymentsModel: List<CostPaymentsModel>,
        val groupPeople: List<PersonModel> = emptyList(),
        val currentParticipantIds: Set<String> = emptySet(),
    ): EditCostUiState
}
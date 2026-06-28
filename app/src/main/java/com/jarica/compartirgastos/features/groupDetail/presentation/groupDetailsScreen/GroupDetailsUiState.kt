package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface GroupDetailsUiState {

    data object Loading: GroupDetailsUiState
    data class Error(val throwable: Throwable): GroupDetailsUiState
    data class Success(val peopleList:List<PersonModel>): GroupDetailsUiState

}

sealed class TotalExpensesUiState {
    object Loading : TotalExpensesUiState()
    data class Success(val totalCost: Long) : TotalExpensesUiState()
    data class Error(val throwable: Throwable) : TotalExpensesUiState()
}

sealed class GroupNameUiState {
    object Loading : GroupNameUiState()
    data class Success(val group: GroupModel) : GroupNameUiState()
    data class Error(val throwable: Throwable) : GroupNameUiState()
}


package com.jarica.compartirgastos.presentation.groupsScreen

import com.jarica.compartirgastos.domain.models.GroupModel

sealed interface GroupUiState {

    data object Loading: GroupUiState
    data class Error(val throwable: Throwable): GroupUiState
    data class Success(val groupsList:List<GroupModel>): GroupUiState

}
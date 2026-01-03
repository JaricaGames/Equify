package com.jarica.compartirgastos.features.groups.presentation.groupsScreen

import com.jarica.compartirgastos.core.domain.models.GroupModel

sealed interface GroupUiState {

    data object Loading: GroupUiState
    data class Error(val throwable: Throwable): GroupUiState
    data class Success(val groupsList:List<GroupModel>): GroupUiState

}
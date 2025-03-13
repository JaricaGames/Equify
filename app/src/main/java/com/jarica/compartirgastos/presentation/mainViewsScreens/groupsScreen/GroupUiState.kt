package com.jarica.compartirgastos.presentation.mainViewsScreens.groupsScreen

import com.jarica.compartirgastos.domain.models.GroupNameModel

sealed interface GroupUiState {

    data object Loading: GroupUiState
    data class Error(val throwable: Throwable): GroupUiState
    data class Success(val groupsList:List<GroupNameModel>): GroupUiState

}
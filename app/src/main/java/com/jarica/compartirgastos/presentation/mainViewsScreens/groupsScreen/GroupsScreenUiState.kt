package com.jarica.compartirgastos.presentation.mainViewsScreens.groupsScreen

import com.jarica.compartirgastos.domain.models.GroupNameModel

sealed interface GroupsScreenUiState {

    data object Loading: GroupsScreenUiState
    data class Error(val throwable: Throwable): GroupsScreenUiState
    data class Success(val groupsList:List<GroupNameModel>): GroupsScreenUiState
}
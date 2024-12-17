package com.jarica.compartirgastos.presentation.groupScreen

import com.jarica.compartirgastos.domain.model.GroupNameModel

sealed interface GroupUiState {

    object Loading: GroupUiState
    data class Error(val throwable: Throwable): GroupUiState
    data class Success(val groupName:List<GroupNameModel>): GroupUiState

}
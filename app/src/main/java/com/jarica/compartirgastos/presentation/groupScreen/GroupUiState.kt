package com.jarica.compartirgastos.presentation.groupScreen

import com.jarica.compartirgastos.domain.models.PersonModel

sealed interface GroupUiState {

    data object Loading: GroupUiState
    data class Error(val throwable: Throwable): GroupUiState
    data class Success(val peopleList:List<PersonModel>): GroupUiState

}
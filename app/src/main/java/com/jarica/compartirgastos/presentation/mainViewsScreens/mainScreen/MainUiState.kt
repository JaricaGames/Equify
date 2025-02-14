package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import com.jarica.compartirgastos.domain.models.PersonModel

sealed interface MainUiState {

    data object Loading: MainUiState
    data class Error(val throwable: Throwable): MainUiState
    data class Success(val peopleList:List<PersonModel>): MainUiState

}
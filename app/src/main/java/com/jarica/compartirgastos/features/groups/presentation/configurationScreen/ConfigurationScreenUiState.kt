package com.jarica.compartirgastos.features.groups.presentation.configurationScreen

import com.jarica.compartirgastos.core.domain.models.PersonModel

sealed interface ConfigurationScreenUiState {

    data object Loading: ConfigurationScreenUiState
    data class Error(val throwable: Throwable): ConfigurationScreenUiState
    data class Success(val listOfPeople: List<PersonModel>): ConfigurationScreenUiState

}
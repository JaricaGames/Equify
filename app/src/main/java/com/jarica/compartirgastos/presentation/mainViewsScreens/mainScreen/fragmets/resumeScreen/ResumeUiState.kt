package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.resumeScreen

import com.jarica.compartirgastos.domain.models.PersonBalance

sealed interface ResumeUiState {
    data object Loading : ResumeUiState
    data class Error(val message: Throwable) : ResumeUiState
    data class Success(val peopleList: List<PersonBalance>) : ResumeUiState
}
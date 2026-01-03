package com.jarica.compartirgastos.features.groupDetail.presentation.resumeScreen

import com.jarica.compartirgastos.core.domain.models.PersonBalance

sealed interface ResumeUiState {
    data object Loading : ResumeUiState
    data class Error(val message: Throwable) : ResumeUiState
    data class Success(val peopleList: List<PersonBalance>) : ResumeUiState
}
package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.costsUseCases.GetCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CostsScreenViewModel @Inject constructor(
    getCostsUseCase: GetCostsUseCase
):ViewModel() {

    val uiStateCosts: StateFlow<CostsScreenUiState> = getCostsUseCase().map(CostsScreenUiState::Success)
        .catch { CostsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CostsScreenUiState.Loading)

}
package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen

import androidx.lifecycle.ViewModel
import com.jarica.compartirgastos.domain.costsUseCases.GetCostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CostsScreenViewModel @Inject constructor(
    getCostsUseCase: GetCostsUseCase
):ViewModel() {


/*    val uiStateCosts: StateFlow<CostsScreenUiState> = getCostsUseCase().map(CostsScreenUiState::Success)
        .catch { CostsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CostsScreenUiState.Loading)*/

}
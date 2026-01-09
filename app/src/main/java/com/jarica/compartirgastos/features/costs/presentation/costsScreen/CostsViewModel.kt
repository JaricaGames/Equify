package com.jarica.compartirgastos.features.costs.presentation.costsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostsByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class CostsViewModel @Inject constructor(
    getCostsByIdGroupUseCase: GetCostsByIdGroupUseCase
) : ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateCosts: StateFlow<CostsScreenUiState> = _groupId
        .filterNotNull()
        .flatMapLatest { id ->
            getCostsByIdGroupUseCase(id)
        }
        .map(CostsScreenUiState::Success)
        .catch { CostsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CostsScreenUiState.Loading)


    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }
}
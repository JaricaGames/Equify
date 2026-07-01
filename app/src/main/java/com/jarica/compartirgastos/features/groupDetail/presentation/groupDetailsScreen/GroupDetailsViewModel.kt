package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetSumCostByGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    getSumCostByGroupUseCase: GetSumCostByGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase
) : ViewModel() {


    private val _groupIdFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val sumCostByGroup: StateFlow<TotalExpensesUiState> = _groupIdFlow
        .filterNotNull()
        .flatMapLatest { id ->
            getSumCostByGroupUseCase(id)
        }
        .map(TotalExpensesUiState::Success)
        .catch { TotalExpensesUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TotalExpensesUiState.Loading)

    enum class MainTab {
        RESUME, COSTS, PAYMENTS
    }

    var selectedTab by mutableStateOf(MainTab.RESUME)
        private set

    fun onTabSelected(tab: MainTab) {
        selectedTab = tab
    }

    private val _nameOfGroup = MutableStateFlow<String>("")
    val nameOfGroup: StateFlow<String> = _nameOfGroup.asStateFlow()


    private val _isFabExpanded = MutableStateFlow(false)
    val isFabExpanded: StateFlow<Boolean> = _isFabExpanded.asStateFlow()


    fun setGroupId(id: String) {
        _groupIdFlow.value = id
    }

    fun getGroupNameById(idGroup: String?) {
        viewModelScope.launch {
            if (idGroup == null) {
                _nameOfGroup.value = "Nuevo grupo"
                return@launch
            }
            val group = getGroupByIdUseCase(idGroup)
            _nameOfGroup.value = group?.groupName ?: "Nuevo grupo"
        }
    }

    fun onFabClick() {
        _isFabExpanded.value = !_isFabExpanded.value
    }

}

package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.domain.costsUseCases.GetCostsUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.paymentUseCases.GetPaymentsUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen.CostsScreenUiState
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen.PaymentsScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    getCostsUseCase: GetCostsUseCase,
    val preferences: Preferences,
    getPaymentsUseCase: GetPaymentsUseCase

) : ViewModel() {

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _totalCost = MutableLiveData(0f)
    val totalCost: LiveData<Float> = _totalCost

    private val _isResumeSelected = MutableLiveData<Boolean>()
    val isResumeSelected: LiveData<Boolean> = _isResumeSelected

    private val _isCostsSelected = MutableLiveData<Boolean>()
    val isCostsSelected: LiveData<Boolean> = _isCostsSelected


    //------------ Variable que se usa para asber el grupo activo -------------------
    companion object {
        var iDGroupName: Int? = null
        var groupNameCompanionObject: String? = null
    }
    //----------------------------------------------------------------------------------

    val uiStateResumeGroup: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)

    val uiStateCosts: StateFlow<CostsScreenUiState> =
        getCostsUseCase().map(CostsScreenUiState::Success)
            .catch { CostsScreenUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CostsScreenUiState.Loading
            )

    val uiStatePayments : StateFlow<PaymentsScreenUiState> = getPaymentsUseCase().map(
        PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)


    fun getGroupNameById(idGroup: Int) {
        viewModelScope.launch {
            _nameOfGroup.value = getGroupByIdUseCase(idGroup).groupName
        }

    }

    fun onResumeSelected() {
        _isResumeSelected.value = true
        _isCostsSelected.value = false
    }

    fun onCostSelected() {
        _isResumeSelected.value = false
        _isCostsSelected.value = true

    }

    fun onPaymentsSelected() {
        _isResumeSelected.value = false
        _isCostsSelected.value = false
    }

    fun onCostListSelected(item: CostModel) {

    }

    fun addCostToTotal(cost: Float) {
        _totalCost.value = _totalCost.value?.plus(cost)
    }

    fun clearCosts() {
        _totalCost.value = 0f
    }
}

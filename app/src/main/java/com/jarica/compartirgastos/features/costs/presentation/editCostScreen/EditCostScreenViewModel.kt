package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.DeleteCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostByIdCost
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetDistributionPaymentsByIdCost
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.UpdateCostUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPersonByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditCostScreenViewModel @Inject constructor(
    private val deleteCostUseCase: DeleteCostUseCase,
    private val updatePersonByIdUseCase: UpdatePersonByIdUseCase,
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val getCostByIdCost: GetCostByIdCost,
    private val updateCostUseCase: UpdateCostUseCase,
    private val getPaymentsByIdCost: GetDistributionPaymentsByIdCost
) : ViewModel() {


    private val _descriptionCost = MutableLiveData<String>()
    val descriptionCost: LiveData<String> = _descriptionCost

    fun onDescriptionTextFieldChange(description: String) {
        _descriptionCost.value = description
    }

    private val _costId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateEditCost: StateFlow<EditCostUiState> =
        _costId
            .filterNotNull()
            .flatMapLatest { costId ->
                getPaymentsByIdCost(costId)
                    .map { distributionList ->
                        EditCostUiState.Success(distributionList)
                    }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                EditCostUiState.Loading
            )

    fun setIdCost(costId: String?) {
        _costId.value = costId
    }

    fun onDeletedSelected(idCost: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCostUseCase(idCost)
        }
    }

    fun updateCost(description: String, amount: Float, idCost: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val cost = getCostByIdCost(idCost)
            cost.description = description
            cost.amount      = amount
            updateCostUseCase(costModel = cost)
        }
    }

}
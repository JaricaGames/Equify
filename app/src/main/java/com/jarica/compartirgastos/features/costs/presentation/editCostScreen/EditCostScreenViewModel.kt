package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.CostOfPersonModel
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
):ViewModel() {


    private val _descriptionCost = MutableLiveData<String>()
    val descriptionCost: LiveData<String> = _descriptionCost

    private val _amountCost = MutableLiveData<Float>()
    val amountCost: LiveData<Float> = _amountCost

    private val _payFor = MutableLiveData<String>()
    val payFor: LiveData<String> = _payFor

    fun onDescriptionTextFieldChange(description: String) {
        _descriptionCost.value = description
    }

    fun onPayForTextFieldChange(payFor: String) {
        _payFor.value = payFor
    }

    fun onAmountTextFieldChange(amount: Float) {
        _amountCost.value = amount
    }

    private val _costIdFlow = MutableStateFlow<String?>(value = null)
    /*val uiEditCostUiState : StateFlow<EditCostUiState> =
        getPaymentsByIdCost().map(EditCostUiState::Success)
            .catch { EditCostUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                EditCostUiState.Loading
            )*/

/*
    val uiEditCostUiState: StateFlow<EditCostUiState> = _costIdFlow
        .filterNotNull() // <--- IMPORTANTE: Si es null, se detiene aquí y no crashea
        .flatMapLatest { idcost ->
            // Ahora 'id' es seguro (no null), llamamos al caso de uso
            getPaymentsByIdCost(idcost)
        }
        .map(EditCostUiState::Success)
        .catch { EditCostUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), EditCostUiState.Loading)
*/

    private val _costId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateEditCost: StateFlow<EditCostUiState> =
        _costId
            .filterNotNull()
            .flatMapLatest { costId ->
                getPaymentsByIdCost(costId)
                    .map {distributionList ->
                        EditCostUiState.Success(distributionList) }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                EditCostUiState.Loading
            )

    fun setIdCost(costId: String?) {
        _costId.value = costId
    }


    fun onDeletedSelected(
        idCost: String,
        listOfCostOfPerson: List<CostOfPersonModel>,
    ) {

        viewModelScope.launch(Dispatchers.IO) {


            //Metodo que actualiza los equitis de las personas que participaron en el gasto
            listOfCostOfPerson.forEach { costOfPerson->

                if(costOfPerson.iDCost == idCost) {

                    val personToUpdate = getPersonByIdUseCase(costOfPerson.iDPerson!!)
                    /*updatePersonByIdUseCase(
                        idPerson = costOfPerson.iDPerson,
                        equity = (personToUpdate.equity.toFloat().absoluteValue - costOfPerson.amount).toString()
                    )*/
                }
            }

            //Moeotos que borran los gastos
            deleteCostUseCase(idCost)
        }



/*
        viewModelScope.launch(Dispatchers.IO) {
            updateCostUseCase(CostModel(
                idCost = idCost,
                idPerson = idPerson,
                amount = amount,
                description = description,
                idGroup = iDGroupName!!,
                personString = personString
            ))
        }*/

    }

    fun updateCost( description: String, amount: Float, idCost: String) {

        viewModelScope.launch(Dispatchers.IO) {
            val cost = getCostByIdCost(idCost)
            cost.description = description
            cost.amount = amount
            //cost.personString = personString

            updateCostUseCase(costModel = cost)
        }
    }

}
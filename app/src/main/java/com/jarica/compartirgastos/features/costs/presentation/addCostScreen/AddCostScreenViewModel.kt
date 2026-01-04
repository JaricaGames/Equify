package com.jarica.compartirgastos.features.costs.presentation.addCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionPaymentUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleNamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AddCostScreenViewModel @Inject constructor(
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val insertCostUseCase: InsertCostUseCase,
    private val insertDistributionCostUseCase: InsertDistributionCostUseCase,
    private val insertDistributionPaymentUseCase: InsertDistributionPaymentUseCase,
) : ViewModel() {

    val uiAddCostsUiState: StateFlow<AddCostsUiState> =
        getPeopleNamesUseCase().map(AddCostsUiState::Success)
            .catch { AddCostsUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AddCostsUiState.Loading
            )

    //Variable texto descripcion
    private val _descriptionText = MutableLiveData<String>()
    val descriptionText: LiveData<String> = _descriptionText

    //Variable texto cantidad
    private val _amountText = MutableLiveData<String>()
    val amountText: LiveData<String> = _amountText

    //Variable cuadro seleccionar persona
    private val _isFromSelected = MutableLiveData<Boolean>()
    val isFromSelected: LiveData<Boolean> = _isFromSelected

    //Variable texto seleccionar persona
    private val _fromTextAddCost = MutableLiveData<String>()
    val fromTextAddCost: LiveData<String> = _fromTextAddCost

    //Variable texto seleccionar persona
    private val _personToAddCost = MutableLiveData<PersonModel?>()
    val personToAddCost: LiveData<PersonModel?> = _personToAddCost


    fun onDescriptionChange(descriptionText: String) {
        _descriptionText.value = descriptionText
    }

    fun onAmountChange(amount: String) {
        _amountText.value = amount

    }

    fun onFromSelected(isFromSelected: Boolean) {
        _isFromSelected.value = !isFromSelected

    }

    fun onPersonSelected(person: PersonModel) {
        _fromTextAddCost.value = person.name
        _personToAddCost.value = person
        _isFromSelected.value = false
        _personToAddCost.value = person
    }

    fun addCost(
        idGroupName: String,
        costModel: CostModel,
        listOfPeople: List<PersonModel>,
        numberOfPeople: Int,
        amount: Float,
        personToAddCosts: String,
        name: String,
        navigateToMainScreen: () -> Unit
    ) {


        viewModelScope.launch(Dispatchers.IO) {

            insertCostUseCase(
                costModel
            )
            listOfPeople.forEach { person ->
                if (person.idGroupName == idGroupName) {
                    insertDistributionCostUseCase(
                        DistributionCostModel(
                            iDCost = costModel.idCost,
                            iDPerson = person.idPerson,
                            amount = amount / numberOfPeople,
                            idGroup = idGroupName,
                            name = person.name

                        )
                    )


                }
            }
            insertDistributionPaymentUseCase(
                DistributionPaymentModel(
                    iDCost = costModel.idCost,
                    iDPerson = personToAddCosts,
                    amount = amount,
                    idGroup = idGroupName,
                    name = name
                )
            )
            withContext(Dispatchers.Main) {
                navigateToMainScreen()
            }


        }


    }

    fun calculateNumberOfPeople(listOfPeople: List<PersonModel>, idGroupName: String): Int {

        var numberOfPeople = 0
        listOfPeople.forEach { person ->
            if (person.idGroupName == idGroupName) numberOfPeople++
        }
        return numberOfPeople
    }

    fun cleanTexts() {
        viewModelScope.launch {
            delay(1000)
            _descriptionText.value = ""
            _amountText.value = ""
            _personToAddCost.value = null
            _fromTextAddCost.value = ""
        }

    }

}





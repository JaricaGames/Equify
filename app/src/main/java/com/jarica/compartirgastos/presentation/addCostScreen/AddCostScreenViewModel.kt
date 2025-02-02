package com.jarica.compartirgastos.presentation.addCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.InsertCostUseCase
import com.jarica.compartirgastos.domain.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.UpdatePersonUseCase
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.models.PersonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddCostScreenViewModel @Inject constructor(

    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val addCostUseCase: InsertCostUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase
) : ViewModel() {

    val uiAddCostsUiState: StateFlow<AddCostsUiState> =
        getPeopleNamesUseCase().map(AddCostsUiState::SuccessAddCosts)
            .catch { AddCostsUiState.ErrorAddCosts(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AddCostsUiState.LoadingAddCosts
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
    }

    fun addCostToGroup(personToAddCosts: PersonModel) {
        viewModelScope.launch(Dispatchers.IO) {
            addCostUseCase(
                CostModel(
                    idCost = null,
                    idPerson = personToAddCosts.idPerson!!,
                    amount = (amountText.value)!!.toFloat(),
                    description = descriptionText.value!!,
                    idGroup = personToAddCosts.idGroupName
                )
            )
        }


    }

    fun updatePerson(personToAddCosts: PersonModel, listOfPeople: List<PersonModel>) {

        val numberOfPeople = listOfPeople.size
        val amountByPeople = _amountText.value!!.toFloat() / numberOfPeople

        listOfPeople.forEach { person->

            //Si es el que paga se le suma al equity tod menos lo que le toca a cada uno
            if(personToAddCosts.idPerson == person.idPerson){
                val personToUpdate = person.copy(equity =  ((_amountText.value!!.toFloat() + person.equity.toFloat()-amountByPeople).toString()))
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(
                        personModel = personToUpdate
                    )
                }
            }
            //Si no es el que paga se le resta al equity lo que le toca a cada uno por pagar
            else{
                val personToUpdate =
                    person.copy(equity = (person.equity.toFloat() - amountByPeople).toString())
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(
                        personModel = personToUpdate
                    )
                }
            }
        }

        _personToAddCost.value = null
        _descriptionText.value = ""
        _amountText.value = ""

    }

}




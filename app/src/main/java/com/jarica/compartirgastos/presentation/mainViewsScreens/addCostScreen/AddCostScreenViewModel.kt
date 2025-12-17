package com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.costsUseCases.InsertCostOfPersonsUseCase
import com.jarica.compartirgastos.domain.costsUseCases.InsertCostUseCase
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    private val insertCostUseCase: InsertCostUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val insertCostOfPersonsUseCase: InsertCostOfPersonsUseCase
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

    fun addCostToGroup(
        personToAddCosts: PersonModel,
        listOfPeople: List<PersonModel>,
        iDGroupName: String?,
        amountText: String,
        descriptionText: String
    ) {

        val numberOfPeople = calculateNumberOfPeople(listOfPeople)

        viewModelScope.launch(Dispatchers.IO) {

            insertCostUseCase(
                CostModel(
                    amount = amountText.toFloat(),
                    idGroup = iDGroupName!!,
                    description = descriptionText
                )
            )

            listOfPeople.forEach { person ->
                if(person.idGroupName == iDGroupName){

                    //Calcular la cantidad del que paga
                    if(personToAddCosts.idPerson == person.idPerson){
                        /*insertCostOfPersonsUseCase(
                            CostOfPersonModel(
                                iDCostOfPerson = null,
                                iDCost = idCost,
                                iDPerson = person.idPerson,
                                amount = amountText.value!!.toFloat() - amountText.value!!.toFloat()/numberOfPeople,
                                iDGroup = iDGroupName
                            )
                        )*/
                    }else{ // calculas la cantidad del que no paga
                        /*insertCostOfPersonsUseCase(
                            CostOfPersonModel(
                                iDCostOfPerson = null,
                                iDCost = idCost,
                                iDPerson = person.idPerson,
                                amount = amountText.value!!.toFloat()/numberOfPeople,
                                iDGroup = iDGroupName
                            )
                        )*/
                    }

                }

            }

        }


    }

    fun updatePerson(personToAddCosts: PersonModel, listOfPeople: List<PersonModel>) {

        val numberOfPeople = calculateNumberOfPeople(listOfPeople)
        val amountByPeople = _amountText.value!!.toFloat() / numberOfPeople

        listOfPeople.forEach { person->

            //Si es el que paga se le suma al equity tod menos lo que le toca a cada uno
            if(personToAddCosts.idPerson == person.idPerson){

            //    val personToUpdate = person.copy(equity =  ((_amountText.value!!.toFloat() + person.equity.toFloat()-amountByPeople).toString()))


                viewModelScope.launch(Dispatchers.IO) {
                    /*updatePersonUseCase(
                        personModel = personToUpdate
                    )*/
                }
            }
            //Si no es el que paga se le resta al equity lo que le toca a cada uno por pagar
            else{
                val personToUpdate =
                   // person.copy(equity = (person.equity.toFloat() - amountByPeople).toString())
                viewModelScope.launch(Dispatchers.IO) {
                    /*updatePersonUseCase(
                        personModel = personToUpdate
                    )*/
                }
            }
        }
    }

    private fun calculateNumberOfPeople(listOfPeople: List<PersonModel>): Int {

        var numberOfPeople = 0
        listOfPeople.forEach { person->
            if (person.idGroupName == iDGroupName) numberOfPeople++
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




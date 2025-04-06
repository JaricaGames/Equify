package com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.costsUseCases.DeleteCostOfPersonUseCase
import com.jarica.compartirgastos.domain.costsUseCases.DeleteCostUseCase
import com.jarica.compartirgastos.domain.costsUseCases.GetCostOfPersonsUseCase
import com.jarica.compartirgastos.domain.models.CostOfPersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.GetPersonByIdUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue


@HiltViewModel
class EditCostScreenViewModel @Inject constructor(
    getCostOfPersonsUseCase: GetCostOfPersonsUseCase,
    private val deleteCostUseCase: DeleteCostUseCase,
    private val deleteCostOfPersonUseCase: DeleteCostOfPersonUseCase,
    private val updatePersonByIdUseCase: UpdatePersonByIdUseCase,
    private val getPersonByIdUseCase: GetPersonByIdUseCase
):ViewModel() {


    val uiEditCostUiState : StateFlow<EditCostUiState> =
        getCostOfPersonsUseCase().map(EditCostUiState::Success)
            .catch { EditCostUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                EditCostUiState.Loading
            )


    fun onDeletedSelected(
        idCost: Int,
        listOfCostOfPerson: List<CostOfPersonModel>,
    ) {

        viewModelScope.launch(Dispatchers.IO) {


            //Metodo que actualiza los equitis de las personas que participaron en el gasto
            listOfCostOfPerson.forEach { costOfPerson->

                if(costOfPerson.iDCost == idCost) {

                    val personToUpdate = getPersonByIdUseCase(costOfPerson.iDPerson!!)
                    updatePersonByIdUseCase(
                        idPerson = costOfPerson.iDPerson,
                        equity = (personToUpdate.equity.toFloat().absoluteValue - costOfPerson.amount).toString()
                    )
                }
            }

            //Moeotos que borran los gastos
            deleteCostUseCase(idCost)
            deleteCostOfPersonUseCase(idCost)
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

}
package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.domain.costsUseCases.GetCostsUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.domain.models.PaymentsModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen.CostsScreenUiState
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
class MainScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    getCostsUseCase: GetCostsUseCase,
    val preferences: Preferences

) : ViewModel() {

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _isResumeSelected = MutableLiveData<Boolean>()
    val isResumeSelected: LiveData<Boolean> = _isResumeSelected

    private val _isCostsSelected = MutableLiveData<Boolean>()
    val isCostsSelected: LiveData<Boolean> = _isCostsSelected


    //------------ Variable que se usa para asber el grupo activo -------------------
    companion object {
        var iDGroupName: Int? = null
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


    //Array de pagos
    private val arrayPaymentsModel = ArrayList<PaymentsModel>(emptyList())


    fun doTheCounts(peopleList: List<PersonModel>) {


        run personWhoPay@{
            for (personWhoPay in peopleList) {

                //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

                if (personWhoPay.equity.toFloat() < 0 && personWhoPay.idGroupName == iDGroupName) {
                    //Calculo a quien le tiene que pagar
                    run personWhoReceive@{

                        for (personWhoReceive in peopleList) {

                            //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue <= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsModel.add(
                                    PaymentsModel(
                                        amount = personWhoPay.equity.toFloat().absoluteValue.toString(),
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
                                        idPayment = TODO(),
                                        idGroup = TODO()
                                    )
                                )

                                personWhoPay.equity = "0"
                                personWhoReceive.equity =
                                    (personWhoReceive.equity.toFloat().absoluteValue - personWhoPay.equity.toFloat().absoluteValue).toString()

                                return@personWhoReceive
                            }

                            //Si lo que tiene que pagar es mayor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue >= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsModel.add(
                                    PaymentsModel(
                                        amount = personWhoReceive.equity,
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
                                        idPayment = null,
                                        idGroup = iDGroupName!!
                                    )
                                )

                                personWhoPay.equity =
                                    "-" + (personWhoPay.equity.toFloat().absoluteValue - personWhoReceive.equity.toFloat()).toString()
                                personWhoReceive.equity = "0"

                            }
                        }
                    }
                }

            }
        }

        for (persons in peopleList) {

            if (persons.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(personModel = persons.copy(equity = "0"))
                }
            }
        }

    }

    fun text() {
        for (item in arrayPaymentsModel) {
            Log.d(
                "Nono",
                item.namePersonWhoPay + " paga a " + item.namePersonWhoReceive + " la cantidad de = " + item.amount + " €"
            )
        }
    }

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


}
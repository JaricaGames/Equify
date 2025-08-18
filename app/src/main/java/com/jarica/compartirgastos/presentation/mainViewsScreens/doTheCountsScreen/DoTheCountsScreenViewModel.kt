package com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class DoTheCountsScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase
) : ViewModel() {


    private val _listOfPayments = MutableLiveData<ArrayList<PaymentsToCountsModel>>()
    val listOfPayments: LiveData<ArrayList<PaymentsToCountsModel>> = _listOfPayments

    private val _listOfPersons = MutableLiveData<List<PersonModel>>()
    //val listOfPersons: LiveData<List<PersonModel>> = _listOfPersons

    fun doTheCounts(peopleList: List<PersonModel>) {

//Array de pagos
        val arrayPaymentsToDoTheCounts = ArrayList<PaymentsToCountsModel>(emptyList())

        // Copia las personas para no modificar la lista original.
        val mutablePeopleList = peopleList.map { it.copy() }
        _listOfPersons.value = peopleList

        run personWhoPay@{
            for (personWhoPay in mutablePeopleList) {

                //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

                if (personWhoPay.equity.toFloat() < 0 && personWhoPay.idGroupName == iDGroupName) {

                    //Calculo a quien le tiene que pagar
                    run personWhoReceive@{

                        for (personWhoReceive in mutablePeopleList) {

                            //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue <= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsToDoTheCounts.add(
                                    PaymentsToCountsModel(
                                        amount = personWhoPay.equity.toFloat().absoluteValue.toString(),
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
                                    )
                                )

                                personWhoPay.equity = "0"
                                personWhoReceive.equity =
                                    (personWhoReceive.equity.toFloat().absoluteValue - personWhoPay.equity.toFloat().absoluteValue).toString()

                                return@personWhoReceive
                            }

                            //Si lo que tiene que pagar es mayor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue >= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsToDoTheCounts.add(
                                    PaymentsToCountsModel(
                                        amount = personWhoReceive.equity,
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
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

        // PONE TODOS LOS EQUITY EN 0
/*        for (persons in peopleList) {

            if (persons.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(personModel = persons.copy(equity = "0"))
                }
            }
        }*/
        for (item in arrayPaymentsToDoTheCounts) {
            Log.d(
                "Nono",
                item.namePersonWhoPay + " paga a " + item.namePersonWhoReceive + " la cantidad de = " + item.amount + " €"
            )
        }
        _listOfPayments.value = arrayPaymentsToDoTheCounts


    }

    fun putEverythingToZero(){

        _listOfPersons.value!!.forEach { person->
            if (person.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(personModel = person.copy(equity = "0"))
                }
            }
        }
    }

}


data class PaymentsToCountsModel(
    val amount: String,
    val namePersonWhoPay: String,
    val namePersonWhoReceive: String,

    )


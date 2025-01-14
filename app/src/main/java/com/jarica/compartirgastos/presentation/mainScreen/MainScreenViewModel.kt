package com.jarica.compartirgastos.presentation.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.UpdatePersonUseCase
import com.jarica.compartirgastos.domain.models.PaymentsModel
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
import kotlin.math.absoluteValue

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,

    ) : ViewModel() {

    val uiStateResumeGroup: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)


    //Array de pagos
    private val arrayPaymentsModel = ArrayList<PaymentsModel>(emptyList())


    fun doTheCounts(peopleList: List<PersonModel>) {


        run personWhoPay@{
            for (personWhoPay in peopleList) {

                //Compruebo si el equity es menor que 0, en ese caso tiene que pagar
                if (personWhoPay.equity.toFloat() < 0) {
                        //Calculo a quien le tiene que pagar
                        run personWhoReceive@{
                            for (personWhoReceive in peopleList) {

                                //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona
                                if (personWhoPay.equity.toFloat().absoluteValue <= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0) {

                                    arrayPaymentsModel.add(
                                        PaymentsModel(
                                            amount = personWhoPay.equity.toFloat().absoluteValue,
                                            namePersonWhoPay = personWhoPay.name,
                                            namePersonWhoReceive = personWhoReceive.name
                                        )
                                    )

                                    personWhoPay.equity = "0"
                                    personWhoReceive.equity = (personWhoReceive.equity.toFloat().absoluteValue - personWhoPay.equity.toFloat().absoluteValue).toString()

                                    return@personWhoReceive
                                }

                                //Si lo que tiene que pagar es mayor que lo que tiene recibe la otra persona
                                if (personWhoPay.equity.toFloat().absoluteValue >= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0) {

                                    arrayPaymentsModel.add(
                                        PaymentsModel(
                                            amount = personWhoReceive.equity.toFloat(),
                                            namePersonWhoPay = personWhoPay.name,
                                            namePersonWhoReceive = personWhoReceive.name
                                        )
                                    )

                                    personWhoPay.equity = "-"+(personWhoPay.equity.toFloat().absoluteValue - personWhoReceive.equity.toFloat()).toString()
                                    personWhoReceive.equity = "0"

                                }
                            }
                        }
                    }

            }
        }

        for (persons in peopleList) {
            viewModelScope.launch(Dispatchers.IO){
                updatePersonUseCase(personModel = persons.copy(equity = "0"))
            }
        }

        /*
                //Recorro el listado de personas
                peopleList.forEach { personWhoPay ->

                    //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

                    if (personWhoPay.equity.toFloat() < 0) {

                        //Calculo a quien le tiene que pagar
                        run personWhoReceive@{

                            peopleList.forEach { personWhoReceive ->

                                //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona

                                if (personWhoPay.equity.toFloat().absoluteValue < personWhoReceive.equity.toFloat() && personWhoReceive.equity.toFloat()>0) {
                                    arrayPaymentsModel.add(
                                        PaymentsModel(
                                            amount = personWhoPay.equity.toFloat().absoluteValue,
                                            namePersonWhoPay = personWhoPay.name,
                                            namePersonWhoReceive = personWhoReceive.name
                                        )
                                    )

                                    viewModelScope.launch(Dispatchers.IO) {
                                        updatePersonUseCase(
                                            personModel = personWhoReceive.copy(equity = (personWhoReceive.equity.toFloat() + personWhoPay.equity.toFloat()).toString())
                                        )
                                        updatePersonUseCase(
                                            personModel = personWhoPay.copy(equity = "0")
                                        )
                                    }

                                    return@personWhoReceive
                                } else {
                                    if(personWhoReceive.equity.toFloat()>0){
                                        arrayPaymentsModel.add(
                                            PaymentsModel(
                                                amount = personWhoReceive.equity.toFloat(),
                                                namePersonWhoPay = personWhoPay.name,
                                                namePersonWhoReceive = personWhoReceive.name
                                            )
                                        )

                                        viewModelScope.launch(Dispatchers.IO) {
                                            updatePersonUseCase(
                                                personModel = personWhoPay.copy(equity = (personWhoPay.equity.toFloat() + personWhoReceive.equity.toFloat()).toString())
                                            )
                                            updatePersonUseCase(
                                                personModel = personWhoReceive.copy(equity = "0")
                                            )
                                        }
                                    }

                                }

                            }
                        }
                    }
                }

        */
        /*
        //Recorro el listado de personas
        peopleList.forEach { personWhoPay ->

            //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

            if (personWhoPay.equity.toFloat() < 0) {

                //Calculo a quien le tiene que pagar
                whoDoIHaveToPay(personWhoPay, peopleList)
            }
        }
*/





    }


    private fun whoDoIHaveToPay(
        personWhoPay: PersonModel,
        peopleList: List<PersonModel>
    ) {

        peopleList.forEach { personWhoReceive ->
            if (personWhoReceive.equity.toFloat() > 0) {
                //payToSomeone(personWhoReceive, personWhoPay, peopleList)
                //Si lo que tiene que pagar es menor que lo que tiene que recibir
                if (personWhoReceive.equity.toFloat() < personWhoPay.equity.toFloat().absoluteValue) {
                    payToSomeoneToZero(personWhoReceive, personWhoPay)

                }

                //Si lo que tiene que pagar es mayor que lo que recibe
                else {
                    payToSomeoneNoZero(personWhoReceive, personWhoPay, peopleList)
                }
            }

        }
    }

    private fun payToSomeone(
        personWhoReceive: PersonModel,
        personWhoPay: PersonModel,
        peopleList: List<PersonModel>,

        ) {

        //Si lo que tiene que pagar es menor que lo que tiene que recibir
        if (personWhoReceive.equity.toFloat() < personWhoPay.equity.toFloat().absoluteValue) {
            payToSomeoneToZero(personWhoReceive, personWhoPay)
        }

        //Si lo que tiene que pagar es mayor que lo que recibe
        else {
            payToSomeoneNoZero(personWhoReceive, personWhoPay, peopleList)
        }

    }


    private fun payToSomeoneNoZero(
        personWhoReceive: PersonModel,
        personWhoPay: PersonModel,
        peopleList: List<PersonModel>,
    ) {

        arrayPaymentsModel.add(
            PaymentsModel(
                amount = personWhoReceive.equity.toFloat(),
                namePersonWhoPay = personWhoPay.name,
                namePersonWhoReceive = personWhoReceive.name
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            updatePersonUseCase(
                personModel = personWhoPay.copy(equity = (personWhoPay.equity.toFloat() + personWhoReceive.equity.toFloat()).toString())
            )
            updatePersonUseCase(
                personModel = personWhoReceive.copy(equity = "0")
            )
        }

    }


    private fun payToSomeoneToZero(personWhoReceive: PersonModel, person: PersonModel) {

        arrayPaymentsModel.add(
            PaymentsModel(
                amount = person.equity.toFloat().absoluteValue,
                namePersonWhoPay = person.name,
                namePersonWhoReceive = personWhoReceive.name
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            updatePersonUseCase(
                personModel = personWhoReceive.copy(equity = (personWhoReceive.equity.toFloat() + person.equity.toFloat()).toString())
            )
            updatePersonUseCase(
                personModel = person.copy(equity = "0")
            )
        }

    }

    fun doTheCounts2(peopleList: List<PersonModel>, personWhoPay: PersonModel) {

        val arrayOfPeople2 = peopleList

        //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

        if (personWhoPay.equity.toFloat() < 0) {

            //Calculo a quien le tiene que pagar
            run personWhoReceive2@{

                peopleList.forEach { personWhoReceive ->

                    if (personWhoPay.idPerson != personWhoReceive.idPerson) {


                        //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona

                        if (personWhoPay.equity.toFloat().absoluteValue <= personWhoReceive.equity.toFloat() && personWhoReceive.equity.toFloat() > 0) {
                            arrayPaymentsModel.add(
                                PaymentsModel(
                                    amount = personWhoPay.equity.toFloat().absoluteValue,
                                    namePersonWhoPay = personWhoPay.name,
                                    namePersonWhoReceive = personWhoReceive.name
                                )
                            )

                            viewModelScope.launch(Dispatchers.IO) {
                                updatePersonUseCase(
                                    personModel = personWhoReceive.copy(equity = (personWhoReceive.equity.toFloat() + personWhoPay.equity.toFloat()).toString())
                                )
                                updatePersonUseCase(
                                    personModel = personWhoPay.copy(equity = "0")
                                )
                            }

                            return@personWhoReceive2

                        } else {

                            arrayPaymentsModel.add(
                                PaymentsModel(
                                    amount = personWhoReceive.equity.toFloat(),
                                    namePersonWhoPay = personWhoPay.name,
                                    namePersonWhoReceive = personWhoReceive.name
                                )
                            )

                            viewModelScope.launch(Dispatchers.IO) {
                                updatePersonUseCase(
                                    personModel = personWhoPay.copy(equity = (personWhoPay.equity.toFloat() - personWhoReceive.equity.toFloat()).toString())
                                )
                                updatePersonUseCase(
                                    personModel = personWhoReceive.copy(equity = "0")
                                )
                            }
                            if (personWhoPay.equity == "0") return@personWhoReceive2
                        }

                    }
                }
            }

        }


    }

    fun text() {
        for (item in arrayPaymentsModel){
            Log.d(
                "Nono",
                item.namePersonWhoPay + " paga a " + item.namePersonWhoReceive + " la cantidad de = " + item.amount + " €"
            )
        }
    }


}
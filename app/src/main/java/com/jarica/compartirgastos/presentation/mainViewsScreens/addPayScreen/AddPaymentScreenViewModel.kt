package com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.models.PaymentsModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.paymentUseCases.InsertPaymentUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
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
class AddPaymentScreenViewModel @Inject constructor(
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val insertPaymentUseCase: InsertPaymentUseCase
    ) : ViewModel() {


    val uiAddPaymentUiState: StateFlow<AddPaymentUiState> =
        getPeopleNamesUseCase().map(AddPaymentUiState::SuccessAddPayment)
            .catch { AddPaymentUiState.ErrorAddPayment(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                AddPaymentUiState.LoadingAddPayment
            )

    //Variable texto Cantidad
    private val _amountText = MutableLiveData<String>()
    val amountText: LiveData<String> = _amountText

    //Variable texto From
    private val _personWhoPayText = MutableLiveData<String>()
    val personWhoPayText: LiveData<String> = _personWhoPayText

    //Variable texto To
    private val _personWhoReceiveText = MutableLiveData<String>()
    val personWhoReceiveText: LiveData<String> = _personWhoReceiveText

    //Variable booleana texto For
    private val _isPaidForSelected = MutableLiveData<Boolean>()
    val isPaidForSelected: LiveData<Boolean> = _isPaidForSelected

    //Variable booleana texto To
    private val _isPaidToSelected = MutableLiveData<Boolean>()
    val isPaidToSelected: LiveData<Boolean> = _isPaidToSelected

    //Variable person persona que paga
    private val _personWhoPay = MutableLiveData<PersonModel>()
    val personWhoPay: LiveData<PersonModel> = _personWhoPay

    //Variable person persona que recibe
    private val _personWhoReceive = MutableLiveData<PersonModel>()
    val personWhoReceive: LiveData<PersonModel> = _personWhoReceive


    fun onFromSelected(paidForSelected: Boolean) {
        _isPaidForSelected.value = !paidForSelected

    }

    fun onPersonWhoPaySelected(person: PersonModel) {
        _personWhoPayText.value = person.name
        _isPaidForSelected.value = false
        _personWhoPay.value = person

    }

    fun onToSelected(paidToSelected: Boolean) {
        _isPaidToSelected.value = !paidToSelected

    }

    fun onPersonWhoReceiveSelected(person: PersonModel) {
        _personWhoReceiveText.value = person.name
        _isPaidToSelected.value = false
        _personWhoReceive.value = person
    }

    fun onAmountChange(amount: String) {
        _amountText.value = amount
    }

    fun addPayment(personWhoPay: PersonModel, personWhoReceive: PersonModel, amountText: String) {

        viewModelScope.launch(Dispatchers.IO) {
            insertPaymentUseCase(paymentsModel = PaymentsModel(
                idPayment = null,
                amount = amountText,
                namePersonWhoPay = personWhoPay.name,
                namePersonWhoReceive = personWhoReceive.name,
                idGroup = personWhoPay.idGroupName
            )  )
        }


    }

    fun updatePersons(personWhoPay: PersonModel, personWhoReceive: PersonModel, amountText: String) {

        personWhoPay.equity = (personWhoPay.equity.toFloat() + amountText.toFloat()).toString()
        personWhoReceive.equity = (personWhoReceive.equity.toFloat() - amountText.toFloat()).toString()

        viewModelScope.launch(Dispatchers.IO) {
            updatePersonUseCase(personWhoReceive)
            updatePersonUseCase(personWhoPay)
        }

    }

    fun clearTexts() {
        _amountText.value = ""
        _personWhoPayText.value = ""
        _personWhoReceiveText.value = ""

    }

}
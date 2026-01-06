package com.jarica.compartirgastos.features.payments.presentation.addPayScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionPaymentUseCase
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.InsertPaymentUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPaymentScreenViewModel @Inject constructor(
    getPeopleByIdGroup: GetPeopleByIdGroupUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val insertPaymentUseCase: InsertPaymentUseCase,
    private val insertDistributionPaymentUseCase: InsertDistributionPaymentUseCase,
    private val insertDistributionCostUseCase: InsertDistributionCostUseCase
) : ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)
    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStatePeopleList: StateFlow<AddPaymentUiState> = _groupId
        .filterNotNull() // <--- IMPORTANTE: Si es null, se detiene aquí y no crashea
        .flatMapLatest { id ->
            // Ahora 'id' es seguro (no null), llamamos al caso de uso
            getPeopleByIdGroup(id)
        }
        .map(AddPaymentUiState::Success)
        .catch { AddPaymentUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddPaymentUiState.Loading)


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

    fun clearText(){
        _amountText.value = ""
        _personWhoPayText.value = ""
        _personWhoReceiveText.value = ""
    }

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
            insertPaymentUseCase(
                paymentsModel = PaymentsModel(
                    amount = amountText.toFloat(),
                    idPersonWhoPay = personWhoPay.idPerson,
                    idPersonWhoReceive = personWhoReceive.idPerson,
                    idGroup = personWhoPay.idGroupName
                )
            )

        }


    }


    fun clearTexts() {
        _amountText.value = ""
        _personWhoPayText.value = ""
        _personWhoReceiveText.value = ""

    }

}
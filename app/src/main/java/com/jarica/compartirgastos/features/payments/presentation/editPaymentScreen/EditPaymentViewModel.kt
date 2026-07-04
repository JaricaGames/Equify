package com.jarica.compartirgastos.features.payments.presentation.editPaymentScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.DeletePaymentByIdUseCase
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.GetPaymentByIdPayment
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.UpdatePaymentUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPersonByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPaymentViewModel @Inject constructor(
    private val updatePaymentUseCase: UpdatePaymentUseCase,
    private val deletePaymentUseCase: DeletePaymentByIdUseCase,
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val getPaymentByIdPayment: GetPaymentByIdPayment
): ViewModel() {

    fun onDeletedSelected(idPayment: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePaymentUseCase(idPayment)
        }
    }

    fun updatePaymentSelected(idPayment: String, amount: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            val payment = getPaymentByIdPayment(idPayment) ?: return@launch
            payment.amount = amount
            updatePaymentUseCase(payment)
        }
    }

    suspend fun getPersonName(personId: String): String? {
        return getPersonByIdUseCase(personId)?.name
    }

}


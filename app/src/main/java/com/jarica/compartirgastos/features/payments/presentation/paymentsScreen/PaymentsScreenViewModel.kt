package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import androidx.lifecycle.ViewModel
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.GetPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentsScreenViewModel @Inject constructor(
    getPaymentsUseCase: GetPaymentsUseCase
): ViewModel() {

    /*val uiStatePayments : StateFlow<PaymentsScreenUiState> = getPaymentsUseCase().map(PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)*/

}
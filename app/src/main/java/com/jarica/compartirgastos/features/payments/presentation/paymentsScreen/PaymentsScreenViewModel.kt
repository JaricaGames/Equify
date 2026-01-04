package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.GetPaymentsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PaymentsScreenViewModel @Inject constructor(
    getPaymentsUseCase: GetPaymentsUseCase
): ViewModel() {

    val uiStatePayments : StateFlow<PaymentsScreenUiState> = getPaymentsUseCase().map(
        PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)

}
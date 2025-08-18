package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen

import androidx.lifecycle.ViewModel
import com.jarica.compartirgastos.domain.paymentUseCases.GetPaymentsUseCase
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
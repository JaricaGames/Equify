package com.jarica.compartirgastos.features.payments.presentation.paymentsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.GetPaymentsByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PaymentsScreenViewModel @Inject constructor(
    getPaymentsUseCase: GetPaymentsByIdGroupUseCase
): ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)

    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStatePayments: StateFlow<PaymentsScreenUiState> = _groupId
        .filterNotNull() // <--- IMPORTANTE: Si es null, se detiene aquí y no crashea
        .flatMapLatest { id ->
            // Ahora 'id' es seguro (no null), llamamos al caso de uso
            getPaymentsUseCase(id)
        }
        .map(PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)


   /* val uiStatePayments : StateFlow<PaymentsScreenUiState> = getPaymentsUseCase().map(
        PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)*/

}
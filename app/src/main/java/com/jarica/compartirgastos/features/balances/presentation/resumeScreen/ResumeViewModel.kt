package com.jarica.compartirgastos.features.balances.presentation.resumeScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetBalancesByGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ResumeViewModel @Inject constructor(
    private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase
): ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateResumeGroup: StateFlow<ResumeUiState> =
        _groupId
            .filterNotNull()
            .flatMapLatest { groupId ->
                getBalancesByGroupUseCase(groupId)
                    .map {listaBalances ->
                        // AÑADE ESTO PARA DEPURAR
                        listaBalances.forEach {
                            Log.d("DEBUG_RESUME", "Persona: ${it.name}, Balance: ${it.balance}")
                        }
                        ResumeUiState.Success(listaBalances) }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                ResumeUiState.Loading
            )

    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }
}
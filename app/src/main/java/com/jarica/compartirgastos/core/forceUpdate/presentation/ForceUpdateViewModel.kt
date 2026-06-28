package com.jarica.compartirgastos.core.forceUpdate.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.forceUpdate.domain.CheckForcedUpdateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForceUpdateViewModel @Inject constructor(
    private val checkForcedUpdateUseCase: CheckForcedUpdateUseCase
) : ViewModel() {

    private val _forceUpdate = MutableStateFlow(false)
    val forceUpdate: StateFlow<Boolean> = _forceUpdate.asStateFlow()

    init {
        viewModelScope.launch {
            _forceUpdate.value = checkForcedUpdateUseCase()
        }
    }
}

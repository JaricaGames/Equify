package com.jarica.compartirgastos.presentation.aboutEquify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutEquifyScreenViewModel @Inject constructor(

) : ViewModel() {

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun onFeedbackClicked() {
        viewModelScope.launch {
            _event.emit(UiEvent.SendEmail)
        }
    }

    sealed class UiEvent {
        object SendEmail : UiEvent()
    }
}
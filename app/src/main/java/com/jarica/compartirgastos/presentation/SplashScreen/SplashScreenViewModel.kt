package com.jarica.compartirgastos.presentation.SplashScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(

): ViewModel() {

    private val _isSplashScreenShowed = MutableLiveData<Boolean>()
    val isSplashScreenShowed: LiveData<Boolean> = _isSplashScreenShowed

    init {
        viewModelScope.launch {
            delay(2000) // Espera 5 segundos
            _isSplashScreenShowed.value = false // Cambia el estado
        }
    }

    fun onSplashScreenShowed(){
        _isSplashScreenShowed.value = true
    }
}
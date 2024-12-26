package com.jarica.compartirgastos.presentation.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.ID_GROUP_SAVED
import com.jarica.compartirgastos.data.dataStore.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InitialScreenViewModel @Inject constructor(private val preferences: Preferences) :ViewModel(){

}

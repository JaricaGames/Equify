package com.jarica.compartirgastos.features.groups.presentation.initialScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.utils.ID_GROUP_SAVED
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainScreenViewModel.Companion.iDGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialScreenViewModel @Inject constructor(
    val preferences: Preferences
) :ViewModel(){

    init {
        viewModelScope.launch(Dispatchers.IO) {
            iDGroupName = preferences.getIdGroup(ID_GROUP_SAVED)
        }


    }
}

package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CustomizeGroupScreenViewModel @Inject constructor(

):ViewModel(){

    private val _newGroupNameToGroup = MutableLiveData<String>()
    val newGroupNameToGroup: LiveData<String> = _newGroupNameToGroup

    //Metodo del TextField
    fun onValueTextFieldChange(newGroupNameName: String) {
        _newGroupNameToGroup.value = newGroupNameName
    }

}
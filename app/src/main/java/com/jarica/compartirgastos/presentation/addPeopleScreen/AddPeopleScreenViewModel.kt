package com.jarica.compartirgastos.presentation.addPeopleScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddPeopleScreenViewModel @Inject constructor() : ViewModel() {


    private val _addNameToGroup = MutableLiveData<String>()
    val addNameToGroup: LiveData<String> = _addNameToGroup

    fun onValueTextFieldChange(personName: String) {

        _addNameToGroup.value = personName
    }

}
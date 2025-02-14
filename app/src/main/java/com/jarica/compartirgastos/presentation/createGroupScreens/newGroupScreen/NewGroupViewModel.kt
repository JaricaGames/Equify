package com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewGroupViewModel @Inject constructor(

) : ViewModel() {



    //VARIABLES DE ESTADO
    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String> = _groupName

    private val _TextNext = MutableLiveData<Boolean>()
    val textNext: LiveData<Boolean> = _TextNext

    //METODO DEL TEXTFIELD
    fun onValueTextFieldChange(text: String) {
        _groupName.value = text
        _TextNext.value = text != ""
    }

    fun onNextSelected() {

        _groupName.value = ""
    }


}

package com.jarica.compartirgastos.presentation.newGroupScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.ID_GROUP_SAVED
import com.jarica.compartirgastos.data.dataStore.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

package com.jarica.compartirgastos.features.people.presentation.addPeopleScreenFromMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.InsertPersonNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPeopleScreenFromMainViewModel @Inject constructor(
    private val insertPersonNameUseCase: InsertPersonNameUseCase,
) : ViewModel() {


    private val _addNameToGroup = MutableLiveData<String>()
    val addNameToGroup: LiveData<String> = _addNameToGroup


    //Metodo del TextField
    fun onValueTextFieldChange(personName: String) {
        _addNameToGroup.value = personName
    }


    //METODO QUE INSERTA LA LISTA DE NOMBRES EN LA BBDD, USA EL IDGRIOP QUE SE LE PASA POR PARAMETROS DE LA VISTA ANTERIOR.
    fun insertPeople(person: PersonModel) {

        viewModelScope.launch(Dispatchers.IO) {
            insertPersonNameUseCase(
                personModel = person
            )
        }
        _addNameToGroup.value = ""
    }

    fun onBackPressed() {
        _addNameToGroup.value = ""
    }
}





package com.jarica.compartirgastos.presentation.addPeopleScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.ID_GROUP_SAVED
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.domain.InsertPersonNameUseCase
import com.jarica.compartirgastos.domain.InsertGroupNameUseCase
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.domain.models.PersonModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPeopleScreenViewModel @Inject constructor(
    private val insertGroupNameUseCase: InsertGroupNameUseCase,
    private val insertPersonNameUseCase: InsertPersonNameUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _personList = mutableStateListOf<String>()
    val personList: List<String> = _personList

    private val _addNameToGroup = MutableLiveData<String>()
    val addNameToGroup: LiveData<String> = _addNameToGroup

    private val _createText = MutableLiveData<Boolean>()
    val createText: LiveData<Boolean> = _createText


    //Metodo del TextField
    fun onValueTextFieldChange(personName: String) {
        _addNameToGroup.value = personName
        _createText.value = personName != ""
    }


    //INSERTAR GRUPO EN LA BBDD
    fun insertGroupName(groupName: GroupNameModel) {
        viewModelScope.launch(Dispatchers.IO) {
            insertGroupNameUseCase(
                GroupNameModel(
                    idGroupName = groupName.idGroupName,
                    groupName = groupName.groupName
                )
            )
            //Parte del metodo que guarda el ID del grupo creado
            preferences.saveIdGroup(
                key = ID_GROUP_SAVED,
                value = groupName.idGroupName
            )
        }
    }

    // Metodo que inserta el nombre en la variable lista de nombres
    fun insertNameOnList(name: String) {
        _personList.add(name)
        _addNameToGroup.value = ""
        _createText.value = false
    }

    //METODO QUE INSERTA LA LISTA DE NOMBRES EN LA BBDD, USA EL IDGRIOP QUE SE LE PASA POR PARAMETROS DE LA VISTA ANTERIOR.
    fun  insertPeople(peopleList: List<String>, idGroupName: Int) {
        peopleList.forEach { personName->

            val personModel = PersonModel(
                idPerson = null,
                name = personName,
                equity = "0.0",
                idGroupName = idGroupName
            )
            viewModelScope.launch(Dispatchers.IO) {
                insertPersonNameUseCase(
                    personModel = personModel
                )
            }
        }
    }

}



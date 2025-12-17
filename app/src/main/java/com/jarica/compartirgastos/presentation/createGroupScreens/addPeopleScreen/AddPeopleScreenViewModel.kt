package com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.domain.groupsUseCases.InsertGroupNameUseCase
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.InsertPersonNameUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
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
            // Guardo en la variable companion de grupo el grupo activo
            iDGroupName = groupName.idGroupName
        }
    }

    // Metodo que inserta el nombre en la variable lista de nombres
    fun insertNameOnList(name: String) {
        _personList.add(name)
        _addNameToGroup.value = ""
        _createText.value = false
    }

    //METODO QUE INSERTA LA LISTA DE NOMBRES EN LA BBDD, USA EL IDGRIOP QUE SE LE PASA POR PARAMETROS DE LA VISTA ANTERIOR.

    fun insertPeople(peopleList: List<String>, idGroupName: String) {

        peopleList.forEach { personName ->

            val personModel = PersonModel(
                idPerson = personName,
                name = personName,
             //   equity = "0.0",
                idGroupName = idGroupName
            )
            viewModelScope.launch(Dispatchers.IO) {
                insertPersonNameUseCase(
                    personModel = personModel
                )
            }
        }
        _personList.clear()
    }

    fun onBackPressed() {
        _addNameToGroup.value = ""
        _personList.clear()
    }

}
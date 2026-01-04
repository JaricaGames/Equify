package com.jarica.compartirgastos.features.people.presentation.addPeopleScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.data.dataStore.Preferences
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.features.groups.domain.useCases.InsertGroupNameUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.InsertPersonNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
    fun insertGroupName(groupName: GroupModel) {
        viewModelScope.launch(Dispatchers.IO) {
            insertGroupNameUseCase(
                GroupModel(
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

    fun saveGroupData(group: GroupModel, peopleList: List<String>, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            // 1. Insertamos el grupo
            insertGroupNameUseCase(group)
            // Guardo en la variable companion de grupo el grupo activo
            iDGroupName = group.idGroupName

            // 2. Insertamos las personas
            peopleList.forEach { personName ->
                val personModel = PersonModel(
                    name = personName,
                    idGroupName = group.idGroupName
                )
                insertPersonNameUseCase(personModel = personModel)
            }
            _personList.clear()

            // 3. Solo cuando TODO ha terminado, llamamos al callback
            // Volvemos al hilo principal para la navegación
            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    fun onBackPressed() {
        _addNameToGroup.value = ""
        _personList.clear()
    }

}
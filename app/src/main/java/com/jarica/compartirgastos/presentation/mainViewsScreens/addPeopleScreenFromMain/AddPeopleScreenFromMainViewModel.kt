package com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.data.dataStore.Preferences
import com.jarica.compartirgastos.domain.groupsUseCases.InsertGroupNameUseCase
import com.jarica.compartirgastos.domain.models.GroupModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.InsertPersonNameUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPeopleScreenFromMainViewModel @Inject constructor(
    private val insertGroupNameUseCase: InsertGroupNameUseCase,
    private val insertPersonNameUseCase: InsertPersonNameUseCase,
    private val preferences: Preferences
) : ViewModel() {


    private val _addNameToGroup = MutableLiveData<String>()
    val addNameToGroup: LiveData<String> = _addNameToGroup


    //Metodo del TextField
    fun onValueTextFieldChange(personName: String) {
        _addNameToGroup.value = personName
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
            iDGroupName = groupName.idGroupName
        }
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





package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.costsUseCases.GetCostByIdPerson
import com.jarica.compartirgastos.domain.groupsUseCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.DeletePersonById
import com.jarica.compartirgastos.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationScreenViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val getCostByIdPerson: GetCostByIdPerson,
    private val deletePersonById: DeletePersonById

) : ViewModel() {

    private val _personSelected = MutableLiveData<String>()
    val personSelected: LiveData<String> = _personSelected

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _showDialogError = MutableLiveData<Boolean>()
    val showDialogError: LiveData<Boolean> = _showDialogError

    private val _showDialogConfirm = MutableLiveData<Boolean>()
    val showDialogConfirm: LiveData<Boolean> = _showDialogConfirm

    private var personSelectedModel:PersonModel? = null

    val uiStateConfigurationScreen: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)


    fun getGroupNameById(idGroup: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _nameOfGroup.postValue(getGroupByIdUseCase(idGroup).groupName)
        }
    }

    fun deleteGroup(iDGroupName: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            val groupToDelete = getGroupByIdUseCase(idGroup = iDGroupName)
            deleteGroupByIdUseCase(groupToDelete, iDGroupName)
        }
    }

    fun onGroupMemberClicked(person: PersonModel) {

        _personSelected.value = person.name
        personSelectedModel = person

        viewModelScope.launch(Dispatchers.IO) {

            val listOfCosts = getCostByIdPerson(person.idPerson!!)

            if(listOfCosts.isNotEmpty()){
                _showDialogError.postValue(true)
            }else{
                _showDialogConfirm.postValue(true)

            }
        }
    }

    fun onDismiss() {
        _showDialogError.value = false
        _showDialogConfirm.value = false
    }

    fun onConfirmDeletePerson() {

        viewModelScope.launch(Dispatchers.IO) {
            deletePersonById(personSelectedModel!!)
        }
       // deletePersonById(personSelected)
         _showDialogConfirm.postValue(false)
    }

}
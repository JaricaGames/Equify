package com.jarica.compartirgastos.features.groups.presentation.configurationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostByIdPersonUseCase
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainUiState
import com.jarica.compartirgastos.features.groups.domain.useCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.DeletePersonByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleNamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfigurationScreenViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val getCostByIdPerson: GetCostByIdPersonUseCase,
    private val deletePersonById: DeletePersonByIdUseCase

) : ViewModel() {

    private val _personSelected = MutableLiveData<String>()
    val personSelected: LiveData<String> = _personSelected

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _showDialogError = MutableLiveData<Boolean>()
    val showDialogError: LiveData<Boolean> = _showDialogError

    private val _showDialogConfirm = MutableLiveData<Boolean>()
    val showDialogConfirm: LiveData<Boolean> = _showDialogConfirm

    private var personSelectedModel: PersonModel? = null

    val uiStateConfigurationScreen: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)


    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()


    // ----------------------------------------
    // Obtener nombre del grupo
    fun getGroupNameById(idGroup: String) {
        viewModelScope.launch {
            val groupName = withContext(Dispatchers.IO) {
                getGroupByIdUseCase(idGroup).groupName
            }
            _nameOfGroup.value = groupName
        }
    }

    // ----------------------------------------
    // Borrar grupo
    fun deleteGroup(iDGroupName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val groupToDelete = getGroupByIdUseCase(iDGroupName)
            deleteGroupByIdUseCase(groupToDelete, iDGroupName)
        }
    }

    // ----------------------------------------
    // Usuario seleccionado de un grupo
    fun onGroupMemberClicked(person: PersonBalance) {
        _personSelected.value = person.name
        //personSelectedModel = person

        viewModelScope.launch {
/*            val listOfCosts = withContext(Dispatchers.IO) {
                getCostByIdPerson(person.idPerson!!)
            }*/

            // Ahora estamos en Main Thread
            /*if (listOfCosts.isNotEmpty()) {
                _showDialogError.value = true
            } else {
                _showDialogConfirm.value = true
            }*/
        }
    }

    // ----------------------------------------
    fun onDismiss() {
        _showDialogError.value = false
        _showDialogConfirm.value = false
    }

    fun clearTexts() {
        _personSelected.value = ""
        _nameOfGroup.value = ""
    }

    // ----------------------------------------
    fun onConfirmDeletePerson() {
        personSelectedModel?.let { person ->
            viewModelScope.launch(Dispatchers.IO) {
                deletePersonById(person)
            }
        }
        _showDialogConfirm.value = false
    }

    fun onFeedbackClicked() {

        viewModelScope.launch {
            _event.emit(UiEvent.SendEmail)
        }
    }

    sealed class UiEvent {
        object SendEmail : UiEvent()
    }

}

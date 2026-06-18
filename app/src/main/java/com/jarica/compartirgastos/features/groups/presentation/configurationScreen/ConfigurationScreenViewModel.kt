package com.jarica.compartirgastos.features.groups.presentation.configurationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetSumDistributionCostByIdPersonUseCase
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetSumDistributionPaymentByIdPersonUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostByIdPersonUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.DeletePersonByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ConfigurationScreenViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    getPeopleNamesUseCase: GetPeopleByIdGroupUseCase,
    private val getCostByIdPerson: GetCostByIdPersonUseCase,
    private val deletePersonById: DeletePersonByIdUseCase,
    private val getPeopleByIdGroupUseCase: GetPeopleByIdGroupUseCase,
    private val getSumDistributionCostByIdPersonUseCase: GetSumDistributionCostByIdPersonUseCase,
    private val getSumDistributionPaymentByIdPersonUseCase: GetSumDistributionPaymentByIdPersonUseCase


) : ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)
    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStatePeopleList: StateFlow<ConfigurationScreenUiState> = _groupId
        .filterNotNull() // <--- IMPORTANTE: Si es null, se detiene aquí y no crashea
        .flatMapLatest { id ->
            // Ahora 'id' es seguro (no null), llamamos al caso de uso
            getPeopleByIdGroupUseCase(id)
        }
        .map(ConfigurationScreenUiState::Success)
        .catch { ConfigurationScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConfigurationScreenUiState.Loading)

    private val _personSelected = MutableLiveData<String>()
    val personSelected: LiveData<String> = _personSelected

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _showDialogError = MutableLiveData<Boolean>()
    val showDialogError: LiveData<Boolean> = _showDialogError

    private val _showDialogConfirm = MutableLiveData<Boolean>()
    val showDialogConfirm: LiveData<Boolean> = _showDialogConfirm

    private var personSelectedModel: PersonModel? = null

    /*val uiStateConfigurationScreen: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)*/


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
    fun deleteGroup(iDGroupName: String, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val groupToDelete = getGroupByIdUseCase(iDGroupName)
            deleteGroupByIdUseCase(groupToDelete, iDGroupName)

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }

    // ----------------------------------------
    // Usuario seleccionado de un grupo
    fun onGroupMemberClicked(person: PersonModel) {
        _personSelected.value = person.name
        personSelectedModel = person

        viewModelScope.launch {
            val listOfDistributionCosts = getSumDistributionCostByIdPersonUseCase(person.idPerson).first()
            val listOfDistributionPayments = getSumDistributionPaymentByIdPersonUseCase(person.idPerson).first()

            // Ahora estamos en Main Thread
            if (listOfDistributionCosts != 0L || listOfDistributionPayments != 0L ) {
                _showDialogError.value = true
            } else {
                _showDialogConfirm.value = true
            }
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

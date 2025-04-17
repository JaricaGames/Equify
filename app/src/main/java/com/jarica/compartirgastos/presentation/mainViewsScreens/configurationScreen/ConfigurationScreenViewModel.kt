package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
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
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
) : ViewModel(){

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    val uiStateConfigurationScreen: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)


    fun getGroupNameById(idGroup: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _nameOfGroup.postValue(getGroupByIdUseCase(idGroup).groupName)
        }
    }

}
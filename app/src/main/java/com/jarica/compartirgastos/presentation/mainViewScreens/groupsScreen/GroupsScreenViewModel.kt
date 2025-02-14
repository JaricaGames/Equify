package com.jarica.compartirgastos.presentation.mainViewScreens.groupsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.GetGroupNamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupsScreenViewModel @Inject constructor(
    getGroupNamesUseCase: GetGroupNamesUseCase
): ViewModel() {


    private val _isFABSelected = MutableLiveData<Boolean>()
    val isFABSelected: LiveData<Boolean> = _isFABSelected

    val uiStateGroups: StateFlow<GroupsScreenUiState> = getGroupNamesUseCase().map(
        GroupsScreenUiState::Success
    )
        .catch { GroupsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupsScreenUiState.Loading)


    fun isFABSelected(fabSelected: Boolean) {

        _isFABSelected.value = !fabSelected
    }

    fun isNewGroupScreenSelected() {

        _isFABSelected.value = false

    }


}
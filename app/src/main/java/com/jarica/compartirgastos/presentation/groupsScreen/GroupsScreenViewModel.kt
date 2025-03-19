package com.jarica.compartirgastos.presentation.groupsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupNamesUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
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
): ViewModel(){

    fun onGroupSelected(idGroupName: Int) {
        iDGroupName = idGroupName
    }

    val uiStateGroupName: StateFlow<GroupUiState> = getGroupNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)


}
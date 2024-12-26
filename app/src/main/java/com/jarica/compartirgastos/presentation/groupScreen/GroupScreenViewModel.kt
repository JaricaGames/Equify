package com.jarica.compartirgastos.presentation.groupScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupUsesCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.newGroupNameUseCases.GetGroupNamesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GroupScreenViewModel @Inject constructor(
    getPeopleNamesUseCase: GetPeopleNamesUseCase
): ViewModel(){

    val uiStateGroupName: StateFlow<GroupUiState> = getPeopleNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)


}
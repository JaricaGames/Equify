package com.jarica.compartirgastos.presentation.groupsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupsUseCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupNamesUseCase
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.groupNameCompanionObject
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  GroupsScreenViewModel @Inject constructor(
    getGroupNamesUseCase: GetGroupNamesUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase
): ViewModel(){

    private val _isDeleteGroupClicked = MutableLiveData<Boolean>()
    val isDeleteGroupClicked: LiveData<Boolean> = _isDeleteGroupClicked

    fun onGroupSelected(idGroupName: Int, groupName: String) {
        iDGroupName = idGroupName
        groupNameCompanionObject = groupName
    }

    fun onDeletedSelected(groupNameModel: GroupNameModel, iDGroupName: Int) {
        viewModelScope.launch {
            deleteGroupByIdUseCase(groupNameModel, iDGroupName)
        }
    }

    val uiStateGroupName: StateFlow<GroupUiState> = getGroupNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)


}
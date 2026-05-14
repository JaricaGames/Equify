package com.jarica.compartirgastos.features.groups.presentation.groupsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetSumCostByGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupNamesUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupsScreenViewModel @Inject constructor(
    getGroupNamesUseCase: GetGroupNamesUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    private val getPeopleByIdGroupUseCase: GetPeopleByIdGroupUseCase,
    private val getSumCostByGroupUseCase: GetSumCostByGroupUseCase
) : ViewModel() {

    private val _isDeleteGroupClicked = MutableLiveData<Boolean>()
    val isDeleteGroupClicked: LiveData<Boolean> = _isDeleteGroupClicked

    fun onGroupSelected(idGroupName: String, groupName: String) {}

    fun onDeletedSelected(groupNameModel: GroupModel, iDGroupName: String) {
        viewModelScope.launch {
            deleteGroupByIdUseCase(groupNameModel, iDGroupName)
        }
    }

    suspend fun getPeopleCount(idGroup: String): Int =
        getPeopleByIdGroupUseCase(idGroup).first().size

    suspend fun getTotalCost(idGroup: String): Float =
        getSumCostByGroupUseCase(idGroup).first()

    val uiStateGroupName: StateFlow<GroupUiState> = getGroupNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)
}

package com.jarica.compartirgastos.features.groups.presentation.groupsScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.data.dataStore.Preferences
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetSumCostByGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.DeleteGroupByIdUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupNamesUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val REVIEW_LAUNCH_THRESHOLD = 3

@HiltViewModel
class GroupsScreenViewModel @Inject constructor(
    getGroupNamesUseCase: GetGroupNamesUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    private val getPeopleByIdGroupUseCase: GetPeopleByIdGroupUseCase,
    private val getSumCostByGroupUseCase: GetSumCostByGroupUseCase,
    private val preferences: Preferences
) : ViewModel() {

    private val _isDeleteGroupClicked = MutableLiveData<Boolean>()
    val isDeleteGroupClicked: LiveData<Boolean> = _isDeleteGroupClicked

    private val _triggerReview = MutableStateFlow(false)
    val triggerReview: StateFlow<Boolean> = _triggerReview.asStateFlow()

    fun onGroupSelected(idGroupName: String, groupName: String) {}

    fun checkAndRequestReview() {
        viewModelScope.launch {
            if (preferences.isReviewRequested()) return@launch
            val count = preferences.incrementAndGetLaunchCount()
            if (count >= REVIEW_LAUNCH_THRESHOLD) {
                _triggerReview.value = true
            }
        }
    }

    fun onReviewFlowComplete() {
        viewModelScope.launch {
            preferences.setReviewRequested()
            _triggerReview.value = false
        }
    }

    fun onDeletedSelected(groupNameModel: GroupModel, iDGroupName: String) {
        viewModelScope.launch {
            deleteGroupByIdUseCase(groupNameModel, iDGroupName)
        }
    }

    suspend fun getPeople(idGroup: String): List<PersonModel> =
        getPeopleByIdGroupUseCase(idGroup).first()

    suspend fun getTotalCost(idGroup: String): Long =
        getSumCostByGroupUseCase(idGroup).first()

    val uiStateGroupName: StateFlow<GroupUiState> = getGroupNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, GroupUiState.Loading)
}

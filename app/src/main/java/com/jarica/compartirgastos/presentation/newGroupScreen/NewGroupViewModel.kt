package com.jarica.compartirgastos.presentation.newGroupScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.GetGroupNamesUseCase
import com.jarica.compartirgastos.domain.InsertGroupNameUseCase
import com.jarica.compartirgastos.domain.model.GroupNameModel
import com.jarica.compartirgastos.presentation.groupScreen.GroupUiState
import com.jarica.compartirgastos.presentation.groupScreen.GroupUiState.Success
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
class NewGroupViewModel @Inject constructor(
    private val insertGroupNameUseCase: InsertGroupNameUseCase,
    getGroupNamesUseCase: GetGroupNamesUseCase
) : ViewModel() {

    val uiState: StateFlow<GroupUiState> = getGroupNamesUseCase().map(::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)

    private val _groupName = MutableLiveData<String>()
    val groupName: LiveData<String> = _groupName

    private val _TextNext = MutableLiveData<Boolean>()
    val textNext: LiveData<Boolean> = _TextNext

    //metodo del textfield
    fun onValueTextFieldChange(text: String) {
        _groupName.value = text
        _TextNext.value = !text.equals("")
    }

    fun insertGroupName(groupName: GroupNameModel, navigateToGroup: Unit) {
        viewModelScope.launch(Dispatchers.IO) {

            insertGroupNameUseCase(
                GroupNameModel(
                    idGroupName = groupName.idGroupName,
                    groupName = groupName.groupName
                )
            )
        }
        return navigateToGroup

    }

}
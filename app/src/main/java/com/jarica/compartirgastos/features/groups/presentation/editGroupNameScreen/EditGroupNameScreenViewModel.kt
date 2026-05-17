package com.jarica.compartirgastos.features.groups.presentation.editGroupNameScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.features.groups.domain.useCases.EditGroupNameToGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditGroupNameScreenViewModel @Inject constructor(
    private val editGroupNameToGroupUseCase: EditGroupNameToGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
):ViewModel(){

    private val _newGroupNameToGroup = MutableLiveData<String>()
    val newGroupNameToGroup: LiveData<String> = _newGroupNameToGroup

    private val _currentGroupName = MutableLiveData<String>()
    val currentGroupName: LiveData<String> = _currentGroupName

    fun onValueTextFieldChange(newGroupNameName: String) {
        _newGroupNameToGroup.value = newGroupNameName
    }

    fun clearNewName() {
        _newGroupNameToGroup.value = ""
    }

    fun loadCurrentGroupName(idGroup: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = getGroupByIdUseCase(idGroup)
            _currentGroupName.postValue(group.groupName)
        }
    }

    fun onEditGroupNameById(idGroup: String, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = getGroupByIdUseCase(idGroup)
            group.groupName = newName
            editGroupNameToGroupUseCase(group)
        }
    }

}
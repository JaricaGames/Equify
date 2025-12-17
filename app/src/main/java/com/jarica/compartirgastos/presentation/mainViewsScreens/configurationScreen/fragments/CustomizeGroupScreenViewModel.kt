package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupsUseCases.EditGroupNameToGroupUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CustomizeGroupScreenViewModel @Inject constructor(
    private val editGroupNameToGroupUseCase: EditGroupNameToGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
):ViewModel(){

    private val _newGroupNameToGroup = MutableLiveData<String>()
    val newGroupNameToGroup: LiveData<String> = _newGroupNameToGroup


    //Metodo del TextField
    fun onValueTextFieldChange(newGroupNameName: String) {
        _newGroupNameToGroup.value = newGroupNameName
    }

    fun onEditGroupNameById(idGroup: String, newName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val group = getGroupByIdUseCase(idGroup)
            group.groupName = newName
            editGroupNameToGroupUseCase(group)
        }
    }

}
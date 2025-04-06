package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.domain.groupsUseCases.GetGroupMembersById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigurationScreenViewModel @Inject constructor(
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val getGroupMembersById: GetGroupMembersById
) : ViewModel(){

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _listOfMembers = MutableLiveData<List<String>?>()
    val listOfMembers: MutableLiveData<List<String>?> = _listOfMembers

    fun getGroupMembersById(idGroup: Int): List<String>? {
        viewModelScope.launch(Dispatchers.IO) {
            _listOfMembers.value = getGroupMembersById(idGroup)
        }
        return _listOfMembers.value
    }

    fun getGroupNameById(idGroup: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _nameOfGroup.value = getGroupByIdUseCase(idGroup).groupName
        }
    }

}
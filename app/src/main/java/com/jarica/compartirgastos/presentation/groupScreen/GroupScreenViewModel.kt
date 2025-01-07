package com.jarica.compartirgastos.presentation.groupScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.domain.groupUsesCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.domain.groupUsesCases.UpdatePersonUseCase
import com.jarica.compartirgastos.domain.models.PersonModel
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
class GroupScreenViewModel @Inject constructor(
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    val updatePersonUseCase: UpdatePersonUseCase
): ViewModel(){



    val uiStateGroupName: StateFlow<GroupUiState> = getPeopleNamesUseCase().map(GroupUiState::Success)
        .catch { GroupUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), GroupUiState.Loading)


     fun updatePerson(
         listOfIdPayers: List<Int>,
         listOfAmounts: List<String>?,
         peopleList: List<PersonModel>
    ) {
         var index = -1
        listOfIdPayers.forEach { id->
            index++
            lateinit var personToUpdate: PersonModel
            peopleList.forEach { person->
                if(id == person.idPerson){

                     personToUpdate = PersonModel(
                        idPerson = person.idPerson,
                        name = person.name,
                        equity = ((person.equity.toFloat() - listOfAmounts?.get(0)!!.toFloat()).toString()),
                        idGroupName = person.idGroupName
                    )

                }
            }

            viewModelScope.launch(Dispatchers.IO) {
                updatePersonUseCase(personToUpdate)
            }
        }



    }


}
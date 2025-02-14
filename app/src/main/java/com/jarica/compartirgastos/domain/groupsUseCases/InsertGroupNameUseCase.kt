package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupNameModel
import javax.inject.Inject

class InsertGroupNameUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(groupNameModel: GroupNameModel){
        repository.insertGroupName(groupNameModel)
    }
}
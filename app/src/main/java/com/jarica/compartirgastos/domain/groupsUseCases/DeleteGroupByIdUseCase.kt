package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupNameModel
import javax.inject.Inject

class DeleteGroupByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(groupNameModel: GroupNameModel, iDGroupName: String) {
        return repository.deleteGroup(groupNameModel, iDGroupName)
    }
}
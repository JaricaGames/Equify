package com.jarica.compartirgastos.features.groups.domain.useCases


import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import javax.inject.Inject

class DeleteGroupByIdUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    suspend operator fun invoke(groupNameModel: GroupModel, iDGroupName: String) {
        return repository.deleteGroup(groupNameModel, iDGroupName)
    }
}
package com.jarica.compartirgastos.features.groups.domain.useCases


import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import javax.inject.Inject

class EditGroupNameToGroupUseCase @Inject constructor(
    val repository: GroupsRepository
) {

    suspend operator fun invoke(newGroupNameModel: GroupModel) {
        return repository.updateGroup(newGroupNameModel)
    }

}
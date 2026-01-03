package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupModel
import javax.inject.Inject

class EditGroupNameToGroupUseCase @Inject constructor(
    val repository: AppRepository
) {

    suspend operator fun invoke(newGroupNameModel: GroupModel) {
        return repository.updateGroup(newGroupNameModel)
    }

}
package com.jarica.compartirgastos.features.groups.domain.useCases

import com.jarica.compartirgastos.core.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import javax.inject.Inject

class InsertGroupNameUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    suspend operator fun invoke(groupNameModel: GroupModel) {
        repository.groupNameDao.insertGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
    }
}
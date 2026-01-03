package com.jarica.compartirgastos.features.groups.domain.useCases

import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import javax.inject.Inject

class GetGroupByIdUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    suspend operator fun invoke(idGroup: String):GroupModel{
        return repository.getGroupNameById(idGroup)
    }
}
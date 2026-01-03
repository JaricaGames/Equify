package com.jarica.compartirgastos.features.groups.domain.useCases

import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupNamesUseCase @Inject constructor(
    private val repository: GroupsRepository
) {
    operator fun invoke(): Flow<List<GroupModel>> = repository.groupNamesModel

}

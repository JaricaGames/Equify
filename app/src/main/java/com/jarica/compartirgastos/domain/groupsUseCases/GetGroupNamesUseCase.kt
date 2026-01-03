package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupNamesUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<GroupModel>> = repository.groupNamesModel

}

package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupModel
import javax.inject.Inject

class GetGroupByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idGroup: String):GroupModel{
        return repository.getGroupNameById(idGroup)
    }
}
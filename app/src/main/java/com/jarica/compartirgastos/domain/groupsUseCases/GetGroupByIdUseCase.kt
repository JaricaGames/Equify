package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.GroupNameModel
import javax.inject.Inject

class GetGroupByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idGroup: String):GroupNameModel{
        return repository.getGroupNameById(idGroup)
    }
}
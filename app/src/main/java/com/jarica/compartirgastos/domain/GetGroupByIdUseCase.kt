package com.jarica.compartirgastos.domain

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.models.GroupNameModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGroupByIdUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idGroup: Int):GroupNameModel{
        return repository.getGroupNameById(idGroup)
    }
}
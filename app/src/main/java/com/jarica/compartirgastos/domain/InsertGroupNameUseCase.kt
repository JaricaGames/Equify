package com.jarica.compartirgastos.domain

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.model.GroupNameModel
import javax.inject.Inject

class InsertGroupNameUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(groupNameModel: GroupNameModel){
        repository.insertGroupName(groupNameModel)
    }
}
package com.jarica.compartirgastos.domain.groupsUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class GetGroupMembersById @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(idGroup: Int): List<String> {
        return repository.getGroupMembersById(idGroup)
    }
}
package com.jarica.compartirgastos.features.groups.domain.useCases

import com.jarica.compartirgastos.features.groups.data.repository.GroupsRepository
import javax.inject.Inject

class GetGroupMembersById @Inject constructor(
    private val repository: GroupsRepository
) {

    suspend operator fun invoke(idGroup: String): List<String> {
        return repository.getGroupMembersById(idGroup)
    }
}
package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.people.data.PeopleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleByIdGroupUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
){
    operator fun invoke (groupId: String): Flow<List<PersonModel>> =
         peopleRepository.getPeopleByIdGroup(groupId)

}



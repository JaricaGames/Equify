package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.people.data.PeopleRepository
import javax.inject.Inject

class DeletePersonByIdUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
) {

    suspend operator fun invoke(personModel: PersonModel) {
        peopleRepository.deletePersonNameByIdPerson(personModel)
    }
}
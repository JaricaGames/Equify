package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.people.data.PeopleRepository
import javax.inject.Inject

class GetPersonByIdUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
) {
    suspend operator fun invoke(idPerson: String):PersonModel{
        return peopleRepository.getPersonById(idPerson)
    }
}
package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class DeletePersonByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(personModel: PersonModel) {
        appRepository.deletePersonNameByIdPerson(personModel)
    }
}
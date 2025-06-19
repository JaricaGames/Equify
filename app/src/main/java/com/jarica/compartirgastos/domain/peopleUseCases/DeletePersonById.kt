package com.jarica.compartirgastos.domain.peopleUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.PersonModel
import javax.inject.Inject

class DeletePersonById @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(personModel: PersonModel) {
        appRepository.deletePersonNameByIdPerson(personModel)
    }
}
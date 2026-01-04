package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.PersonModel
import javax.inject.Inject


class InsertPersonNameUseCase @Inject constructor(

    private val appRepository: AppRepository
) {

    suspend operator fun invoke(personModel: PersonModel) {
        appRepository.insertPersonName(personModel = personModel)
    }
}
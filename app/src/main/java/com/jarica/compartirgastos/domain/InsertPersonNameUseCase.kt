package com.jarica.compartirgastos.domain

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.PersonModel
import javax.inject.Inject


class InsertPersonNameUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(personModel: PersonModel) {
        appRepository.insertPersonName(personModel = personModel)
    }
}
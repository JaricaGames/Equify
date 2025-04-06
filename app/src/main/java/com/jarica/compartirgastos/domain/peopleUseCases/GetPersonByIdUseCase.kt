package com.jarica.compartirgastos.domain.peopleUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.PersonModel
import javax.inject.Inject

class GetPersonByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(idPerson:Int):PersonModel{
        return appRepository.getPersonById(idPerson)
    }
}
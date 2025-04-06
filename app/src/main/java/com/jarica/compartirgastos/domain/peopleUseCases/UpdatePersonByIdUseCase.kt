package com.jarica.compartirgastos.domain.peopleUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class UpdatePersonByIdUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(idPerson:Int, equity: String){
        appRepository.updatePersonById(idPerson, equity)
    }
}
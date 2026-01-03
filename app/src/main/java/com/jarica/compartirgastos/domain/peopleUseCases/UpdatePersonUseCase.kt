package com.jarica.compartirgastos.domain.peopleUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class UpdatePersonUseCase @Inject constructor(
    private val repository: AppRepository
) {

/*    suspend operator fun invoke(personModel: PersonModel){
        repository.updateEquity(personModel = personModel)
    }*/
}
package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import javax.inject.Inject

class UpdatePersonUseCase @Inject constructor(
    private val repository: AppRepository
) {

/*    suspend operator fun invoke(personModel: PersonModel){
        repository.updateEquity(personModel = personModel)
    }*/
}
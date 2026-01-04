package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.features.people.data.PeopleRepository
import javax.inject.Inject

class UpdatePersonUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
) {

/*    suspend operator fun invoke(personModel: PersonModel){
        peopleRepository.updateEquity(personModel = personModel)
    }*/
}
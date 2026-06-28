package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.features.people.data.PeopleRepository
import javax.inject.Inject

class UpdatePersonByIdUseCase @Inject constructor(
    private val peopleRepository: PeopleRepository
) {

/*    suspend operator fun invoke(idPerson:Int, equity: String){
        peopleRepository.updatePersonById(idPerson, equity)
    }*/
}
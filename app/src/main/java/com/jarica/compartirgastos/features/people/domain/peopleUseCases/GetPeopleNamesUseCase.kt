package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleNamesUseCase @Inject constructor(
    private val appRepository: AppRepository
){
    operator fun invoke(): Flow<List<PersonModel>> = appRepository.personModel
}
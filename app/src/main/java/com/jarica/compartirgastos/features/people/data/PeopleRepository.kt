package com.jarica.compartirgastos.features.people.data

import com.jarica.compartirgastos.core.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.core.data.database.entities.PersonEntity
import com.jarica.compartirgastos.core.data.mappers.toDomain
import com.jarica.compartirgastos.core.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PeopleRepository @Inject constructor(
    private val personNameDao: PersonNameDao,
){

     fun getPeopleByIdGroup(groupId: String): Flow<List<PersonModel>> {
        return personNameDao.getPeopleByIdGroup (groupId)
            .map { it.map { dto -> dto.toDomain() } }
    }

    suspend fun getPersonById(idPerson: String): PersonModel? {
        return personNameDao.getPersonById(idPerson)?.toDomain()
    }

    suspend fun insertPersonName(personModel: PersonModel) {
        personNameDao.insertPersonName(
            PersonEntity(
                idPerson = personModel.idPerson,
                name = personModel.name,
                idGroup = personModel.idGroupName
            )
        )

    }

    suspend fun deletePersonNameByIdPerson(personModel: PersonModel) {
        personNameDao.deletePersonNameByIdPerson(personModel.idPerson)
    }
}

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

    //Mapear de GroupEntity a GroupNameModel
    val personModel: Flow<List<PersonModel>> = personNameDao.getAllPeopleName()
        .map { items ->
            items.map {
                PersonModel(
                    it.idPerson,
                    it.name,
                    it.idGroupName
                )
            }
        }


    suspend fun getPersonById(idPerson: String): PersonModel? {
        return personNameDao.getPersonById(idPerson).toDomain()
    }

    suspend fun insertPersonName(personModel: PersonModel) {
        personNameDao.insertPersonName(
            PersonEntity(
                idPerson = personModel.idPerson,
                name = personModel.name,
                //      equity = personModel.equity,
                idGroupName = personModel.idGroupName
            )
        )

    }


    /*    suspend fun updatePersonById(idPerson: Int, equity: String) {
            personNameDao.updatePersonById(idPerson, equity)
        }*/

    suspend fun deletePersonNameByIdPerson(personModel: PersonModel) {
        personNameDao.deletePersonNameByIdPerson(personModel.idPerson)
    }
}
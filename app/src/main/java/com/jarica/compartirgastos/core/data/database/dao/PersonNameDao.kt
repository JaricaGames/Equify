package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.core.data.database.entities.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonNameDao {

    //Metodo que lista las personas
    @Query(value = "SELECT * FROM peopleTable WHERE idGroup = :idGroup ORDER BY idPerson ASC")
    fun getPeopleByIdGroup(idGroup: String): Flow<List<PersonEntity>>

    //Metodo que inserta una nueva persoina
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonName(personName: PersonEntity)

    //Metodo que borra una persona por idde grupo
    @Query(value = "DELETE FROM peopleTable WHERE idGroup like :idGroupName ")
    suspend fun deletePersonNameByIdGroup(idGroupName: String)

    //Metodo que borra una persona por id
    @Query(value = "DELETE FROM peopleTable WHERE idPerson like :idPerson ")
    suspend fun deletePersonNameByIdPerson(idPerson: String)

    //Metodo que actualiza un usuario
    @Update
    suspend fun updatePerson(personEntity: PersonEntity)

/*    //Metodo que actualiza un usuario por id
    @Query("UPDATE peopleTable SET equity=:equity WHERE idPerson like :idPerson")
    suspend fun updatePersonById(idPerson:Int, equity: String)*/

    //Metodo que devuelve un usuario por id
    @Query("SELECT * FROM peopleTable WHERE  idPerson LIKE :idPerson")
    suspend fun getPersonById(idPerson: String):PersonEntity
}
package com.jarica.compartirgastos.data.database.dao

import android.app.Person
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.data.database.entities.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonNameDao {

    //Metodo que lista las personas
    @Query("SELECT * FROM peopleTable ORDER BY idPerson ASC")
    fun getAllPeopleName(): Flow<List<PersonEntity>>

    //Metodo que inserta una nueva persoina
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPersonName(personName: PersonEntity)

    //Metodo que borra una persona
    @Delete
    suspend fun deletePersonName(personName: PersonEntity)

    //Metodo que actualiza la equity del usuario
    @Update
    suspend fun updateEquity(personEntity: PersonEntity)
}
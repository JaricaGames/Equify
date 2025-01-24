package com.jarica.compartirgastos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupNameDao {

    //Metodo que lista los grupos
    @Query("SELECT * FROM groupNameTable ORDER BY idGroupName ASC")
    fun getAllGroupName():Flow<List<GroupNameEntity>>

    //Metodo que inserta un nuevo grupo
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroupName(groupName: GroupNameEntity)

    //Metodo que borra un grupo
    @Delete
    suspend fun deleteGroupName(groupName: GroupNameEntity)

    //Metodo que devuelve un grupo por su ID
    @Query("SELECT * FROM groupNameTable WHERE idGroupName LIKE :idGroup ")
    suspend fun getGroupNameById(idGroup: Int):GroupNameEntity
}
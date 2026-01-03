package com.jarica.compartirgastos.features.groups.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GroupsDao {

    //Metodo que lista los grupos
    @Query("SELECT * FROM groupNameTable ORDER BY idGroupName ASC")
    fun getAllGroupName(): Flow<List<GroupNameEntity>>

    //Metodo que inserta un nuevo grupo
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertGroupName(groupName: GroupNameEntity)

    //Metodo que borra un grupo
    @Delete
    suspend fun deleteGroupName(groupName: GroupNameEntity)

    //Metodo que actualiza un grupo
    @Update
    suspend fun updateGroupName(groupName: GroupNameEntity)

    //Metodo que devuelve un grupo por su ID
    @Query("SELECT * FROM groupNameTable WHERE idGroupName LIKE :idGroup ")
    suspend fun getGroupNameById(idGroup: String): GroupNameEntity

    //Metodo que devuelve los miembros de de un grupo
    @Query("SELECT name FROM peopleTable WHERE idGroupName LIKE :idGroup ")
    suspend fun getGroupsMembersById(idGroup: Int):List<String>
}
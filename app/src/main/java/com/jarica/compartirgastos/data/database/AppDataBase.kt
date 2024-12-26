package com.jarica.compartirgastos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.data.database.entities.PersonEntity


@Database(
    entities = [GroupNameEntity::class, PersonEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun groupNameDao(): GroupNameDao
    abstract fun personNameDao(): PersonNameDao


}
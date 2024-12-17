package com.jarica.compartirgastos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity


@Database(
    entities = [GroupNameEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun groupNameDao(): GroupNameDao


}
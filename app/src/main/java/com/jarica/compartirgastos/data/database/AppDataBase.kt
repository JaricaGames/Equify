package com.jarica.compartirgastos.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jarica.compartirgastos.data.database.dao.CostsDao
import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.data.database.entities.CostEntity
import com.jarica.compartirgastos.data.database.entities.CostsOfPersonsEntity
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.data.database.entities.PersonEntity


@Database(
    entities = [
        GroupNameEntity::class,
        PersonEntity::class,
        CostEntity::class,
        PaymentEntity::class,
        CostsOfPersonsEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun groupNameDao(): GroupNameDao
    abstract fun personNameDao(): PersonNameDao
    abstract fun costsDao(): CostsDao
    abstract fun paymentsDao(): PaymentsDao


}
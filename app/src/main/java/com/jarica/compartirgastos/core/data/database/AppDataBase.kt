package com.jarica.compartirgastos.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jarica.compartirgastos.core.data.database.dao.CostsDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionCostDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionPaymentDao
import com.jarica.compartirgastos.core.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.core.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.core.data.database.entities.CostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionPaymentEntity
import com.jarica.compartirgastos.core.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.core.data.database.entities.PersonEntity
import com.jarica.compartirgastos.features.balances.data.dao.PersonBalanceDao
import com.jarica.compartirgastos.features.groups.data.dao.GroupsDao


@Database(
    entities = [
        GroupNameEntity::class,
        PersonEntity::class,
        CostEntity::class,
        PaymentEntity::class,
        DistributionCostEntity::class,
        DistributionPaymentEntity::class],
    version = 1,
    exportSchema = false
)

abstract class AppDataBase : RoomDatabase() {

    abstract fun groupNameDao(): GroupsDao
    abstract fun personNameDao(): PersonNameDao
    abstract fun costsDao(): CostsDao
    abstract fun paymentsDao(): PaymentsDao
    abstract fun distributionCostDao(): DistributionCostDao
    abstract fun distributionPaymentDao(): DistributionPaymentDao
    abstract fun PersonBalanceDao(): PersonBalanceDao

}




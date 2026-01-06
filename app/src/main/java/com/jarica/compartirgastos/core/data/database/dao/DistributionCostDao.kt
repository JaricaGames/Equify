package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DistributionCostDao {

    //Metodo que inserta una nueva  distribucion de gasto por persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistributionCost(distributionCost: DistributionCostEntity)

    // Metodo que devuelve la suma de la distribucion de gasto por persona
    @Query("SELECT COALESCE(SUM(amount),0) FROM distributionCostTable WHERE iDPerson = :iDPerson")
    fun getSumDistributionCostByIdPerson(iDPerson: String): Flow<Float>

}
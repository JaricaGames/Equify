package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import com.jarica.compartirgastos.features.costs.data.dto.CostPaymentDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DistributionCostDao {

    //Metodo que inserta una nueva  distribucion de gasto por persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistributionCost(distributionCost: DistributionCostEntity)

    // Metodo que devuelve la suma de la distribucion de gasto por persona
    @Query("SELECT COALESCE(SUM(amount),0) FROM distributionCostTable WHERE iDPerson = :iDPerson")
    fun getSumDistributionCostByIdPerson(iDPerson: String): Flow<Float>

    // Metodo que devuelve quienes participan en un gasto (su reparto) por Id
    @Query("""
        SELECT
            dc.idPerson AS idPerson,
            p.name AS name,
            dc.amount AS amount
        FROM distributionCostTable dc
        JOIN peopleTable p ON p.idPerson = dc.idPerson
        WHERE dc.idCost = :costId
    """)
    fun getDistributionCostByIdCost(costId: String): Flow<List<CostPaymentDto>>

    // Metodo que borra el reparto de un gasto por Id
    @Query("DELETE FROM distributionCostTable WHERE idCost = :costId")
    suspend fun deleteDistributionCostByIdCost(costId: String)

}
package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.core.data.database.entities.DistributionPaymentEntity
import com.jarica.compartirgastos.features.costs.data.dto.CostPaymentDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DistributionPaymentDao {

    //Metodo que inserta una nueva  distribucion de pago por persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDistributionPayment(distributionPayment: DistributionPaymentEntity)

    // Metodo que devuelve la suma de la distribucion de gasto por persona
    @Query("SELECT COALESCE(SUM(amount),0) FROM distributionPaymentCostTable WHERE iDPerson = :iDPerson ")
    fun getSumDistributionPaymentByIdPerson(iDPerson: String): Flow<Long>

    //Metodo que borra los pagos asociados a un gasto
    @Query("DELETE FROM distributionPaymentCostTable WHERE idCost = :idCost")
    suspend fun deleteDistributionPaymentsByIdCost(idCost: String)

    //  Metodo que devuelve quienes han pagado un gasto por Id

    @Query("""
        SELECT
            dp.idPerson AS idPerson,
            p.name AS name,
            dp.amount AS amount
        FROM distributionPaymentCostTable dp
        JOIN peopleTable p ON p.idPerson = dp.idPerson
        WHERE dp.idCost = :costId
    """)
    fun getDistributionPaymentsByIdCost(costId: String): Flow<List<CostPaymentDto>>
}

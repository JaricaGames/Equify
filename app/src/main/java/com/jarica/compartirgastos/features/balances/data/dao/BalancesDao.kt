package com.jarica.compartirgastos.features.balances.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import kotlinx.coroutines.flow.Flow

@Dao
interface BalancesDao {

    @Query("""
    SELECT
        p.idPerson AS idPerson,
        COALESCE(p.name, '') AS name,
        (
            -- 1. Balance de Gastos Compartidos (Lo que puse - Lo que consumí)
            COALESCE(c.totalCost, 0) - COALESCE(dist.totalConsumption, 0) 
            
            -- 2. Balance de Pagos Directos (Lo que envié - Lo que recibí)
            - COALESCE(sent.totalSent, 0) 
            + COALESCE(received.totalReceived, 0)
        ) AS balance
    FROM peopleTable p
    
    -- Subconsulta 1: Dinero puesto en GASTOS (Costs)
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalCost
        FROM distributionCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) c ON p.idPerson = c.idPerson
    
    -- Subconsulta 2: Dinero que DEBO de los gastos (Distributions)
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalConsumption
        FROM distributionPaymentCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) dist ON p.idPerson = dist.idPerson
    
    -- Subconsulta 3 (NUEVA): Dinero que HE PAGADO directamente a otros (PaymentEntity - idPersonWhoPay)
    LEFT JOIN (
        SELECT idPersonWhoPay, SUM(amount) AS totalSent
        FROM paymentsTable  -- Asegúrate que este nombre coincida con PAYMENTS_TABLE
        WHERE idGroup = :groupId
        GROUP BY idPersonWhoPay
    ) sent ON p.idPerson = sent.idPersonWhoPay

    -- Subconsulta 4 (NUEVA): Dinero que HE RECIBIDO de otros (PaymentEntity - idPersonWhoReceive)
    LEFT JOIN (
        SELECT idPersonWhoReceive, SUM(amount) AS totalReceived
        FROM paymentsTable -- Asegúrate que este nombre coincida con PAYMENTS_TABLE
        WHERE idGroup = :groupId
        GROUP BY idPersonWhoReceive
    ) received ON p.idPerson = received.idPersonWhoReceive

    WHERE p.idGroup = :groupId
""")
    fun getBalancesByGroup(groupId: String?): Flow<List<PersonBalance>>

}
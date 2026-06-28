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
             COALESCE(c.totalCost, 0) - COALESCE(dist.totalConsumption, 0) 
            - COALESCE(sent.totalSent, 0) 
            + COALESCE(received.totalReceived, 0)
        ) AS balance
    FROM peopleTable p
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalCost
        FROM distributionCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) c ON p.idPerson = c.idPerson
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalConsumption
        FROM distributionPaymentCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) dist ON p.idPerson = dist.idPerson
    LEFT JOIN (
        SELECT idPersonWhoPay, SUM(amount) AS totalSent
        FROM paymentsTable  
        WHERE idGroup = :groupId
        GROUP BY idPersonWhoPay
    ) sent ON p.idPerson = sent.idPersonWhoPay
    LEFT JOIN (
        SELECT idPersonWhoReceive, SUM(amount) AS totalReceived
        FROM paymentsTable
        WHERE idGroup = :groupId
        GROUP BY idPersonWhoReceive
    ) received ON p.idPerson = received.idPersonWhoReceive
    WHERE p.idGroup = :groupId
""")
    fun getBalancesByGroup(groupId: String?): Flow<List<PersonBalance>>

}
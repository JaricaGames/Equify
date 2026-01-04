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
        COALESCE(c.totalCost, 0) - COALESCE(pay.totalPayment, 0) AS balance
    FROM peopleTable p
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalCost
        FROM distributionCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) c ON p.idPerson = c.idPerson
    LEFT JOIN (
        SELECT idPerson, SUM(amount) AS totalPayment
        FROM distributionPaymentCostTable
        WHERE idGroup = :groupId
        GROUP BY idPerson
    ) pay ON p.idPerson = pay.idPerson
    WHERE p.idGroupName = :groupId
    """)
    fun getBalancesByGroup(groupId: String?): Flow<List<PersonBalance>>

}
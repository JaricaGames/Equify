package com.jarica.compartirgastos.features.balances.data.repository

import com.jarica.compartirgastos.core.data.database.dao.DistributionCostDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionPaymentDao
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.features.balances.data.dao.PersonBalanceDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalancesRepository @Inject constructor(
    private val personBalanceDao: PersonBalanceDao,
    private val distributionPaymentDao: DistributionPaymentDao,
    private val distributionCostDao: DistributionCostDao
) {

    fun getBalancesByGroup(groupId: String): Flow<List<PersonBalance>> {
        return personBalanceDao.getBalancesByGroup(groupId)
    }

    fun getSumDistributionPaymentByIdPerson(idPerson: String): Flow<Float> {
        return distributionPaymentDao.getSumDistributionPaymentByIdPerson(idPerson)
    }

    fun getSumDistributionCostByIdPerson(idPerson: String): Flow<Float> {
        return distributionCostDao.getSumDistributionCostByIdPerson(idPerson)
    }

}
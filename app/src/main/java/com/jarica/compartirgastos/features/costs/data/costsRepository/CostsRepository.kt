package com.jarica.compartirgastos.features.costs.data.costsRepository

import androidx.room.Transaction
import com.jarica.compartirgastos.core.data.database.dao.CostsDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionCostDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionPaymentDao
import com.jarica.compartirgastos.core.data.database.entities.CostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionPaymentEntity
import com.jarica.compartirgastos.core.data.mappers.toDomain
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.features.costs.data.dto.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CostsRepository @Inject constructor(
    private val costsDao: CostsDao,
    private val distributionCostDao: DistributionCostDao,
    private val distributionPaymentDao: DistributionPaymentDao
) {

    fun getCostsByGroup(groupId: String): Flow<List<CostModel>> {
        return costsDao.getAllCostsByIdGroup(groupId)
            .map { it.map { dto -> dto.toDomain() } }
    }

    fun getSumCostsByIdGroup(groupId: String): Flow<Long> {
        return costsDao.getSumCostsByIdGroup(groupId)
    }

    suspend fun insertCost(costModel: CostModel) {
        costsDao.insertCost(
            CostEntity(
                idCost = costModel.idCost,
                amount = costModel.amount,
                description = costModel.description,
                idGroup = costModel.idGroup,
            )
        )
    }

    @Transaction
    suspend fun deleteCost(idCost: String) {
        // Cascada FK elimina distributionCostTable y distributionPaymentCostTable automáticamente.
        // Transacción asegura atomicidad: todo-o-nada.
        costsDao.deleteCost(idCost = idCost)
    }


    suspend fun getCostByIdCost(id: String): CostModel? {
        return costsDao.getCostsByIdCost(id)?.toDomain()
    }

    suspend fun updateCost(costModel: CostModel) {
        costsDao.updateCost(
            costEntity = CostEntity(
                costModel.idCost,
                costModel.amount,
                costModel.description,
                costModel.idGroup,
            )
        )
    }

    suspend fun insertDistributionCost(distributionCostModel: DistributionCostModel) {
        distributionCostDao.insertDistributionCost(
            DistributionCostEntity(
                idCost = distributionCostModel.iDCost,
                idPerson = distributionCostModel.iDPerson,
                amount = distributionCostModel.amount,
                idGroup = distributionCostModel.idGroup,
            )
        )
    }


    suspend fun insertDistributionPayment(distributionPaymentModel: DistributionPaymentModel) {
        distributionPaymentDao.insertDistributionPayment(
            DistributionPaymentEntity(
                idCost = distributionPaymentModel.iDCost,
                idPerson = distributionPaymentModel.iDPerson,
                amount = distributionPaymentModel.amount,
                idGroup = distributionPaymentModel.idGroup,
            )
        )
    }

    fun getDistributionPaymentsByIdCost(idCost: String): Flow<List<CostPaymentsModel>> {
        return distributionPaymentDao.getDistributionPaymentsByIdCost(idCost)
            .map { it.map { dto -> dto.toDomain() } }
    }

    fun getDistributionCostByIdCost(idCost: String): Flow<List<CostPaymentsModel>> {
        return distributionCostDao.getDistributionCostByIdCost(idCost)
            .map { it.map { dto -> dto.toDomain() } }
    }

    @Transaction
    suspend fun deleteDistributionCostByIdCost(idCost: String) {
        distributionCostDao.deleteDistributionCostByIdCost(idCost)
    }
}

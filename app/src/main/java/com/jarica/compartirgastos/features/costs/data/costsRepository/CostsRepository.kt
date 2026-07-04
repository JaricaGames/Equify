package com.jarica.compartirgastos.features.costs.data.costsRepository

import androidx.room.withTransaction
import com.jarica.compartirgastos.core.data.database.AppDataBase
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
    private val db: AppDataBase,
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

    suspend fun deleteCost(idCost: String) {
        // Una sola sentencia DELETE: la cascada FK elimina sus distribuciones atómicamente.
        costsDao.deleteCost(idCost = idCost)
    }

    // Inserta el gasto y todas sus distribuciones en una transacción: todo-o-nada.
    suspend fun insertCostWithDistributions(
        costModel: CostModel,
        distributionCosts: List<DistributionCostModel>,
        distributionPayment: DistributionPaymentModel,
    ) {
        db.withTransaction {
            insertCost(costModel)
            distributionCosts.forEach { insertDistributionCost(it) }
            insertDistributionPayment(distributionPayment)
        }
    }

    // Actualiza el gasto y reemplaza su reparto y su pago en una transacción: todo-o-nada.
    suspend fun updateCostWithDistributions(
        costModel: CostModel,
        distributionCosts: List<DistributionCostModel>,
        distributionPayment: DistributionPaymentModel?,
    ) {
        db.withTransaction {
            updateCost(costModel)
            deleteDistributionCostByIdCost(costModel.idCost)
            distributionCosts.forEach { insertDistributionCost(it) }
            if (distributionPayment != null) {
                distributionPaymentDao.deleteDistributionPaymentsByIdCost(costModel.idCost)
                insertDistributionPayment(distributionPayment)
            }
        }
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

    suspend fun deleteDistributionCostByIdCost(idCost: String) {
        distributionCostDao.deleteDistributionCostByIdCost(idCost)
    }
}

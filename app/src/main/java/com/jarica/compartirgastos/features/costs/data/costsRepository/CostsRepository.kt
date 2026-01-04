package com.jarica.compartirgastos.features.costs.data.costsRepository

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
    //Mapear de CostEntity a CostModel
    val costModel: Flow<List<CostModel>> = costsDao.getAllCosts()
        .map { items ->
            items.map {
                CostModel(
                    it.idCost,
                    // it.idPerson,
                    it.amount,
                    it.description,
                    it.idGroup,
                    //it.personString
                )
            }
        }

    //Mapear de CostOfPersonEntity a CostOfPersonModel
    /*val costOfPersonModel: Flow<List<CostOfPersonModel>> = costsDao.getAllCostsOfPerson()
        .map { items ->
            items.map {
                CostOfPersonModel(
                 //   it.iDCostOfPerson,
                    it.iDCost,
                    it.iDPerson,
                    it.amount,
                 //   it.iDGroup
                )
            }
        }*/
    fun getTotalExpensesByGroup(groupId: String): Flow<Float> {
        return costsDao.getTotalExpensesByGroup(groupId)
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
        costsDao.deleteCost(idCost = idCost)
    }


    suspend fun getCostByIdCost(id: String): CostModel {
        return costsDao.getCostsByIdCost(id).toDomain()
    }

    suspend fun updateCost(costModel: CostModel) {
        costsDao.updateCost(
            costEntity = CostEntity(
                costModel.idCost,
                //   costModel.idPerson,
                costModel.amount,
                costModel.description,
                costModel.idGroup,
                //  costModel.personString
            )
        )
    }

    //DISTRIBUTION_COST_DAO
    //Mapear de DistributionCostEntity a DistributionCostModel

    suspend fun insertDistributionCost(distributionCostModel: DistributionCostModel) {
        distributionCostDao.insertDistributionCost(
            DistributionCostEntity(
                idCost = distributionCostModel.iDCost,
                idPerson = distributionCostModel.iDPerson,
                amount = distributionCostModel.amount,
                idGroup = distributionCostModel.idGroup,
                //name = distributionCostModel.name
            )
        )
    }

    //DISTRIBUTION_PAYMENT_DAO
    //Mapear de DistributionPaymentEntity a DistributionPaymentModel
    suspend fun insertDistributionPayment(distributionPaymentModel: DistributionPaymentModel) {
        distributionPaymentDao.insertDistributionPayment(
            DistributionPaymentEntity(
                idCost = distributionPaymentModel.iDCost,
                idPerson = distributionPaymentModel.iDPerson,
                amount = distributionPaymentModel.amount,
                idGroup = distributionPaymentModel.idGroup,
                // name = distributionPaymentModel.name
            )
        )
    }

    fun getDistributionPaymentsByIdCost(idCost: String): Flow<List<CostPaymentsModel>> {
        return distributionPaymentDao.getPaymentsByCost(idCost)
            .map { it.map { dto -> dto.toDomain() } }
    }
}
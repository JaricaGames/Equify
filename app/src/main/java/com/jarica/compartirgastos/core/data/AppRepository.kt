package com.jarica.compartirgastos.core.data

import com.jarica.compartirgastos.core.data.database.dao.CostsDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionCostDao
import com.jarica.compartirgastos.core.data.database.dao.DistributionPaymentDao
import com.jarica.compartirgastos.core.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.core.data.database.dao.PersonBalanceDao
import com.jarica.compartirgastos.core.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.core.data.database.entities.CostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionPaymentEntity
import com.jarica.compartirgastos.core.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.core.data.database.entities.PersonEntity
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.features.costs.data.dto.toDomain
import com.jarica.compartirgastos.features.groups.data.dao.GroupsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val groupNameDao: GroupsDao,
    private val personNameDao: PersonNameDao,
    private val costsDao: CostsDao,
    private val paymentsDao: PaymentsDao,
    private val distributionCostDao: DistributionCostDao,
    private val distributionPaymentDao: DistributionPaymentDao,
    private val personBalanceDao: PersonBalanceDao
) {

    /*//GROUP_NAME_DAO

    //Mapear de GroupEntity a GroupNameModel
    val groupNamesModel: Flow<List<GroupModel>> = groupNameDao.getAllGroupName()
        .map { items -> items.map { GroupModel(it.idGroupName, it.groupName) } }


    suspend fun getGroupNameById(id: String): GroupModel {
        return groupNameDao.getGroupNameById(idGroup = id).toDomain()
    }


    suspend fun getGroupMembersById(id: Int): List<String> {
        return groupNameDao.getGroupsMembersById(id)
    }

    //Metodo que actualiza algun valor del grupo (ej:nombre)
    suspend fun updateGroup(groupNameModel: GroupModel) {
        return groupNameDao.updateGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
    }


    //Metodo que borra un grupo y lo que lleva ese grupo (costos, personas, etc...)
    suspend fun deleteGroup(groupNameModel: GroupModel, iDGroupName: String) {

        groupNameDao.deleteGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
        personNameDao.deletePersonNameByIdGroup(iDGroupName)
        costsDao.deleteAllCostOfAGroup(iDGroupName)
        // costsDao.deleteCostOfPersonOfAGroup(iDGroupName)
        paymentsDao.deletePaymentsOfAGroup(iDGroupName)
    }*/

    //PERSON_NAME_DAO

    //Mapear de GroupEntity a GroupNameModel
    val personModel: Flow<List<PersonModel>> = personNameDao.getAllPeopleName()
        .map { items ->
            items.map {
                PersonModel(
                    it.idPerson,
                    it.name,
                    //   it.equity,
                    it.idGroupName
                )
            }
        }


    suspend fun getPersonById(idPerson: String): PersonModel {
        return personNameDao.getPersonById(idPerson).toDomain()
    }

    suspend fun insertPersonName(personModel: PersonModel) {
        personNameDao.insertPersonName(
            PersonEntity(
                idPerson = personModel.idPerson,
                name = personModel.name,
                //      equity = personModel.equity,
                idGroupName = personModel.idGroupName
            )
        )

    }


    /*    suspend fun updatePersonById(idPerson: Int, equity: String) {
            personNameDao.updatePersonById(idPerson, equity)
        }*/

    suspend fun deletePersonNameByIdPerson(personModel: PersonModel) {
        personNameDao.deletePersonNameByIdPerson(personModel.idPerson)
    }


    //COST_DAO
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
                //idPerson = costModel.idPerson,
                amount = costModel.amount,
                description = costModel.description,
                idGroup = costModel.idGroup,
                // personString = costModel.personString
            )
        )
    }

    suspend fun deleteCost(idCost: String) {
        costsDao.deleteCost(idCost = idCost)
    }

    /*    suspend fun deleteCostOfPerson(idCost: String) {
            costsDao.deleteCostOfPerson(idCost = idCost)
        }*/

    /*suspend fun insertCostOfPerson(costModelOfPerson: CostOfPersonModel) {

        costsDao.insertCostOfPerson(
            DistributionCost(
               // iDCostOfPerson = null,
                iDCost = costModelOfPerson.iDCost,
                iDPerson = costModelOfPerson.iDPerson,
                amount = costModelOfPerson.amount,
              //  iDGroup = costModelOfPerson.iDGroup
            )
        )
    }*/

/*        suspend fun getCostsById(id: String): List<DistributionCost> {
            return costsDao.getCostsById(id)
        }*/

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

    //PERSONBALANCE_DAO

    fun getBalancesByGroup(groupId: String): Flow<List<PersonBalance>> {
        return personBalanceDao.getBalancesByGroup(groupId)
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

    fun getSumDistributionCostByIdPerson(idPerson: String): Flow<Float> {
        return distributionCostDao.getSumDistributionCostByIdPerson(idPerson)
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

    fun getSumDistributionPaymentByIdPerson(idPerson: String): Flow<Float> {
        return distributionPaymentDao.getSumDistributionPaymentByIdPerson(idPerson)
    }

    fun getPaymentsByIdCost(idCost: String): Flow<List<CostPaymentsModel>> {
        return distributionPaymentDao.getPaymentsByCost(idCost)
            .map{ it.map { dto -> dto.toDomain() } }

    }

//PAYMENTS:DAO
//Mapear de PaymentEntity a PaymentModel

    val paymentsModel: Flow<List<PaymentsModel>> = paymentsDao.getAllPayments()
        .map { items ->
            items.map {
                PaymentsModel(
                    idPayment = it.idPayment,
                    amount = it.amount,
                    idPersonWhoPay = it.idPersonWhoPay,
                    idPersonWhoReceive = it.idPersonWhoReceive,
                    idGroup = it.idGroup
                )
            }
        }

    /*suspend fun insertPayment(paymentsModel: PaymentsModel) {
        paymentsDao.insertPayment(
            PaymentEntity(
                idPayment = it.idPayment,
                amount = TODO(),
                idPersonWhoPay = TODO(),
                idPersonWhoReceive = TODO(),
                idGroup = TODO()
            )
        )
    }*/


}


fun GroupNameEntity.toDomain(): GroupModel {
    return GroupModel(this.idGroupName, this.groupName)
}

fun CostEntity.toDomain(): CostModel {
    return CostModel(
        idCost = this.idCost,
        // idPerson = this.idPerson,
        amount = this.amount,
        description = this.description,
        idGroup = this.idGroup,
        //personString = this.personString
    )
}


fun PersonEntity.toDomain(): PersonModel {
    return PersonModel(
        idPerson = this.idPerson,
        name = this.name,
        // equity = this.equity,
        idGroupName = this.idGroupName
    )
}


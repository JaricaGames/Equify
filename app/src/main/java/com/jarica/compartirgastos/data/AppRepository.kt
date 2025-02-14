package com.jarica.compartirgastos.data

import com.jarica.compartirgastos.data.database.dao.CostsDao
import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.data.database.entities.CostEntity
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.data.database.entities.PersonEntity
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.domain.models.PaymentsModel
import com.jarica.compartirgastos.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val groupNameDao: GroupNameDao,
    private val personNameDao: PersonNameDao,
    private val costsDao: CostsDao,
    private val paymentsDao: PaymentsDao
) {

    //GROUP_NAME_DAO

    //Mapear de GroupEntity a GroupNameModel
    val groupNamesModel: Flow<List<GroupNameModel>> = groupNameDao.getAllGroupName()
        .map { items -> items.map { GroupNameModel(it.idGroupName, it.groupName) } }


    suspend fun insertGroupName(groupNameModel: GroupNameModel) {
        groupNameDao.insertGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )

    }

    suspend fun getGroupNameById(id: Int): GroupNameModel {
        return groupNameDao.getGroupNameById(idGroup = id).toDomain()
    }

    //PERSON_NAME_DAO

    //Mapear de GroupEntuty a GroupNameModel
    val personModel: Flow<List<PersonModel>> = personNameDao.getAllPeopleName()
        .map { items -> items.map { PersonModel(it.idPerson, it.name, it.equity, it.idGroupName) } }


    suspend fun insertPersonName(personModel: PersonModel) {
        personNameDao.insertPersonName(
            PersonEntity(
                idPerson = null,
                name = personModel.name,
                equity = personModel.equity,
                idGroupName = personModel.idGroupName
            )
        )

    }

    suspend fun updateEquity(personModel: PersonModel) {
        personNameDao.updateEquity(
            PersonEntity(
                idPerson = personModel.idPerson,
                name = personModel.name,
                equity = personModel.equity,
                idGroupName = personModel.idGroupName
            )
        )
    }


    //COST_DAO
    //Mapear de CostEntity a CostModel
    val costModel: Flow<List<CostModel>> = costsDao.getAllCosts()
        .map { items ->
            items.map {
                CostModel(
                    it.idCost,
                    it.idPerson,
                    it.amount,
                    it.description,
                    it.idGroup
                )
            }
        }


    suspend fun insertCost(costModel: CostModel) {
        costsDao.insertCost(
            CostEntity(
                idCost = null,
                idPerson = costModel.idPerson,
                amount = costModel.amount,
                description = costModel.description,
                idGroup = costModel.idGroup,
            )
        )


    }


//PAYMENTS:DAO
//Mapear de PaymentEntity a PaymentModel

    val paymentsModel: Flow<List<PaymentsModel>> = paymentsDao.getAllPayments()
        .map { items ->
            items.map {
                PaymentsModel(
                    it.idPayment,
                    it.amount,
                    it.namePersonWhoPay,
                    it.namePersonWhoReceive,
                    it.idGroup
                )
            }
        }

    suspend fun insertPayment(paymentsModel: PaymentsModel){
        paymentsDao.insertPayment(
            PaymentEntity(
                idPayment = null,
                amount = paymentsModel.amount,
                namePersonWhoPay = paymentsModel.namePersonWhoPay,
                namePersonWhoReceive = paymentsModel.namePersonWhoReceive,
                idGroup = paymentsModel.idGroup
            )
        )
    }

}


fun GroupNameEntity.toDomain(): GroupNameModel {
    return GroupNameModel(this.idGroupName, this.groupName)
}


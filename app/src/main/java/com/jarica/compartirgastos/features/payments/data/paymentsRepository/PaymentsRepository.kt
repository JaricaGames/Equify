package com.jarica.compartirgastos.features.payments.data.paymentsRepository

import com.jarica.compartirgastos.core.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.core.data.mappers.toDomain
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentsRepository @Inject constructor(
    private val paymentsDao: PaymentsDao)
{


    fun getPaymentsByIdGroup(groupId: String): Flow<List<PaymentsModel>> {
        return paymentsDao.getPaymentsByIdGroup (groupId)
            .map { it.map { dto -> dto.toDomain() } }
    }

    suspend fun getPaymentByIdPayment(idPayment: String): PaymentsModel {
        return paymentsDao.getPaymentByIdPayment(idPayment).toDomain()
    }


    suspend fun insertPayment(paymentsModel: PaymentsModel) {
        paymentsDao.insertPayment(
            PaymentEntity(
                idPayment = paymentsModel.idPayment,
                amount = paymentsModel.amount,
                idPersonWhoPay = paymentsModel.idPersonWhoPay,
                idPersonWhoReceive = paymentsModel.idPersonWhoReceive,
                idGroup = paymentsModel.idGroup
            )
        )
    }

    suspend fun deletePaymentById(idPayment: String){
        paymentsDao.deletePaymentById(idPayment)
    }

    suspend fun updatePaymentById(paymentModel: PaymentsModel){
        paymentsDao.updatePaymentById(
            PaymentEntity(
                paymentModel.idPayment,
                paymentModel.amount,
                paymentModel.idPersonWhoPay,
                paymentModel.idPersonWhoReceive,
                paymentModel.idGroup
            )
        )
    }


}
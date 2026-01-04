package com.jarica.compartirgastos.features.payments.data.paymentsRepository

import com.jarica.compartirgastos.core.data.database.dao.PaymentsDao
import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PaymentsRepository @Inject constructor(
    private val paymentsDao: PaymentsDao)
{

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
}
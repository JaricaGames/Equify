package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDao {

    //Metodo que lista los pagos
    @Query(value = "SELECT * FROM paymentsTable WHERE idGroup = :idGroup")
    fun getPaymentsByIdGroup(idGroup: String): Flow<List<PaymentEntity>>

    //Metodo que inserta una nuevo pago
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    //Metodo que borra un pago
    @Delete
    suspend fun deletePayment(payment: PaymentEntity)

    //Metodo que borra los pagos de un grupo
    @Query("DELETE FROM paymentsTable WHERE idGroup LIKE :idGroup ")
    suspend fun deletePaymentsOfAGroup(idGroup: String)

}
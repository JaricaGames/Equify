package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDao {

    //Metodo que lista los pagos
    @Query(value = "SELECT * FROM paymentsTable WHERE idGroup = :idGroup")
    fun getPaymentsByIdGroup(idGroup: String): Flow<List<PaymentEntity>>

    // Metodo que devuelve un gasto por ID
    @Query("SELECT * FROM paymentsTable WHERE idPayment LIKE :idPayment ")
    suspend fun getPaymentByIdPayment(idPayment: String): PaymentEntity

    //Metodo que inserta una nuevo pago
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    @Query("DELETE FROM paymentsTable WHERE idPayment LIKE :idPayment ")
    suspend fun deletePaymentById(idPayment: String)

    //Metodo que actualiza un pago
    @Update
    suspend fun updatePaymentById(payment: PaymentEntity)

    //Metodo que borra los pagos de un grupo
    @Query("DELETE FROM paymentsTable WHERE idGroup LIKE :idGroup ")
    suspend fun deletePaymentsOfAGroup(idGroup: String)



}
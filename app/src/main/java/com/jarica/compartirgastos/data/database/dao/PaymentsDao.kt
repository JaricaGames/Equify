package com.jarica.compartirgastos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jarica.compartirgastos.data.database.entities.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentsDao {

    //Metodo que lista los pagos
    @Query(value = "SELECT * FROM paymentsTable")
    fun getAllPayments(): Flow<List<PaymentEntity>>

    //Metodo que inserta una nuevo pago
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    //Metodo que borra un pago
    @Delete
    suspend fun deletePayment(payment: PaymentEntity)

}
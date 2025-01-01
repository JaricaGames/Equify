package com.jarica.compartirgastos.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.data.database.entities.CostEntity
import com.jarica.compartirgastos.data.database.entities.PersonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CostsDao {


    //Metodo que lista los gastos
    @Query("SELECT * FROM costsTable ORDER BY iDCost ASC")
    fun getAllCosts(): Flow<List<CostEntity>>

    //Metodo que inserta un nuevo gasto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCost(cost: CostEntity)

    //Metodo que borra un gasto
    @Delete
    suspend fun deleteCost(cost: CostEntity)

}
package com.jarica.compartirgastos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.data.database.entities.CostEntity
import com.jarica.compartirgastos.data.database.entities.CostsOfPersonsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CostsDao {


    //Metodo que lista los gastos
    @Query("SELECT * FROM costsTable ORDER BY iDCost ASC")
    fun getAllCosts(): Flow<List<CostEntity>>

   // Metodo que lista los gastos por persona
    @Query("SELECT * FROM CostsOfPersonTable ORDER BY iDCost ASC")
    fun getAllCostsOfPerson(): Flow<List<CostsOfPersonsEntity>>

   // Metodo que devuelve todos los gastos de una persona
    @Query("SELECT * FROM CostsOfPersonTable WHERE iDPerson LIKE :idPerson ")
    suspend fun getCostsById(idPerson: Int): List<CostsOfPersonsEntity>

   // Metodo que devuelve un gasto por ID
    @Query("SELECT * FROM costsTable WHERE iDCost LIKE :idCost ")
    suspend fun getCostsByIdCost(idCost: Int): CostEntity

    //Metodo que inserta un nuevo gasto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCost(cost: CostEntity)

    //Metodo que inserta un nuevo gasto por persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCostOfPerson(cost: CostsOfPersonsEntity)

    //Metodo que borra un gasto por Id
    @Query("DELETE FROM costsTable WHERE idCost LIKE :idCost ")
    suspend fun deleteCost(idCost:Int)

    //Metodo que borra los gastos de un grupo
    @Query("DELETE FROM costsTable WHERE idGroup LIKE :idGroup ")
    suspend fun deleteAllCostOfAGroup(idGroup:Int)

    //Metodo que borra todos los gasto por persona con un IdCost
    @Query("DELETE FROM CostsOfPersonTable WHERE idCost LIKE :idCost ")
    suspend fun deleteCostOfPerson(idCost: Int)

    //Metodo que borra todos los gasto por persona de un grupo
    @Query("DELETE FROM CostsOfPersonTable WHERE idGroup LIKE :idGroup ")
    suspend fun deleteCostOfPersonOfAGroup(idGroup: Int)

    //Metodo que actualiza un gasto
    @Update
    suspend fun updateCost(costEntity: CostEntity)

    //Metodo que actualiza un gasto por persona
    @Update
    suspend fun updateCostOfPerson(costsOfPersonsEntity: CostsOfPersonsEntity)

}
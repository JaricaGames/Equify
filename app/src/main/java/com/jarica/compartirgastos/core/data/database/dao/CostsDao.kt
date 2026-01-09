package com.jarica.compartirgastos.core.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.core.data.database.entities.CostEntity
import com.jarica.compartirgastos.core.data.database.entities.DistributionCostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CostsDao {


    //Metodo que lista los gastos
    @Query("SELECT * FROM costsTable WHERE idGroup = :idGroup ORDER BY iDCost ASC")
    fun getAllCostsByIdGroup(idGroup: String): Flow<List<CostEntity>>

   // Metodo que devuelve un gasto por ID
    @Query("SELECT * FROM costsTable WHERE iDCost LIKE :idCost ")
    suspend fun getCostsByIdCost(idCost: String): CostEntity

    //Metodo que inserta un nuevo gasto
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCost(cost: CostEntity)

    //Metodo que inserta un nuevo gasto por persona
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCostOfPerson(cost: DistributionCostEntity)

    //Metodo que borra un gasto por Id
    @Query("DELETE FROM costsTable WHERE idCost LIKE :idCost ")
    suspend fun deleteCost(idCost: String)

    //Metodo que borra los gastos de un grupo
    @Query("DELETE FROM costsTable WHERE idGroup LIKE :idGroup ")
    suspend fun deleteAllCostOfAGroup(idGroup: String)

    //Metodo que actualiza un gasto
    @Update
    suspend fun updateCost(costEntity: CostEntity)

    //Metodo que devuelve la suma de todos los gastos de un grupo
    @Query("""SELECT COALESCE(SUM(amount), 0) FROM costsTable WHERE idGroup = :groupId""")
    fun getSumCostsByIdGroup(groupId: String): Flow<Float>


}
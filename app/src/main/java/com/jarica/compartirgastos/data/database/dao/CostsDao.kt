package com.jarica.compartirgastos.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.jarica.compartirgastos.data.database.entities.CostEntity
import com.jarica.compartirgastos.data.database.entities.DistributionCostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CostsDao {


    //Metodo que lista los gastos
    @Query("SELECT * FROM costsTable ORDER BY iDCost ASC")
    fun getAllCosts(): Flow<List<CostEntity>>

/*   // Metodo que lista los gastos por persona
    @Query("SELECT * FROM CostsOfPersonTable ORDER BY iDCost ASC")
    fun getAllCostsOfPerson(): Flow<List<DistributionCost>>*/

   // Metodo que devuelve todos los gastos de una persona
/*    @Query("SELECT * FROM CostsOfPersonTable WHERE iDPerson LIKE :idPerson ")
    suspend fun getCostsById(idPerson: String): List<DistributionCost>*/

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

    //Metodo que borra todos los gasto por persona con un IdCost
  /*  @Query("DELETE FROM CostsOfPersonTable WHERE idCost LIKE :idCost ")
    suspend fun deleteCostOfPerson(idCost: String)*/

    //Metodo que borra todos los gasto por persona de un grupo
/*    @Query("DELETE FROM CostsOfPersonTable WHERE idGroup LIKE :idGroup ")
    suspend fun deleteCostOfPersonOfAGroup(idGroup: String)*/

    //Metodo que actualiza un gasto
    @Update
    suspend fun updateCost(costEntity: CostEntity)

    //Metodo que actualiza un gasto por persona
    @Update
    suspend fun updateCostOfPerson(costsOfPersonsEntity: DistributionCostEntity)

    //Metodo que devuelve la suma de todos los gastos de un grupo
    @Query("""SELECT COALESCE(SUM(amount), 0) FROM costsTable WHERE idGroup = :groupId""")
    fun getTotalExpensesByGroup(groupId: String): Flow<Float>


}
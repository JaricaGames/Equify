package com.jarica.compartirgastos.domain.distributionCostUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostPaymentsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentsByIdCost @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(idCost: String): Flow<List<CostPaymentsModel>> {
        return repository.getPaymentsByIdCost(idCost)
    }
}
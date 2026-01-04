package com.jarica.compartirgastos.features.costs.domain.distributionCostUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentsByIdCost @Inject constructor(
    private val repository: AppRepository
){
    operator fun invoke(idCost: String): Flow<List<CostPaymentsModel>> {
        return repository.getPaymentsByIdCost(idCost)
    }
}
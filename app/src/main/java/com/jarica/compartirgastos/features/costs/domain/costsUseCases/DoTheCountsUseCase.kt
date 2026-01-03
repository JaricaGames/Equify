package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import javax.inject.Inject

class DoTheCountsUseCase @Inject constructor(
    //private val appRepository: AppRepository,
    //private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase
){

    operator fun invoke(
        balances: List<PersonBalance>
    ): List<PaymentsToDoCountsModel> {

        val debtors = balances
            .filter { it.balance > 0 }
            .map { it.copy(balance = it.balance) }
            .toMutableList()

        val creditors = balances
            .filter { it.balance < 0 }
            .map { it.copy(balance = -it.balance) }
            .toMutableList()

        val payments = mutableListOf<PaymentsToDoCountsModel>()

        var i = 0
        var j = 0

        while (i < debtors.size && j < creditors.size) {

            val debtor = debtors[i]
            val creditor = creditors[j]

            val amount = minOf(debtor.balance, creditor.balance)

            payments.add(
                PaymentsToDoCountsModel(
                    idPersonWhoPay = debtor.idPerson,
                    idPersonWhoReceive = creditor.idPerson,
                    amount = amount,
                    namePersonWhoPay = debtor.name,
                    namePersonWhoReceive = creditor.name
                )
            )

            debtors[i] = debtor.copy(balance = debtor.balance - amount)
            creditors[j] = creditor.copy(balance = creditor.balance - amount)

            if (debtors[i].balance == 0f) i++
            if (creditors[j].balance == 0f) j++
        }

        return payments
    }
}

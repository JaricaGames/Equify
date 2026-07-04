package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.utils.splitEvenly
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.DeleteCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostByIdCost
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetDistributionCostByIdCost
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetDistributionPaymentsByIdCost
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.UpdateCostWithDistributionsUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPersonByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class EditCostScreenViewModel @Inject constructor(
    private val deleteCostUseCase: DeleteCostUseCase,
    private val updatePersonByIdUseCase: UpdatePersonByIdUseCase,
    private val getPersonByIdUseCase: GetPersonByIdUseCase,
    private val getCostByIdCost: GetCostByIdCost,
    private val updateCostWithDistributionsUseCase: UpdateCostWithDistributionsUseCase,
    private val getPaymentsByIdCost: GetDistributionPaymentsByIdCost,
    private val getPeopleByIdGroupUseCase: GetPeopleByIdGroupUseCase,
    private val getDistributionCostByIdCost: GetDistributionCostByIdCost,
) : ViewModel() {


    private val _descriptionCost = MutableLiveData<String>()
    val descriptionCost: LiveData<String> = _descriptionCost

    fun onDescriptionTextFieldChange(description: String) {
        _descriptionCost.value = description
    }

    private val _costId = MutableStateFlow<String?>(null)

    // Ids de los miembros que participan en el gasto. null = se usan los participantes actuales.
    private val _selectedParticipantIds = MutableStateFlow<Set<String>?>(null)
    val selectedParticipantIds: StateFlow<Set<String>?> = _selectedParticipantIds

    // Id del miembro que pagó el gasto. null = se mantiene el pagador actual.
    private val _selectedPayerId = MutableStateFlow<String?>(null)
    val selectedPayerId: StateFlow<String?> = _selectedPayerId

    fun onPayerSelected(person: PersonModel) {
        _selectedPayerId.value = person.idPerson
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiStateEditCost: StateFlow<EditCostUiState> =
        _costId
            .filterNotNull()
            .flatMapLatest { costId ->
                val costOrNull = getCostByIdCost(costId)
                if (costOrNull == null) {
                    kotlinx.coroutines.flow.flowOf(EditCostUiState.Loading)
                } else {
                    val idGroup = costOrNull.idGroup.orEmpty()
                    combine(
                        getPaymentsByIdCost(costId),
                        getDistributionCostByIdCost(costId),
                        getPeopleByIdGroupUseCase(idGroup)
                    ) { payers, participants, groupPeople ->
                        EditCostUiState.Success(
                            listOfCostPaymentsModel = payers,
                            groupPeople             = groupPeople,
                            currentParticipantIds   = participants.map { it.idPerson }.toSet()
                        )
                    }
                }
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                EditCostUiState.Loading
            )

    fun setIdCost(costId: String?) {
        _costId.value = costId
    }

    fun onParticipantToggled(person: PersonModel, currentIds: Set<String>) {
        val base = _selectedParticipantIds.value ?: currentIds
        val updated = if (person.idPerson in base) base - person.idPerson else base + person.idPerson
        _selectedParticipantIds.value = updated
    }

    fun onDeletedSelected(idCost: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCostUseCase(idCost)
        }
    }

    fun updateCost(
        description: String,
        amount: Long,
        idCost: String,
        participants: List<PersonModel>,
        payer: PersonModel?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val cost = getCostByIdCost(idCost) ?: return@launch
            cost.description = description
            cost.amount      = amount
            val idGroup = cost.idGroup.orEmpty()

            // Recalcula el reparto entre los participantes seleccionados.
            val distributionCosts = if (participants.isEmpty()) emptyList() else {
                val shares = splitEvenly(amount, participants.size)
                participants.mapIndexed { index, person ->
                    DistributionCostModel(
                        iDCost   = idCost,
                        iDPerson = person.idPerson,
                        amount   = shares[index],
                        idGroup  = idGroup,
                        name     = person.name
                    )
                }
            }

            // Reemplaza el pago con el nuevo importe y pagador. null = deja el pago como está.
            val payment = payer?.let {
                DistributionPaymentModel(
                    iDCost   = idCost,
                    iDPerson = it.idPerson,
                    amount   = amount,
                    idGroup  = idGroup,
                    name     = it.name
                )
            }

            updateCostWithDistributionsUseCase(
                costModel           = cost,
                distributionCosts   = distributionCosts,
                distributionPayment = payment
            )
        }
    }

}

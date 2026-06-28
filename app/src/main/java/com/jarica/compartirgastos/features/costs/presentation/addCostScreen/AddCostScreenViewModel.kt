package com.jarica.compartirgastos.features.costs.presentation.addCostScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.utils.splitEvenly
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionCostUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.InsertDistributionPaymentUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleByIdGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class AddCostScreenViewModel @Inject constructor(
    private val getPeopleByIdGroupUseCase: GetPeopleByIdGroupUseCase,
    private val insertCostUseCase: InsertCostUseCase,
    private val insertDistributionCostUseCase: InsertDistributionCostUseCase,
    private val insertDistributionPaymentUseCase: InsertDistributionPaymentUseCase,
) : ViewModel() {

    private val _groupId = MutableStateFlow<String?>(null)
    fun setGroup(groupId: String?) {
        _groupId.value = groupId
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiAddCostsUiState: StateFlow<AddCostsUiState> = _groupId
        .filterNotNull()
        .flatMapLatest { id -> getPeopleByIdGroupUseCase(id) }
        .map(AddCostsUiState::Success)
        .catch { AddCostsUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), AddCostsUiState.Loading)

    private val _descriptionText = MutableStateFlow("")
    val descriptionText: StateFlow<String> = _descriptionText

    private val _amountText = MutableStateFlow("")
    val amountText: StateFlow<String> = _amountText

    private val _isFromSelected = MutableStateFlow(false)
    val isFromSelected: StateFlow<Boolean> = _isFromSelected

    private val _fromTextAddCost = MutableStateFlow("")
    val fromTextAddCost: StateFlow<String> = _fromTextAddCost

    private val _personToAddCost = MutableStateFlow<PersonModel?>(null)
    val personToAddCost: StateFlow<PersonModel?> = _personToAddCost

    // Ids de los miembros que participan en el gasto. null = todos los del grupo.
    private val _selectedParticipantIds = MutableStateFlow<Set<String>?>(null)
    val selectedParticipantIds: StateFlow<Set<String>?> = _selectedParticipantIds

    fun onParticipantToggled(person: PersonModel, allPeople: List<PersonModel>) {
        val current = _selectedParticipantIds.value ?: allPeople.map { it.idPerson }.toSet()
        val updated = if (person.idPerson in current) current - person.idPerson else current + person.idPerson
        _selectedParticipantIds.value = updated
    }

    fun onDescriptionChange(descriptionText: String) {
        _descriptionText.value = descriptionText
    }

    fun onAmountChange(amount: String) {
        _amountText.value = amount
    }

    fun onFromSelected(isFromSelected: Boolean) {
        _isFromSelected.value = !isFromSelected
    }

    fun onPersonSelected(person: PersonModel) {
        _fromTextAddCost.value = person.name
        _personToAddCost.value = person
        _isFromSelected.value = false
    }

    fun addCost(
        idGroupName: String,
        costModel: CostModel,
        listOfPeople: List<PersonModel>,
        amount: Long,
        personToAddCosts: String,
        name: String,
        navigateToMainScreen: () -> Unit
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            insertCostUseCase(costModel)
            val shares = splitEvenly(amount, listOfPeople.size)
            listOfPeople.forEachIndexed { index, person ->
                insertDistributionCostUseCase(
                    DistributionCostModel(
                        iDCost = costModel.idCost,
                        iDPerson = person.idPerson,
                        amount = shares[index],
                        idGroup = idGroupName,
                        name = person.name
                    )
                )
            }
            insertDistributionPaymentUseCase(
                DistributionPaymentModel(
                    iDCost = costModel.idCost,
                    iDPerson = personToAddCosts,
                    amount = amount,
                    idGroup = idGroupName,
                    name = name
                )
            )
            withContext(Dispatchers.Main) {
                cleanTexts()
                navigateToMainScreen()
            }
        }
    }

    private fun cleanTexts() {
        _descriptionText.value = ""
        _amountText.value = ""
        _personToAddCost.value = null
        _fromTextAddCost.value = ""
        _selectedParticipantIds.value = null
    }
}

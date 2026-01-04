package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import android.app.Activity
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.DoTheCountsUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetBalancesByGroupUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostsUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetSumCostByGroupUseCase
import com.jarica.compartirgastos.features.costs.domain.distributionCostUseCases.GetSumDistributionCostByIdPersonUseCase
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostsScreenUiState
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.features.payments.domain.distributionPaymentsUseCases.GetSumDistributionPaymentByIdPersonUseCase
import com.jarica.compartirgastos.features.payments.domain.paymentUseCases.GetPaymentsUseCase
import com.jarica.compartirgastos.features.payments.presentation.paymentsScreen.PaymentsScreenUiState
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.GetPeopleNamesUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    getSumCostByGroupUseCase: GetSumCostByGroupUseCase,
    private val getSumDistributionCostByIdPersonUseCase: GetSumDistributionCostByIdPersonUseCase,
    private val getSumDistributionPaymentByIdPersonUseCase: GetSumDistributionPaymentByIdPersonUseCase,
    private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase,
    private val doTheCountsUseCase: DoTheCountsUseCase,
    private val updatePersonUseCase: UpdatePersonUseCase,
    getPeopleNamesUseCase: GetPeopleNamesUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    getCostsUseCase: GetCostsUseCase,
    //val preferences: Preferences,
    getPaymentsUseCase: GetPaymentsUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {


    private val _groupIdFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val SumCostByGroup: StateFlow<TotalExpensesUiState> = _groupIdFlow
        .filterNotNull() // <--- IMPORTANTE: Si es null, se detiene aquí y no crashea
        .flatMapLatest { id ->
            // Ahora 'id' es seguro (no null), llamamos al caso de uso
            getSumCostByGroupUseCase(id)
        }
            .map(TotalExpensesUiState::Success)
            .catch { TotalExpensesUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TotalExpensesUiState.Loading)


    enum class MainTab {
        RESUME, COSTS, PAYMENTS}

    var selectedTab by mutableStateOf(MainTab.RESUME)
        private set

    fun onTabSelected(tab: MainTab) {
        selectedTab = tab
    }

    private val _paymentsToDoTheCounts = MutableStateFlow<List<PaymentsToDoCountsModel>>(emptyList())
    val paymentsToDoTheCounts: StateFlow<List<PaymentsToDoCountsModel>> = _paymentsToDoTheCounts

    private val _isFabExpanded = MutableLiveData<Boolean>(false)
    val isFabExpanded: LiveData<Boolean> = _isFabExpanded


    // ------------------- ADMOB -------------------------

    //lateinit var person: String
    private var interstitialAd: InterstitialAd? = null
    private val _isAdLoaded = MutableStateFlow(false)
    val isAdLoaded: StateFlow<Boolean> = _isAdLoaded

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-4979320410432560/2157781438", // <-- ID de prueba
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    _isAdLoaded.value = true
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    _isAdLoaded.value = false
                }
            }
        )
    }



    fun showAdThenNavigate(activity: Activity, onNavigate: () -> Unit) {
        interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                _isAdLoaded.value = false
                loadAd() // recargar para el próximo
                onNavigate()
            }
        }
        if (interstitialAd != null) {
            interstitialAd?.show(activity)
        } else {
            onNavigate()
        }
    }


    // ------------------- ADMOB -------------------------

    private val _nameOfGroup = MutableLiveData<String>()
    val nameOfGroup: LiveData<String> = _nameOfGroup

    private val _totalCost = MutableLiveData(0f)
    val totalCost: LiveData<Float> = _totalCost


    //------------ Variable que se usa para asber el grupo activo -------------------
    companion object {
        var iDGroupName: String? = null
        var groupNameCompanionObject: String? = null
    }
    //----------------------------------------------------------------------------------

    val uiStateResumeGroup: StateFlow<MainUiState> =
        getPeopleNamesUseCase().map(MainUiState::Success)
            .catch { MainUiState.Error(it) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUiState.Loading)

    val uiStateCosts: StateFlow<CostsScreenUiState> =
        getCostsUseCase().map(CostsScreenUiState::Success)
            .catch { CostsScreenUiState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                CostsScreenUiState.Loading
            )

    val uiStatePayments : StateFlow<PaymentsScreenUiState> = getPaymentsUseCase().map(
        PaymentsScreenUiState::Success)
        .catch { PaymentsScreenUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PaymentsScreenUiState.Loading)

    fun setGroupId(id: String) {
        iDGroupName = id
        _groupIdFlow.value = id
        getGroupNameById(id)
    }

    fun getGroupNameById(idGroup: String) {
        viewModelScope.launch {
            _nameOfGroup.value = getGroupByIdUseCase(idGroup).groupName
        }
    }

    fun addCostToTotal(cost: Float) {
        _totalCost.value = _totalCost.value?.plus(cost)
    }

    fun clearCosts() {
        _totalCost.value = 0f
    }

    fun onDoTheCountsClicked() {

        var balances: List<PersonBalance>
        var result: List<PaymentsToDoCountsModel> = emptyList()

        viewModelScope.launch {

            balances = getBalancesByGroupUseCase(iDGroupName!!).first()
            result = doTheCountsUseCase(balances)
            result.forEach {
                Log.d("MainScreenViewModel", "onDoTheCountsClicked: ${it.idPersonWhoPay +"le paga " +  it.amount +" a "+ it.idPersonWhoReceive}")
            }
            _paymentsToDoTheCounts.value = result
        }


    }

    fun onFabClick() {
        _isFabExpanded.value = !_isFabExpanded.value!!
    }

    fun closeFab() {
        _isFabExpanded.value = false
    }
}

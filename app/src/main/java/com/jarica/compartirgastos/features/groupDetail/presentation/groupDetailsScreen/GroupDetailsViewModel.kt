package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetSumCostByGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailsViewModel @Inject constructor(
    getSumCostByGroupUseCase: GetSumCostByGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    @param:ApplicationContext private val context: Context
) : ViewModel() {


    private val _groupIdFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val sumCostByGroup: StateFlow<TotalExpensesUiState> = _groupIdFlow
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

    private val _nameOfGroup = MutableStateFlow<String>("")
    val nameOfGroup: StateFlow<String> = _nameOfGroup.asStateFlow()


    private val _isFabExpanded = MutableStateFlow(false)
    val isFabExpanded: StateFlow<Boolean> = _isFabExpanded.asStateFlow()


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


    fun setGroupId(id: String) {
      //  iDGroupName = id
        _groupIdFlow.value = id
        getGroupNameById(id)
    }

    fun getGroupNameById(idGroup: String) {
        viewModelScope.launch {
            _nameOfGroup.value = getGroupByIdUseCase(idGroup).groupName
        }
    }

    fun onFabClick() {
        _isFabExpanded.value = !_isFabExpanded.value!!
    }

}

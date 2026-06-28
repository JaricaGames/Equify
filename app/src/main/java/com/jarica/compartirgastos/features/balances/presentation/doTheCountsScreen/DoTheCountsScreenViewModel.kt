package com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.billing.BillingManager
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.utils.CountsPdfGenerator
import com.jarica.compartirgastos.core.utils.InterstitialAdController
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.DoTheCountsUseCase
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetBalancesByGroupUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostsByIdGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoTheCountsScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase,
    private val doTheCountsUseCase: DoTheCountsUseCase,
    private val getCostsByIdGroupUseCase: GetCostsByIdGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val billingManager: BillingManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _paymentsState = MutableStateFlow<List<PaymentsToDoCountsModel>>(emptyList())
    val paymentsState: StateFlow<List<PaymentsToDoCountsModel>> = _paymentsState

    private val _costsState = MutableStateFlow<List<CostModel>>(emptyList())

    private val _groupName = MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    private val _pdfReady = MutableStateFlow<Uri?>(null)
    val pdfReady: StateFlow<Uri?> = _pdfReady

    private val _launchPicker = MutableStateFlow(false)
    val launchPicker: StateFlow<Boolean> = _launchPicker

    fun calculatePayments(groupId: String?) {
        viewModelScope.launch {
            try {
                val balances = getBalancesByGroupUseCase(groupId).first()
                _paymentsState.value = doTheCountsUseCase(balances)

                if (groupId != null) {
                    _costsState.value = getCostsByIdGroupUseCase(groupId).first()
                    _groupName.value = getGroupByIdUseCase(groupId).groupName
                }
            } catch (e: Exception) {
                _paymentsState.value = emptyList()
            }
        }
    }

    private val interstitial = InterstitialAdController(context) { billingManager.adsRemoved.value }

    fun loadAd() = interstitial.load()

    fun showAdThenLaunchPicker(activity: Activity) {
        interstitial.showThen(activity) { _launchPicker.value = true }
    }

    fun onPickerLaunched() {
        _launchPicker.value = false
    }

    fun createPdf(contentResolver: ContentResolver, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val generator = CountsPdfGenerator(
                    context = context,
                    groupName = _groupName.value,
                    costs = _costsState.value,
                    payments = _paymentsState.value,
                )
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    generator.writeTo(outputStream)
                }
                _pdfReady.value = uri
            } catch (_: Exception) { }
        }
    }

    fun onPdfOpened() {
        _pdfReady.value = null
    }
}

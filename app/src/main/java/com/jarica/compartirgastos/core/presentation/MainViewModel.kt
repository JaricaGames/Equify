package com.jarica.compartirgastos.core.presentation

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.billing.BillingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel de ámbito de la navegación. Expone el estado de la compra de
 * "quitar anuncios" para proveerlo vía [LocalAdsRemoved] a toda la app.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val billingManager: BillingManager
) : ViewModel() {

    val adsRemoved: StateFlow<Boolean> = billingManager.adsRemoved

    fun purchaseRemoveAds(activity: Activity) = billingManager.launchPurchase(activity)

    fun restorePurchases() {
        viewModelScope.launch { billingManager.refreshPurchases() }
    }
}

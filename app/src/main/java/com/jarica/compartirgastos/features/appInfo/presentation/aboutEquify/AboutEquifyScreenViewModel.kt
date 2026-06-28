package com.jarica.compartirgastos.features.appInfo.presentation.aboutEquify

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jarica.compartirgastos.core.billing.BillingManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AboutEquifyScreenViewModel @Inject constructor(
    private val billingManager: BillingManager
) : ViewModel() {

    private val _event = MutableSharedFlow<UiEvent>()
    val event = _event.asSharedFlow()

    fun onFeedbackClicked() {
        viewModelScope.launch {
            _event.emit(UiEvent.SendEmail)
        }
    }

    /** Lanza el flujo de compra de Google Play para quitar los anuncios. */
    fun onRemoveAdsClicked(activity: Activity) = billingManager.launchPurchase(activity)

    /** Vuelve a comprobar compras previas (restaurar tras reinstalar/cambiar de móvil). */
    fun onRestorePurchasesClicked() {
        viewModelScope.launch { billingManager.refreshPurchases() }
    }

    sealed class UiEvent {
        object SendEmail : UiEvent()
    }
}
package com.jarica.compartirgastos.core.billing

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.acknowledgePurchase
import com.android.billingclient.api.queryProductDetails
import com.android.billingclient.api.queryPurchasesAsync
import com.jarica.compartirgastos.core.data.dataStore.Preferences
import com.jarica.compartirgastos.core.utils.REMOVE_ADS_PRODUCT_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Gestiona la compra única "quitar anuncios" a través de Google Play Billing.
 *
 * - [adsRemoved] es la única fuente que debe consultar la UI para saber si hay que
 *   mostrar anuncios. Se inicializa con la caché local (DataStore) para no parpadear
 *   al arrancar y luego se refresca contra Google Play.
 * - La compra solo funciona con la app publicada (al menos en un canal de pruebas)
 *   y con cuentas de prueba (license testers) configuradas en Play Console.
 */
@Singleton
class BillingManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val preferences: Preferences,
) : PurchasesUpdatedListener {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _adsRemoved = MutableStateFlow(false)
    val adsRemoved: StateFlow<Boolean> = _adsRemoved.asStateFlow()

    private val billingClient: BillingClient = BillingClient.newBuilder(context)
        .setListener(this)
        .enablePendingPurchases(
            PendingPurchasesParams.newBuilder()
                .enableOneTimeProducts()
                .build()
        )
        .build()

    init {
        scope.launch {
            // 1) Caché local primero (instantáneo).
            _adsRemoved.value = preferences.isAdsRemoved()
            // 2) Verdad de Google Play.
            refreshPurchases()
        }
    }

    /** Lanza el flujo de compra de Google. La pantalla de pago la pinta Google. */
    fun launchPurchase(activity: Activity) {
        scope.launch {
            if (!ensureReady()) return@launch
            val product = QueryProductDetailsParams.Product.newBuilder()
                .setProductId(REMOVE_ADS_PRODUCT_ID)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
            val params = QueryProductDetailsParams.newBuilder()
                .setProductList(listOf(product))
                .build()

            val details = billingClient.queryProductDetails(params)
                .productDetailsList
                ?.firstOrNull() ?: return@launch

            val flowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(
                    listOf(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(details)
                            .build()
                    )
                )
                .build()

            withContext(Dispatchers.Main) {
                billingClient.launchBillingFlow(activity, flowParams)
            }
        }
    }

    /**
     * Vuelve a comprobar las compras del usuario en Google Play (también sirve como
     * "restaurar compra" tras reinstalar o cambiar de móvil).
     */
    suspend fun refreshPurchases() {
        if (!ensureReady()) return
        val params = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.INAPP)
            .build()
        val purchases = billingClient.queryPurchasesAsync(params).purchasesList

        purchases
            .filter {
                it.purchaseState == Purchase.PurchaseState.PURCHASED &&
                    it.products.contains(REMOVE_ADS_PRODUCT_ID)
            }
            .forEach { if (!it.isAcknowledged) acknowledge(it) }

        val owned = purchases.any {
            it.purchaseState == Purchase.PurchaseState.PURCHASED &&
                it.products.contains(REMOVE_ADS_PRODUCT_ID)
        }
        updateAdsRemoved(owned)
    }

    override fun onPurchasesUpdated(result: BillingResult, purchases: MutableList<Purchase>?) {
        if (result.responseCode != BillingClient.BillingResponseCode.OK || purchases == null) return
        scope.launch {
            purchases
                .filter {
                    it.purchaseState == Purchase.PurchaseState.PURCHASED &&
                        it.products.contains(REMOVE_ADS_PRODUCT_ID)
                }
                .forEach { purchase ->
                    if (!purchase.isAcknowledged) acknowledge(purchase)
                    updateAdsRemoved(true)
                }
        }
    }

    private suspend fun acknowledge(purchase: Purchase) {
        val params = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(params)
    }

    private suspend fun updateAdsRemoved(value: Boolean) {
        if (_adsRemoved.value != value) {
            preferences.setAdsRemoved(value)
            _adsRemoved.value = value
        }
    }

    /** Garantiza que el cliente está conectado antes de cualquier consulta. */
    private suspend fun ensureReady(): Boolean {
        if (billingClient.isReady) return true
        return suspendCancellableCoroutine { cont ->
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingSetupFinished(result: BillingResult) {
                    if (cont.isActive) {
                        cont.resume(result.responseCode == BillingClient.BillingResponseCode.OK)
                    }
                }

                override fun onBillingServiceDisconnected() {
                    if (cont.isActive) cont.resume(false)
                }
            })
        }
    }
}

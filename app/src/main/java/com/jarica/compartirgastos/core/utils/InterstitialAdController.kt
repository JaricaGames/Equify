package com.jarica.compartirgastos.core.utils

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Lógica reutilizable para cargar y mostrar un anuncio intersticial de AdMob.
 *
 * Centraliza el boilerplate que antes estaba duplicado en varios ViewModels:
 * carga el anuncio, lo muestra y, al cerrarse (o si falla al mostrarse),
 * ejecuta la acción pendiente y precarga el siguiente.
 *
 * Si la publicidad está desactivada (el usuario compró "quitar anuncios") o el
 * anuncio no está listo, la acción se ejecuta igualmente para no bloquear al usuario.
 *
 * @param adsRemoved devuelve si el usuario ha comprado quitar los anuncios.
 */
class InterstitialAdController(
    private val context: Context,
    private val adsRemoved: () -> Boolean,
) {

    private var interstitialAd: InterstitialAd? = null

    fun load() {
        if (adsRemoved()) return
        InterstitialAd.load(
            context,
            AdIds.interstitial,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    /**
     * Muestra el intersticial si está disponible y ejecuta [onDone] cuando se
     * cierra. Si no hay anuncio (o la publicidad está desactivada), ejecuta
     * [onDone] de inmediato. [onDone] se invoca siempre exactamente una vez.
     */
    fun showThen(activity: Activity, onDone: () -> Unit) {
        val ad = interstitialAd
        if (adsRemoved() || ad == null) {
            onDone()
            return
        }
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                load() // precargar para la próxima vez
                onDone()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                interstitialAd = null
                load()
                onDone()
            }
        }
        ad.show(activity)
    }
}

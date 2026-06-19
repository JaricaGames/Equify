package com.jarica.compartirgastos.core.presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.compartirgastos.BuildConfig
import com.jarica.compartirgastos.core.utils.AdIds

@Composable
fun BannerAdView(modifier: Modifier = Modifier) {
    if (!BuildConfig.SHOW_ADS) return
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = AdIds.banner
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

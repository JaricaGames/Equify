package com.jarica.compartirgastos.core.di

import android.content.Context
import com.google.android.gms.ads.MobileAds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdsModule {

    @Provides
    @Singleton
    fun provideMobileAdsInitializer(
        @ApplicationContext context: Context
    ): MobileAdsInitializer {
        MobileAds.initialize(context)
        return MobileAdsInitializer()
    }
}

class MobileAdsInitializer
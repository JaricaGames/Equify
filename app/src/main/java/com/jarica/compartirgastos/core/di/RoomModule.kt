package com.jarica.compartirgastos.core.di

import android.content.Context
import androidx.room.Room
import com.jarica.compartirgastos.core.data.database.AppDataBase
import com.jarica.compartirgastos.core.data.database.MIGRATION_1_2
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val QUOTE_DATABASE_NAME = "AppDataBase"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDataBase::class.java, QUOTE_DATABASE_NAME)
            .addMigrations(MIGRATION_1_2)
            // v3 cambia los importes de REAL (Float) a INTEGER (céntimos). En desarrollo
            // recreamos la BD local en vez de migrar los datos antiguos.
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideGroupNameDao(db:AppDataBase) = db.groupNameDao()

    @Provides
    fun providePersonNameDao(db:AppDataBase) = db.personNameDao()

    @Provides
    fun provideCostsDao(db:AppDataBase) = db.costsDao()

    @Provides
    fun providePaymentsDao(db:AppDataBase) = db.paymentsDao()

    @Provides
    fun provideDistributionPaymentsDao(db:AppDataBase) = db.distributionPaymentDao()

    @Provides
    fun provideDistributionCostDao(db:AppDataBase) = db.distributionCostDao()

    @Provides
    fun personBalanceDao(db:AppDataBase) = db.balanceDao()


}
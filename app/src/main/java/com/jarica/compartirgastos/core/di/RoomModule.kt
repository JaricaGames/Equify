package com.jarica.compartirgastos.core.di

import android.content.Context
import androidx.room.Room
import com.jarica.compartirgastos.core.data.database.ALL_MIGRATIONS
import com.jarica.compartirgastos.core.data.database.AppDataBase
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
            // Migraciones reales que conservan los datos del usuario (ver Migrations.kt).
            // Ahora la lista está vacía: la versión 3 es la línea base previa a publicar.
            .addMigrations(*ALL_MIGRATIONS)
            // ⚠️ PRE-PUBLICACIÓN: recreamos la BD local cuando cambia el esquema (no hay
            // usuarios con datos todavía). En cuanto publiques la v1, ELIMINA esta línea
            // —o cámbiala por fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)—
            // y a partir de ahí cada cambio de esquema debe tener su Migration en ALL_MIGRATIONS.
            // Si la dejas, los usuarios PERDERÍAN sus datos al actualizar.
            .fallbackToDestructiveMigration(dropAllTables = true)
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
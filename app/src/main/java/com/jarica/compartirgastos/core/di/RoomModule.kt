package com.jarica.compartirgastos.core.di

import android.content.Context
import androidx.room.Room
import com.jarica.compartirgastos.data.database.AppDataBase
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
        Room.databaseBuilder(context, AppDataBase::class.java, QUOTE_DATABASE_NAME).build()


    @Provides
    fun provideGroupNameDao(db:AppDataBase) = db.groupNameDao()

    @Provides
    fun providePersonNameDao(db:AppDataBase) = db.personNameDao()


}
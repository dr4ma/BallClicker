package com.bclccasino.ballclicker.di

import android.content.Context
import com.bclccasino.ballclicker.data.utils.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RealmModule {

    private val realmVersion = 1L

    @Singleton
    @Provides
    fun providesRealmConfig(@ApplicationContext context: Context) : Realm {
        Realm.init(context)
        val realmConfig = RealmConfiguration.Builder()
            .name(DATABASE_NAME)
            .schemaVersion(realmVersion)
            .build()
        Realm.setDefaultConfiguration(realmConfig)
        return Realm.getDefaultInstance()
    }
}
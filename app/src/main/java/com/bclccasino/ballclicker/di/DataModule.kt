package com.bclccasino.ballclicker.di

import com.bclccasino.ballclicker.data.realm.ResultsRequests
import com.bclccasino.ballclicker.domain.repository.ResultsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideResultsRequests(realm: Realm): ResultsRepository {
        return ResultsRequests(realm = realm)
    }
}
package com.bclccasino.ballclicker.di

import com.bclccasino.ballclicker.presentation.fragments.result.ResultsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun provideResultsAdapter() : ResultsAdapter {
        return ResultsAdapter()
    }
}
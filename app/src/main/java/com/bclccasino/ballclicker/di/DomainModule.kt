package com.bclccasino.ballclicker.di

import com.bclccasino.ballclicker.domain.objects.Ball
import com.bclccasino.ballclicker.domain.repository.ResultsRepository
import com.bclccasino.ballclicker.domain.usecases.ResultsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideBall(): Ball {
        return Ball()
    }

    @Provides
    fun provideResultsUseCase(repository : ResultsRepository) : ResultsUseCase{
        return ResultsUseCase(resultsRepository = repository)
    }
}
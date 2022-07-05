package com.gcirilo.ygocollection.di

import androidx.paging.ExperimentalPagingApi
import com.gcirilo.ygocollection.data.repository.ArchetypeRepositoryImpl
import com.gcirilo.ygocollection.data.repository.CardRepositoryImpl
import com.gcirilo.ygocollection.domain.repository.ArchetypeRepository
import com.gcirilo.ygocollection.domain.repository.CardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @ExperimentalPagingApi
    @Binds
    @Singleton
    abstract fun providesCardRepository(cardRepositoryImpl: CardRepositoryImpl): CardRepository


    @Binds
    @Singleton
    abstract fun providesArchetypeRepository(cardRepositoryImpl: ArchetypeRepositoryImpl): ArchetypeRepository

}
package com.gcirilo.ygocollection.di

import com.gcirilo.ygocollection.domain.repository.ArchetypeRepository
import com.gcirilo.ygocollection.domain.repository.CardRepository
import com.gcirilo.ygocollection.domain.use_case.get_archetypes.GetArchetypesUseCase
import com.gcirilo.ygocollection.domain.use_case.get_cards.GetCardsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetCardsUseCase(cardRepository: CardRepository): GetCardsUseCase {
        return GetCardsUseCase(cardRepository = cardRepository)
    }

    @Provides
    fun provideGetArchetypesUseCase(archetypeRepository: ArchetypeRepository): GetArchetypesUseCase {
        return GetArchetypesUseCase(archetypeRepository)
    }
}
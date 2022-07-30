package com.gcirilo.ygocollection.domain.use_case.get_card

import com.gcirilo.ygocollection.domain.model.Card
import com.gcirilo.ygocollection.domain.repository.CardRepository
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCardUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {

    suspend operator fun invoke(id: Long): Flow<Resource<Card>> {
        return cardRepository.getCard(id)
    }
}
package com.gcirilo.ygocollection.data.repository

import com.gcirilo.ygocollection.data.local.entity.CardIdAndCollectionId
import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.local.entity.CardIdAndCollectionIdDelete
import com.gcirilo.ygocollection.data.mapper.toCollection
import com.gcirilo.ygocollection.data.mapper.toCollectionCards
import com.gcirilo.ygocollection.data.mapper.toCollectionEntity
import com.gcirilo.ygocollection.domain.model.CardCollection
import com.gcirilo.ygocollection.domain.model.CardQuantity
import com.gcirilo.ygocollection.domain.model.Collection
import com.gcirilo.ygocollection.domain.model.CollectionCards
import com.gcirilo.ygocollection.domain.model.CollectionForm
import com.gcirilo.ygocollection.domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CollectionRepositoryImpl @Inject constructor(
    db: YGOCollectionDatabase
): CollectionRepository {

    private val collectionDao = db.collectionDao()
    private val cardCollectionDao = db.cardCollectionDao()

    override fun getCollections(): Flow<List<Collection>> {
        return collectionDao.getCollections().map {
            it.map { item -> item.toCollection() }
        }
    }

    override suspend fun deleteCollection(collection: Collection) {
        collectionDao.deleteCollections(collection.toCollectionEntity())
    }

    override suspend fun getCollection(id: Long): Flow<CollectionCards> {
        return collectionDao.getCollection(id).map { it.toCollectionCards() }
    }

    override suspend fun saveCollection(collection: CollectionForm) {
        collectionDao.insertCollection(collection.toCollectionEntity())
    }

    override suspend fun addCardsToCollection(id: Long?, collectionId: Long, vararg cards: CardQuantity) {
        cardCollectionDao.insertByCardIdAndCollectionId(
            *cards.map { CardIdAndCollectionId(collectionId = collectionId, cardId = it.cardId, quantity = it.quantity, id = id) }.toTypedArray()
        )
    }

    override suspend fun removeCardsFromCollection(collectionId: Long, vararg cardIds: Long) {
        cardCollectionDao.deleteByCardIdAndCollectionId(
            *cardIds.map { CardIdAndCollectionIdDelete(collectionId = collectionId, cardId = it) }.toTypedArray()
        )
    }

    override suspend fun getCardCollection(cardId: Long, collectionId: Long): CardCollection? {
        cardCollectionDao.getCardCollection(cardId, collectionId)?.let {
            return CardCollection(it.id, it.cardId, it.collectionId, it.quantity)
        }
        return null
    }

}
package com.gcirilo.ygocollection.data.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.gcirilo.ygocollection.data.local.CardEntity

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCards(cards: List<CardEntity>)

    @Query("DELETE FROM card_entity")
    suspend fun clearCards()

    @Query("""
        SELECT * FROM card_entity
        WHERE id LIKE :id
    """)
    suspend fun getCard(id: Long): CardEntity

    @RawQuery(observedEntities = [CardEntity::class])
    fun searchCardsPaged(query: SupportSQLiteQuery): PagingSource<Int, CardEntity>

}
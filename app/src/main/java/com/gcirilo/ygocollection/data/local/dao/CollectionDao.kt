package com.gcirilo.ygocollection.data.local.dao

import androidx.room.*
import com.gcirilo.ygocollection.data.local.CollectionAndAllCards
import com.gcirilo.ygocollection.data.local.CollectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)

    @Transaction
    @Query("""
        SELECT * FROM collection_entity
        WHERE id LIKE :id
    """)
    fun getCollection(id: Long): Flow<CollectionAndAllCards>

    @Query("""
        SELECT * FROM collection_entity
    """)
    fun getCollections(): Flow<List<CollectionAndAllCards>>

    @Delete
    suspend fun deleteCollections(vararg collection: CollectionEntity)
}
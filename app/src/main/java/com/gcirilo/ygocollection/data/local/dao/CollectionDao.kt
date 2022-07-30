package com.gcirilo.ygocollection.data.local.dao

import androidx.room.*
import com.gcirilo.ygocollection.data.local.CollectionAndAllCards
import com.gcirilo.ygocollection.data.local.CollectionEntity

@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollection(collection: CollectionEntity)

    @Transaction
    @Query("""
        SELECT * FROM collection_entity
        WHERE id LIKE :id
    """)
    suspend fun getCollection(id: Long): CollectionAndAllCards

    @Query("""
        SELECT * FROM collection_entity
    """)
    suspend fun getCollections(): List<CollectionEntity>

    @Delete
    suspend fun deleteCollections(vararg collection: CollectionEntity)
}
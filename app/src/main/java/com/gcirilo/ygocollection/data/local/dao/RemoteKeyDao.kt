package com.gcirilo.ygocollection.data.local.dao

import androidx.room.*
import com.gcirilo.ygocollection.data.local.RemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKey>): List<Long>

    @Query("SELECT * FROM remote_key WHERE cardId = :cardId AND queryFilter = :query")
    suspend fun remoteKeyByCardIdAndQuery(cardId: Long, query: String): RemoteKey

    @Query("DELETE FROM remote_key")
    suspend fun clearRemoteKeys()

    @Query("DELETE FROM remote_key WHERE queryFilter = :query")
    suspend fun clearRemoteKeysWithQuery(query: String)


    @Update
    suspend fun update(keys: List<RemoteKey>)

    @Transaction
    suspend fun insertOrUpdateAll(keys: List<RemoteKey>) {
        val insertResults = insertAll(keys)
        val keysToUpdate = insertResults.indices
            .filter { i -> insertResults[i] == -1L }
            .map { i -> keys[i] }
        if (keysToUpdate.isNotEmpty()) update(keysToUpdate)
    }


}
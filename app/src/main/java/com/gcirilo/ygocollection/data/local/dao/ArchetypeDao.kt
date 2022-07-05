package com.gcirilo.ygocollection.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gcirilo.ygocollection.data.local.ArchetypeEntity

@Dao
interface ArchetypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArchetypes(archetypeEntities: List<ArchetypeEntity>)

    @Query("DELETE FROM archetype_entity")
    suspend fun clearArchetypes()

    @Query(
        """
            SELECT * 
            FROM archetype_entity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%'
        """
    )
    suspend fun searchArchetype(query: String): List<ArchetypeEntity>
}
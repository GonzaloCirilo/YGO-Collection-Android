package com.gcirilo.ygocollection.domain.repository

import com.gcirilo.ygocollection.domain.model.Archetype
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow

interface ArchetypeRepository {

    suspend fun searchArchetype(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Archetype>>>

}
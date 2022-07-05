package com.gcirilo.ygocollection.domain.use_case.get_archetypes

import com.gcirilo.ygocollection.domain.model.Archetype
import com.gcirilo.ygocollection.domain.repository.ArchetypeRepository
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArchetypesUseCase @Inject constructor(
    private val repository: ArchetypeRepository
) {

    suspend operator fun invoke(
        query: String = "",
        fetchFromRemote: Boolean = false,
    ): Flow<Resource<List<Archetype>>> {
        return repository.searchArchetype(query = query, fetchFromRemote = fetchFromRemote)
    }
}
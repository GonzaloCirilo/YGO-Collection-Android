package com.gcirilo.ygocollection.data.repository

import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.mapper.toArchetype
import com.gcirilo.ygocollection.data.mapper.toArchetypeEntity
import com.gcirilo.ygocollection.data.remote.YGOCardService
import com.gcirilo.ygocollection.domain.model.Archetype
import com.gcirilo.ygocollection.domain.repository.ArchetypeRepository
import com.gcirilo.ygocollection.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArchetypeRepositoryImpl @Inject constructor(
    private val api: YGOCardService,
    db: YGOCollectionDatabase,
): ArchetypeRepository {

    private val archetypeDao = db.archetypeDao()

    override suspend fun searchArchetype(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<Archetype>>> {
        return flow {
            emit(Resource.Loading(true))
            val localArchetypes = archetypeDao.searchArchetype(query)
            emit(Resource.Success(
                data = localArchetypes.map { it.toArchetype() }
            ))

            val isDbEmpty = localArchetypes.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if(shouldJustLoadFromCache){
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteArchetypes = try {
                api.getArchetypes()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteArchetypes?.let { archetypes ->
                archetypeDao.clearArchetypes()
                archetypeDao.insertArchetypes(
                    archetypes.map {
                        it.toArchetypeEntity()
                    }
                )
                emit(Resource.Success(
                    data = archetypeDao.searchArchetype("")
                        .map { it.toArchetype() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}
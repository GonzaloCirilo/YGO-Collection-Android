package com.gcirilo.ygocollection.data.remote

import com.gcirilo.ygocollection.data.remote.dto.ArchetypeDto
import com.gcirilo.ygocollection.data.remote.dto.CardDto
import com.gcirilo.ygocollection.data.remote.dto.YGOAPIResponse
import com.gcirilo.ygocollection.domain.model.CardFilters
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap


interface YGOCardService {

    @GET("cardinfo.php")
    suspend fun getCards(
        @QueryMap filters: Map<String, String>,
    ): YGOAPIResponse<List<CardDto>>

    @GET("cardinfo.php")
    suspend fun getCard(
        @Query("id") id: Long
    ): YGOAPIResponse<CardDto>

    @GET("archetypes.php")
    suspend fun getArchetypes(): List<ArchetypeDto>
}
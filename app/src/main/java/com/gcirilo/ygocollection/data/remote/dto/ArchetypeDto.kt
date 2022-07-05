package com.gcirilo.ygocollection.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArchetypeDto(
    @SerializedName("archetype_name")
    val name: String
)

package com.gcirilo.ygocollection.data.mapper

import com.gcirilo.ygocollection.data.local.ArchetypeEntity
import com.gcirilo.ygocollection.data.remote.dto.ArchetypeDto
import com.gcirilo.ygocollection.domain.model.Archetype

fun ArchetypeDto.toArchetypeEntity(): ArchetypeEntity {
    return ArchetypeEntity(
        name = this.name
    )
}

fun ArchetypeEntity.toArchetype(): Archetype {
    return Archetype(name = this.name)
}
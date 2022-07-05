package com.gcirilo.ygocollection.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archetype_entity")
data class ArchetypeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String
) {
}
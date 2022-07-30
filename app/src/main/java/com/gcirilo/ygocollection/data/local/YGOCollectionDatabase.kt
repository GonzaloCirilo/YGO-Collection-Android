package com.gcirilo.ygocollection.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gcirilo.ygocollection.data.local.dao.*
import com.gcirilo.ygocollection.domain.model.Collection

@Database(
    entities = [
        CardEntity::class,
        RemoteKey::class,
        ArchetypeEntity::class,
        CardCollectionEntity::class,
        CollectionEntity::class,
    ],
    version = 3,
    exportSchema = false,
)
abstract class YGOCollectionDatabase: RoomDatabase() {

    abstract val cardsDao: CardDao

    abstract fun remoteKeysDao(): RemoteKeyDao

    abstract fun archetypeDao(): ArchetypeDao

    abstract fun collectionDao(): CollectionDao

    abstract fun cardCollectionDao(): CardCollectionDao
}
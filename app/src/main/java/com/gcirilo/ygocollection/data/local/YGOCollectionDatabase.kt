package com.gcirilo.ygocollection.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gcirilo.ygocollection.data.local.dao.*
import com.gcirilo.ygocollection.data.local.entity.ArchetypeEntity
import com.gcirilo.ygocollection.data.local.entity.CardCollectionEntity
import com.gcirilo.ygocollection.data.local.entity.CardEntity
import com.gcirilo.ygocollection.data.local.entity.CollectionEntity

@Database(
    entities = [
        CardEntity::class,
        RemoteKey::class,
        ArchetypeEntity::class,
        CardCollectionEntity::class,
        CollectionEntity::class,
    ],
    version = 7,
    exportSchema = false,
)
abstract class YGOCollectionDatabase: RoomDatabase() {

    abstract val cardsDao: CardDao

    abstract fun remoteKeysDao(): RemoteKeyDao

    abstract fun archetypeDao(): ArchetypeDao

    abstract fun collectionDao(): CollectionDao

    abstract fun cardCollectionDao(): CardCollectionDao
}
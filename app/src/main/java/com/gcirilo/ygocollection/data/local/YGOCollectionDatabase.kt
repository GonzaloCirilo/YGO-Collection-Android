package com.gcirilo.ygocollection.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gcirilo.ygocollection.data.local.dao.CardDao
import com.gcirilo.ygocollection.data.local.dao.RemoteKeyDao

@Database(
    entities = [
        CardEntity::class,
        RemoteKey::class
               ],
    version = 1
)
abstract class YGOCollectionDatabase: RoomDatabase() {

    abstract val cardsDao: CardDao

    abstract fun remoteKeysDao(): RemoteKeyDao
}
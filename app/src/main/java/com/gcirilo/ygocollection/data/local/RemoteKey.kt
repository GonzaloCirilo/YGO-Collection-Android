package com.gcirilo.ygocollection.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "remote_key", primaryKeys = ["queryFilter", "cardId"])
data class RemoteKey(
    @ColumnInfo(name = "queryFilter")
    val query: String,
    val cardId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
)

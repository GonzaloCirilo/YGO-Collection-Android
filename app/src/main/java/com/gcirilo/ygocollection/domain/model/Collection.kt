package com.gcirilo.ygocollection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Collection(
    val id: Long,
    val name: String,
    val artworkUrls: List<String>,
    val cardCount: Long,
)

@Parcelize
data class CollectionForm(
    val id: Long,
    val name: String,
): Parcelable

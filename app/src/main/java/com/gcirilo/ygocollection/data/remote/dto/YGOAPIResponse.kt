package com.gcirilo.ygocollection.data.remote.dto

data class YGOAPIResponse<T>(
    var data: T,
    var meta: MetadataDto?,
)

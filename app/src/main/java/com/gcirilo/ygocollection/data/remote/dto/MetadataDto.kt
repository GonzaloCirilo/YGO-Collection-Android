package com.gcirilo.ygocollection.data.remote.dto

data class MetadataDto(
    var currentRow: Int,
    var totalRows: Long,
    var rowsRemaining: Long,
    var totalPages: Long,
    var pagesRemaining: Long,
    var nextPage: String,
    var nextPageOffset: Long,
)

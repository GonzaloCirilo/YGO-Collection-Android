package com.gcirilo.ygocollection.data.mapper

import com.gcirilo.ygocollection.data.local.CardQuery
import com.gcirilo.ygocollection.domain.model.CardFilters

fun CardFilters.toCardQuery(): CardQuery {
    return CardQuery(
        archetype = archetype
    )
}
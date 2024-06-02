package com.gcirilo.ygocollection.domain.model

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

data class CardQuery(
    val archetype: String? = null,
    val cardName: String? = "",
    val pageSize: Int = 20,
    val races: List<CardRace>? = emptyList()
) {

    private val racesStrings: List<String>
    get() = races?.map { it.toString() }.orEmpty()

    /**
     * Generates a string of the query params to be stored
     * in a RemoteKey object
     *
     * @return `key=value` string of the attributes of the query
     */
    fun toRemoteKeyString(): String {
        val queryParamsStr = mutableListOf<String>()
        archetype?.let { queryParamsStr.add("archetype=$archetype") }
        cardName?.let { if (it.isNotBlank() && it.isNotEmpty()) queryParamsStr.add("fname=$cardName") }
        races?.let { if (races.isNotEmpty()) queryParamsStr.add("race=${racesStrings.joinToString(",")}") }
        return queryParamsStr.joinToString(prefix = "?", separator = "&")
    }

    /**
     * Returns the query params for the card request. It calculates de offset given the
     * current page
     *
     * @param page current page of the Network Query
     *
     * @return A Map containing all query parameters for a Card request including
     * paging info
     */
    fun toQueryMap(page: Int): Map<String, String> {
        val currentPage = if(page < 0)
            0
        else
            page
        val map = mutableMapOf<String, String>()
        val offset = (pageSize * (currentPage - 1))
        archetype?.let { map.put("archetype", archetype) }
        cardName?.let { if (it.isNotBlank() && it.isNotEmpty()) map["fname"] = cardName }
        races?.let { if (races.isNotEmpty()) map["race"] = racesStrings.joinToString(",") }
        map["num"] = pageSize.toString()
        map["offset"] = offset.toString()
        map["sort"] = "name"
        return map
    }

    fun toSQLiteQuery(): SupportSQLiteQuery {
        var queryString = "SELECT * FROM card_entity"
        val bindings = mutableListOf<Any>()
        val queryFilterStrings = mutableListOf<String>()
        archetype?.let {
            queryFilterStrings += "LOWER(archetype) LIKE LOWER(?)"
            bindings.add(archetype)
        }
        cardName?.let {
            if (it.isNotBlank() && it.isNotEmpty()) {
                queryFilterStrings += "LOWER(name) LIKE '%' || LOWER(?) || '%'"
                bindings.add(cardName)
            }
        }
        races?.let {
            if (races.isNotEmpty()) {
                queryFilterStrings += racesStrings.map { race ->
                    bindings.add(race)
                    "LOWER(race) LIKE LOWER(?)"
                }.joinToString(separator = " OR ", prefix = "(", postfix = ")")
            }

        }
        val queryOrderString = " ORDER BY lastSeen DESC, name ASC"
        if(queryFilterStrings.isNotEmpty())
            queryString += queryFilterStrings.joinToString(separator = " AND ", prefix = " WHERE ")
        queryString += queryOrderString
        return SimpleSQLiteQuery(queryString, bindings.toTypedArray())
    }

}
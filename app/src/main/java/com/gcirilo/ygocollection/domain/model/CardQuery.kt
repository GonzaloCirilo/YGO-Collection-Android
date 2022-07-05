package com.gcirilo.ygocollection.domain.model

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery

data class CardQuery(
    val archetype: String? = "Destiny HERO",
    val cardName: String? = "",
    val pageSize: Int = 20,
) {

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
        cardName?.let {  }
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
        cardName?.let { if (it.isNotBlank() && it.isNotEmpty()) map.put("fname", cardName) }
        map["num"] = pageSize.toString()
        map["offset"] = offset.toString()
        map["sort"] = "name"
        return map
    }

    fun toSQLiteQuery(): SupportSQLiteQuery {
        var queryString = "SELECT * FROM card_entity"
        val bindings = mutableListOf<Any>()
        archetype?.let {
            queryString += " WHERE LOWER(archetype) LIKE LOWER(?)"
            bindings.add(archetype)
        }
        cardName?.let {
            if (it.isNotBlank() && it.isNotEmpty()) {
                queryString += " AND LOWER(name) LIKE '%' || LOWER(?) || '%'"
                bindings.add(cardName)
            }
        }
        queryString += " ORDER BY name ASC"
        return SimpleSQLiteQuery(queryString, bindings.toTypedArray())
    }

}
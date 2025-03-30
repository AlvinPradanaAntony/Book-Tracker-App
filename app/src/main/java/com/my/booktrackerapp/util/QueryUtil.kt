package com.my.booktrackerapp.util

import androidx.sqlite.db.SimpleSQLiteQuery

object QueryUtil {

    fun sortedBookQuery(bookSortType: BookSortType): SimpleSQLiteQuery {
        val query = StringBuilder().append("SELECT * FROM book ")
        when (bookSortType) {
            BookSortType.DATE_ADDED -> {
                query.append("ORDER BY bookAddedInMillis DESC")
            }
            BookSortType.TITLE -> {
                query.append("ORDER BY title DESC")
            }
            BookSortType.GENRE -> {
                query.append("ORDER BY genre DESC")
            }
            BookSortType.AUTHOR -> {
                query.append("ORDER BY author DESC")
            }
        }

        return SimpleSQLiteQuery(query.toString())
    }

}
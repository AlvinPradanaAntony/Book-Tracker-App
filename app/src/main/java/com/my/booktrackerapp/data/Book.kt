package com.my.booktrackerapp.data

data class Book(
    val id: Int = 0,
    val title: String,
    val genre: String,
    val totalPage: Int,
    val author: String,
    var status: String,
    var readingProgress: Int,
    var personalNote: String,
    val bookAddedInMillis: Long,
)

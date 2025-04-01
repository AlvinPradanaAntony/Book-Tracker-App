package com.my.booktrackerapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "genre")
    val genre: String,
    @ColumnInfo(name = "totalPage")
    val totalPage: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "status")
    var status: String,
    @ColumnInfo(name = "readingProgress")
    var readingProgress: Int,
    @ColumnInfo(name = "personalNote")
    var personalNote: String,
    @ColumnInfo(name = "bookAddedInMillis")
    val bookAddedInMillis: Long,
)

package com.my.booktrackerapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Query
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.my.booktrackerapp.util.CURRENTLY_READING_VALUE

interface BookDao {

    fun insertAllBooks(vararg books: Book)

    fun getListOfBookByReadingStatus(status: String): LiveData<List<Book>>

    @Transaction
    fun getAllBooks(query: SupportSQLiteQuery): PagingSource<Int, Book>

    fun insertBook(book: Book) : Long

    fun getBook(id: Int): LiveData<Book?>

    fun updateBook(updatedBook: Book)

    fun deleteBook(book: Book)

    @Query("SELECT COUNT(*) FROM book WHERE status = '$CURRENTLY_READING_VALUE' ORDER BY bookAddedInMillis DESC")
    fun getTotalOfCurrentlyReadBooks(): Int
}
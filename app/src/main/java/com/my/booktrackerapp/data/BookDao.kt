package com.my.booktrackerapp.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery
import com.my.booktrackerapp.util.CURRENTLY_READING_VALUE

@Dao
interface BookDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllBooks(vararg books: Book)

    @Query("SELECT * FROM books WHERE status = :status")
    fun getListOfBookByReadingStatus(status: String): LiveData<List<Book>>

//    @Transaction
    @RawQuery(observedEntities = [Book::class])
    fun getAllBooks(query: SupportSQLiteQuery): PagingSource<Int, Book>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBook(book: Book) : Long

    @Query("SELECT * FROM books WHERE id = :id")
    fun getBook(id: Int): LiveData<Book?>

    @Update
    fun updateBook(updatedBook: Book)

    @Delete
    fun deleteBook(book: Book)

    @Query("SELECT COUNT(*) FROM books WHERE status = '$CURRENTLY_READING_VALUE' ORDER BY bookAddedInMillis DESC")
    fun getTotalOfCurrentlyReadBooks(): Int
}
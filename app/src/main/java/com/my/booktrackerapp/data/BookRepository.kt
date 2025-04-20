package com.my.booktrackerapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.my.booktrackerapp.util.BookSortType
import com.my.booktrackerapp.util.BookStatusType
import com.my.booktrackerapp.util.CURRENTLY_READING_VALUE
import com.my.booktrackerapp.util.QueryUtil
import java.util.concurrent.Executors

class BookRepository(private val bookDao: BookDao) {

    fun getListOfCurrentlyReadBooks(): LiveData<List<Book>> =
        bookDao.getListOfBookByReadingStatus(CURRENTLY_READING_VALUE)

    fun getListOfBookByReadingStatus(status: BookStatusType): LiveData<List<Book>>  =
        bookDao.getListOfBookByReadingStatus(status.value)

    fun getAllBooks(bookSortType: BookSortType): LiveData<PagingData<Book>> {
        val query = QueryUtil.sortedBookQuery(bookSortType)
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = ENABLE_PLACEHOLDER
            ),
            pagingSourceFactory = { bookDao.getAllBooks(query) }
        ).liveData
    }

    fun insertBook(book: Book) {
        executeThread {
            bookDao.insertBook(book)
        }
    }

    fun getBook(bookId: Int): LiveData<Book?> =
        bookDao.getBook(bookId)

    fun updateBook(updatedBook: Book) {
        executeThread {
            bookDao.updateBook(updatedBook)
        }
    }

    fun deleteBook(book: Book) {
        executeThread {
            bookDao.deleteBook(book)
        }
    }

    fun getTotalOfCurrentlyReadBooks() =
        bookDao.getTotalOfCurrentlyReadBooks()

    private fun executeThread(f: () -> Unit) {
        Executors.newSingleThreadExecutor().execute(f)
    }

    companion object {

        private const val PAGE_SIZE = 10
        private const val ENABLE_PLACEHOLDER = true

        @Volatile
        private var instance: BookRepository? = null

        fun getInstance(context: Context): BookRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = BookDatabase.getInstance(context)
                    instance = BookRepository(database.bookDao())
                }
                return instance as BookRepository
            }
        }
    }
}


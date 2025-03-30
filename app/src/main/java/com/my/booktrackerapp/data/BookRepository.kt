package com.my.booktrackerapp.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.my.booktrackerapp.util.BookSortType
import java.util.concurrent.Executors

class BookRepository(private val bookDao: BookDao) {

    fun getListOfCurrentlyReadBooks(): LiveData<List<Book>> =
        throw NotImplementedError("needs implementation")

    fun getListOfBookByReadingStatus(status: String): LiveData<List<Book>> {
        throw NotImplementedError("needs implementation")
    }

    fun getAllBooks(bookSortType: BookSortType): LiveData<PagingData<Book>> {
        throw NotImplementedError("needs implementation")
    }

    fun insertBook(book: Book) {
        executeThread {
            throw NotImplementedError("needs implementation")
        }
    }

    fun getBook(bookId: Int): LiveData<Book?> =
        throw NotImplementedError("needs implementation")

    fun updateBook(
        updatedBook: Book
    ) {
        executeThread {
            throw NotImplementedError("needs implementation")
        }
    }

    fun deleteBook(book: Book) {
        executeThread {
            throw NotImplementedError("needs implementation")
        }
    }

    fun getTotalOfCurrentlyReadBooks() =
        throw NotImplementedError("needs implementation")

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


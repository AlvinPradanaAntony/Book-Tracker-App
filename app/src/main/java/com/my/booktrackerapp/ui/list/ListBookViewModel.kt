package com.my.booktrackerapp.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.paging.PagingData
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.util.BookSortType
import com.my.booktrackerapp.util.Event

class ListBookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _bookSortType = MutableLiveData<BookSortType>()

    private val _snackbarText = MutableLiveData<Event<Int>>()
    val snackbarText: LiveData<Event<Int>> = _snackbarText

    private val _undo = MutableLiveData<Event<Book>>()
    val undo: LiveData<Event<Book>> = _undo

    init {
        _bookSortType.value = BookSortType.DATE_ADDED
    }

    val books: LiveData<PagingData<Book>> = _bookSortType.switchMap { sortType ->
        repository.getAllBooks(sortType)
    }

    fun changeBookSortType(bookSortType: BookSortType) {
        _bookSortType.value = bookSortType
    }

    fun deleteBook(book: Book) {
        repository.deleteBook(book)
        _snackbarText.value = Event(R.string.book_delete_success_message)
        _undo.value = Event(book)
    }

    fun insertBook(book: Book) {
        repository.insertBook(book)
    }

}
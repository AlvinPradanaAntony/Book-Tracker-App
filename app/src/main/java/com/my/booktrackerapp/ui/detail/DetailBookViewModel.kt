package com.my.booktrackerapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.util.Event

class DetailBookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _bookId: MutableLiveData<Int> = MutableLiveData()
    val book: LiveData<Book?> = _bookId.switchMap { bookId ->
        repository.getBook(bookId)
    }

    private val _updated: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val updated: LiveData<Event<Boolean>>
        get() = _updated

    private val _deleted: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val deleted: LiveData<Event<Boolean>>
        get() = _deleted

    fun setBookId(bookId: Int){
        _bookId.value = bookId
    }

    fun updateBook(newStatus: String, newReadingProgress: Int, newPersonalNote: String) {
        val updatedBook = book.value

        updatedBook?.apply {

        }

        updatedBook?.let {

            _updated.value = Event(true)
        }
    }

    fun deleteBook() {
        book.value?.let {

            _deleted.value = Event(true)
        }
    }

}
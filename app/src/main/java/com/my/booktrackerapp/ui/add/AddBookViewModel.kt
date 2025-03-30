package com.my.booktrackerapp.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.util.Event

class AddBookViewModel(private val repository: BookRepository) : ViewModel() {

    private val _saved: MutableLiveData<Event<Boolean>> = MutableLiveData(Event(false))
    val saved: LiveData<Event<Boolean>>
        get() = _saved

    fun insertBook(book: Book) {
        repository.insertBook(book)
        _saved.value = Event(true)
    }

}
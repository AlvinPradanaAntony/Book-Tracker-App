package com.my.booktrackerapp.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository

class StatusBookViewModel (bookRepository: BookRepository) : ViewModel() {

    var tabPosition: Int = 1

    val statusWantToRead: LiveData<List<Book>> =
        throw NotImplementedError("needs implementation")
    val statusCurrentlyReading: LiveData<List<Book>> =
        throw NotImplementedError("needs implementation")
    val statusFinishedReading: LiveData<List<Book>> =
        throw NotImplementedError("needs implementation")
}
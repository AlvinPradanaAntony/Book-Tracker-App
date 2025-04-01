package com.my.booktrackerapp.ui.status

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.util.BookStatusType

class StatusBookViewModel (bookRepository: BookRepository) : ViewModel() {

    var tabPosition: Int = 1

    val statusWantToRead: LiveData<List<Book>> = bookRepository.getListOfBookByReadingStatus(BookStatusType.WANT_TO_READ)
    val statusCurrentlyReading: LiveData<List<Book>> = bookRepository.getListOfBookByReadingStatus(BookStatusType.CURRENTLY_READING)
    val statusFinishedReading: LiveData<List<Book>> = bookRepository.getListOfBookByReadingStatus(BookStatusType.FINISHED_READING)
}
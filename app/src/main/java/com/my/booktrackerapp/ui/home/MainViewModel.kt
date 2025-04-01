package com.my.booktrackerapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.data.BookRepository

class MainViewModel(bookRepository: BookRepository) : ViewModel() {
    val listOfCurrentlyReadBook: LiveData<List<Book>> =
        bookRepository.getListOfCurrentlyReadBooks()
}
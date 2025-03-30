package com.my.booktrackerapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.my.booktrackerapp.data.BookRepository
import com.my.booktrackerapp.ui.add.AddBookViewModel
import com.my.booktrackerapp.ui.detail.DetailBookViewModel
import com.my.booktrackerapp.ui.home.MainViewModel
import com.my.booktrackerapp.ui.list.ListBookViewModel
import com.my.booktrackerapp.ui.status.StatusBookViewModel

class ViewModelFactory private constructor(private val bookRepository: BookRepository) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    BookRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(bookRepository) as T
            }
            modelClass.isAssignableFrom(StatusBookViewModel::class.java) -> {
                StatusBookViewModel(bookRepository) as T
            }
            modelClass.isAssignableFrom(ListBookViewModel::class.java) -> {
                ListBookViewModel(bookRepository) as T
            }
            modelClass.isAssignableFrom(AddBookViewModel::class.java) -> {
                AddBookViewModel(bookRepository) as T
            }
            modelClass.isAssignableFrom(DetailBookViewModel::class.java) -> {
                DetailBookViewModel(bookRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}
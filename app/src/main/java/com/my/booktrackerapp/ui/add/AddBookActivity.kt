package com.my.booktrackerapp.ui.add

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.util.BookStatusType

class AddBookActivity : AppCompatActivity() {

    private lateinit var viewModel: AddBookViewModel

    private lateinit var tilBookTitle: TextInputLayout
    private lateinit var tilBookGenre: TextInputLayout
    private lateinit var tilBookTotalPage: TextInputLayout
    private lateinit var tilBookAuthor: TextInputLayout
    private lateinit var tilReadingProgress: TextInputLayout
    private lateinit var tilPersonalNote: TextInputLayout

    private lateinit var tietBookTitle: TextInputEditText
    private lateinit var tietBookGenre: TextInputEditText
    private lateinit var tietBookTotalPage: TextInputEditText
    private lateinit var tietBookAuthor: TextInputEditText
    private lateinit var spinnerBookStatus: Spinner
    private lateinit var tietReadingProgress: TextInputEditText
    private lateinit var tietPersonalNote: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        setSupportActionBar(findViewById(R.id.toolbar_addBookActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.title_activity_add)

        tilBookTitle = findViewById(R.id.til_title_add)
        tilBookGenre = findViewById(R.id.til_genre_add)
        tilBookTotalPage = findViewById(R.id.til_totalPage_add)
        tilBookAuthor = findViewById(R.id.til_author_add)
        tilReadingProgress = findViewById(R.id.til_readingProgress_add)
        tilPersonalNote = findViewById(R.id.til_personalNote_add)

        tietBookTitle = findViewById(R.id.tiet_title_add)
        tietBookGenre = findViewById(R.id.tiet_genre_add)
        tietBookTotalPage = findViewById(R.id.tiet_totalPage_add)
        tietBookAuthor = findViewById(R.id.tiet_author_add)
        spinnerBookStatus = findViewById(R.id.spinner_status_add)
        tietReadingProgress = findViewById(R.id.tiet_readingProgress_add)
        tietPersonalNote = findViewById(R.id.tiet_personalNote_add)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(AddBookViewModel::class.java)

        viewModel.saved.observe(this) { event ->
            val saved = event.getContentIfNotHandled()
            if (saved == true) {
                Toast.makeText(this, "Book successfully added", Toast.LENGTH_SHORT).show()

                tilBookTitle.error = null
                tilBookGenre.error = null
                tilBookTotalPage.error = null
                tilBookAuthor.error = null
                tilReadingProgress.error = null
                tilPersonalNote.error = null

                tietBookTitle.text?.clear()
                tietBookGenre.text?.clear()
                tietBookTotalPage.text?.clear()
                tietBookAuthor.text?.clear()
                tietReadingProgress.text?.clear()
                tietPersonalNote.text?.clear()
                spinnerBookStatus.setSelection(0)
                findViewById<ConstraintLayout>(R.id.viewGroup_addBookActivity).clearFocus()
            }
        }

        spinnerBookStatus.apply {
            val listOfBookStatus = BookStatusType.entries.map { it.value }
            adapter = ArrayAdapter(
                this@AddBookActivity,
                android.R.layout.simple_list_item_1,
                listOfBookStatus
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        1 -> {
                            tilReadingProgress.visibility = View.VISIBLE
                            tilPersonalNote.visibility = View.GONE
                        }

                        2 -> {
                            tilReadingProgress.visibility = View.GONE
                            tilPersonalNote.visibility = View.VISIBLE
                        }

                        else -> {
                            tilReadingProgress.visibility = View.GONE
                            tilPersonalNote.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_insert -> {
                if (isFieldInputFilled()) {
                    val bookTitle = tietBookTitle.text.toString()
                    val bookGenre = tietBookGenre.text.toString()
                    val bookTotalPage = tietBookTotalPage.text.toString().toInt()
                    val bookAuthor = tietBookAuthor.text.toString()
                    val bookStatus = BookStatusType.entries[spinnerBookStatus.selectedItemPosition]
                    val readingProgress = when (bookStatus) {
                        BookStatusType.WANT_TO_READ -> 0
                        BookStatusType.FINISHED_READING -> bookTotalPage
                        else -> tietReadingProgress.text.toString().toIntOrNull() ?: 0
                    }
                    val personalNote = tietPersonalNote.text.toString()
                    if (readingProgress > bookTotalPage) {
                        Toast.makeText(this, R.string.invalid_reading_progress_input_message, Toast.LENGTH_SHORT).show()
                    } else {
                        val book = Book(
                            title = bookTitle,
                            genre = bookGenre,
                            totalPage = bookTotalPage,
                            author = bookAuthor,
                            readingProgress = readingProgress,
                            status = bookStatus.value,
                            personalNote = personalNote,
                            bookAddedInMillis = System.currentTimeMillis() // added missing parameter
                        )
                        viewModel.insertBook(book)
                    }
                } else {
                    Toast.makeText(this, R.string.invalid_input_data_not_filled_message, Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun isFieldInputFilled(): Boolean {
        val checkTitleField = tietBookTitle.text?.isBlank() == false
        val checkGenreField = tietBookGenre.text?.isBlank() == false
        val checkTotalPageField = tietBookTotalPage.text?.isBlank() == false
        val checkAuthorField = tietBookAuthor.text?.isBlank() == false

        return checkTitleField && checkGenreField && checkTotalPageField && checkAuthorField
    }
}

package com.my.booktrackerapp.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.my.booktrackerapp.R
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.util.BOOK_ID
import com.my.booktrackerapp.util.BookStatusType
import com.my.booktrackerapp.util.toBookStatusType

class DetailBookActivity : AppCompatActivity() {

    private lateinit var tvBookTitleValue: TextView
    private lateinit var tvBookGenreValue: TextView
    private lateinit var tvBookTotalPageValue: TextView
    private lateinit var tvBookAuthorValue: TextView
    private lateinit var tvBookAddedValue: TextView

    private lateinit var spinnerBookStatusValue: Spinner

    private lateinit var tilReadingProgress: TextInputLayout
    private lateinit var tietReadingProgress: TextInputEditText

    private lateinit var tilPersonalNote: TextInputLayout
    private lateinit var tietPersonalNote: TextInputEditText

    private lateinit var viewModel: DetailBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_book)

        setSupportActionBar(findViewById(R.id.toolbar_detailBookActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvBookTitleValue = findViewById(R.id.tv_bookTitleValue)
        tvBookGenreValue = findViewById(R.id.tv_bookGenreValue)
        tvBookTotalPageValue = findViewById(R.id.tv_bookTotalPageValue)
        tvBookAuthorValue = findViewById(R.id.tv_bookAuthorValue)
        tvBookAddedValue = findViewById(R.id.tv_bookAddedAtValue)

        spinnerBookStatusValue = findViewById(R.id.spinner_bookStatusValue)

        tilReadingProgress = findViewById(R.id.til_readingProgress_detail)
        tietReadingProgress = findViewById(R.id.tiet_readingProgress_detail)

        tilPersonalNote = findViewById(R.id.til_personalNote_detail)
        tietPersonalNote = findViewById(R.id.tiet_personalNote_detail)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(DetailBookViewModel::class.java)

        val bookId = intent.getIntExtra(BOOK_ID, -1)
        if (bookId != -1) {
            viewModel.setBookId(bookId)
        }

        viewModel.book.observe(this) { book ->
            try {
                if (book != null) {
                    Log.d("DetailBookActivity", "Book details: $book")
                    with(book) {
                        tvBookTitleValue.text = title
                        tvBookGenreValue.text = genre
                        tvBookTotalPageValue.text = totalPage.toString()
                        tvBookAuthorValue.text = author
                        tvBookAddedValue.text = bookAddedInMillis.toString()
                        tietReadingProgress.setText(readingProgress.toString())
                        tietPersonalNote.setText(personalNote)
                        val bookStatusType = status.toBookStatusType()
                        spinnerBookStatusValue.setSelection(bookStatusType.ordinal)
                    }
                }
            } catch (e: Exception) {
                Log.e("DetailBookActivity", "Error updating book details: ${e.message}", e)
            }
        }

        viewModel.updated.observe(this) { event ->
            val updated = event.getContentIfNotHandled()
            if (updated == true) {
                Toast.makeText(
                    this,
                    getString(R.string.book_update_success_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.deleted.observe(this) { event ->
            val deleted = event.getContentIfNotHandled()
            if (deleted == true) {
                Toast.makeText(
                    this,
                    getString(R.string.book_delete_success_message),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }

        spinnerBookStatusValue.apply {
            val listOfBookStatus = BookStatusType.entries.map { it.value }
            adapter = ArrayAdapter(
                this@DetailBookActivity,
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
                            tilPersonalNote.visibility = View.GONE
                            tilReadingProgress.visibility = View.VISIBLE
                        }
                        2 -> {
                            tilPersonalNote.visibility = View.VISIBLE
                            tilReadingProgress.visibility = View.GONE
                        }
                        else -> {
                            tilReadingProgress.visibility = View.GONE
                            tilPersonalNote.visibility = View.GONE
                        }
                    }
                }
            }
        }

        findViewById<Button>(R.id.btn_update).setOnClickListener {
            val newReadingProgress = tietReadingProgress.text.toString().toIntOrNull() ?: 0
            val newPersonalNote = tietPersonalNote.text.toString()
            val newStatus = BookStatusType.entries[spinnerBookStatusValue.selectedItemPosition]
            if (newReadingProgress > tvBookTotalPageValue.text.toString().toInt()) {
                Toast.makeText(this, R.string.invalid_reading_progress_input_message, Toast.LENGTH_SHORT).show()
            } else {
                viewModel.updateBook(newStatus.value, newReadingProgress, newPersonalNote)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog.Builder(this).apply {
                    setMessage(getString(R.string.delete_alert))
                    setNegativeButton(getString(R.string.no), null)
                    setPositiveButton(getString(R.string.yes)) { _, _ ->
                        viewModel.deleteBook()
                    }
                    show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
package com.my.booktrackerapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.ui.add.AddBookActivity
import com.my.booktrackerapp.ui.detail.DetailBookActivity
import com.my.booktrackerapp.util.BookSortType
import com.my.booktrackerapp.util.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collectLatest

class ListBookActivity : AppCompatActivity() {

    private lateinit var viewModel: ListBookViewModel
    private lateinit var rvListBooks: RecyclerView
    private lateinit var adapterBooks: BookAdapter
    private lateinit var tvEmptyList: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        setSupportActionBar(findViewById(R.id.toolbar_listBookActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[ListBookViewModel::class.java]

        rvListBooks = findViewById(R.id.rv_list_books)
        tvEmptyList = findViewById(R.id.tv_empty_list)
        adapterBooks = BookAdapter { book ->
            val intent = Intent(this, DetailBookActivity::class.java).apply {
                putExtra("BOOK_ID", book.id)
            }
            startActivity(intent)
        }

        rvListBooks.layoutManager = LinearLayoutManager(this)
        rvListBooks.adapter = adapterBooks

        ItemTouchHelper(ItemTouchCallBack()).attachToRecyclerView(rvListBooks)

        viewModel.books.observe(this) { pagingData ->
            adapterBooks.submitData(lifecycle, pagingData)
            if (adapterBooks.itemCount == 0) {
                tvEmptyList.visibility = View.VISIBLE
            } else {
                tvEmptyList.visibility = View.GONE
            }
        }

        viewModel.snackbarText.observe(this) { event ->
            showSnackBar(event)
        }

        findViewById<FloatingActionButton>(R.id.fab_addBook).setOnClickListener {
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                showSortMenu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSortMenu() {
        val view = findViewById<View>(R.id.action_sort) ?: return
        PopupMenu(this, view).run {
            menuInflater.inflate(R.menu.menu_sort_book, menu)
            setOnMenuItemClickListener {
                viewModel.changeBookSortType(
                    when (it.itemId) {
                        R.id.sort_date_added -> BookSortType.DATE_ADDED
                        R.id.sort_title -> BookSortType.TITLE
                        R.id.sort_genre -> BookSortType.GENRE
                        R.id.sort_author -> BookSortType.AUTHOR
                        else -> BookSortType.TITLE
                    }
                )
                true
            }
            show()
        }
    }

    private fun showSnackBar(eventMessage: Event<Int>) {
        val message = eventMessage.getContentIfNotHandled() ?: return
        Snackbar.make(
            findViewById(R.id.vg_listBookActivity),
            getString(message),
            Snackbar.LENGTH_SHORT
        ).setAction("Undo"){
            viewModel.insertBook(viewModel.undo.value?.getContentIfNotHandled() as Book)
        }.show()
    }

    inner class ItemTouchCallBack : ItemTouchHelper.Callback() {

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return makeMovementFlags(0, ItemTouchHelper.RIGHT)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val book = (viewHolder as BookAdapter.BookViewHolder).getBook()
            viewModel.deleteBook(book)
        }
    }
}
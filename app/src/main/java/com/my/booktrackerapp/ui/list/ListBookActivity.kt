package com.my.booktrackerapp.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.ui.add.AddBookActivity
import com.my.booktrackerapp.util.BookSortType
import com.my.booktrackerapp.util.Event

class ListBookActivity : AppCompatActivity() {

    private lateinit var viewModel: ListBookViewModel
    private lateinit var rvListBooks: RecyclerView
    private lateinit var adapterBooks: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_book)

        setSupportActionBar(findViewById(R.id.toolbar_listBookActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(ListBookViewModel::class.java)

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
                        R.id.sort_date_added -> throw NotImplementedError("needs implementation")
                        R.id.sort_title -> throw NotImplementedError("needs implementation")
                        R.id.sort_genre -> throw NotImplementedError("needs implementation")
                        R.id.sort_author -> throw NotImplementedError("needs implementation")
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
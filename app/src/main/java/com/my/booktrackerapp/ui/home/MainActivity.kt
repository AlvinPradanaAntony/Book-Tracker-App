package com.my.booktrackerapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.my.booktrackerapp.R
import com.my.booktrackerapp.customview.CurrentlyReadBookView
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.ui.detail.DetailBookActivity
import com.my.booktrackerapp.ui.list.ListBookActivity
import com.my.booktrackerapp.ui.setting.SettingsActivity
import com.my.booktrackerapp.ui.status.StatusBookActivity

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var currentlyReadBookView: CurrentlyReadBookView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        currentlyReadBookView = findViewById(R.id.customRVCurrentlyReadBookView)
        viewModel.listOfCurrentlyReadBook.observe(this) { books ->
            currentlyReadBookView.submitData(books)
        }
        currentlyReadBookView.setAdapterItemClickCallback { book ->
            val intent = Intent(this, DetailBookActivity::class.java).apply {
                putExtra("BOOK_ID", book.id)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_status -> {
                val intent = Intent(this, StatusBookActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_list -> {
                val intent = Intent(this, ListBookActivity::class.java)
                startActivity(intent)
                true
            }

            R.id.action_settings -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
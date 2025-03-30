package com.my.booktrackerapp.ui.status

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.my.booktrackerapp.R
import com.my.booktrackerapp.ui.ViewModelFactory
import com.my.booktrackerapp.util.BookStatusType
import com.my.booktrackerapp.util.CURRENTLY_READING_VALUE
import com.my.booktrackerapp.util.FINISHED_READING_VALUE
import com.my.booktrackerapp.util.WANT_TO_READ_VALUE

class StatusBookActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: BookStatusAdapter

    private lateinit var tabsLayout: TabLayout

    private lateinit var viewModel: StatusBookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_book)

        setSupportActionBar(findViewById(R.id.toolbar_statusBookActivity))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val factory = ViewModelFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(StatusBookViewModel::class.java)

        viewPager = findViewById(R.id.view_pager)

        tabsLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabsLayout, viewPager) { tab, position ->
            viewPager.setCurrentItem(viewModel.tabPosition, true)
            tab.text = when (position) {
                0 -> WANT_TO_READ_VALUE
                1 -> CURRENTLY_READING_VALUE
                2 -> FINISHED_READING_VALUE
                else -> WANT_TO_READ_VALUE
            }
        }.attach()

        viewModel.statusWantToRead.observe(this) {
            if (it != null) {
                adapter.submitData(BookStatusType.WANT_TO_READ, it)
            }
        }

        viewModel.statusCurrentlyReading.observe(this) {
            if (it != null) {
                adapter.submitData(BookStatusType.CURRENTLY_READING, it)
            }
        }

        viewModel.statusFinishedReading.observe(this) {
            if (it != null) {
                adapter.submitData(BookStatusType.FINISHED_READING, it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.tabPosition = tabsLayout.selectedTabPosition
    }
}
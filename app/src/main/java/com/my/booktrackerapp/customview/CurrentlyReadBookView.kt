package com.my.booktrackerapp.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book

class CurrentlyReadBookView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var rvCurrentlyReadBooks: RecyclerView
    private var adapterCurrentlyReadBooks: CurrentlyReadBookAdapter
    private var tvEmptyCurrentlyReadBooks: TextView

    init {
        val rootView = inflate(context, R.layout.currently_read_book_view, this)

        rvCurrentlyReadBooks = rootView.findViewById(R.id.rv_currently_read_books)
        adapterCurrentlyReadBooks = CurrentlyReadBookAdapter()
        tvEmptyCurrentlyReadBooks = rootView.findViewById(R.id.tv_empty_currently_read_books)

        rvCurrentlyReadBooks.apply {
            adapter = adapterCurrentlyReadBooks
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun setAdapterItemClickCallback(onItemClick: (Book) -> Unit){
        adapterCurrentlyReadBooks.setOnItemClickCallback(object : CurrentlyReadBookAdapter.OnItemClickCallback{
            override fun onItemClicked(book: Book) {
                onItemClick.invoke(book)
            }
        })
    }

    fun submitData(newData: List<Book>) {
        //        invalidate()
        if (newData.isEmpty()) {
            rvCurrentlyReadBooks.visibility = View.GONE
            tvEmptyCurrentlyReadBooks.visibility = View.VISIBLE
        } else {
            rvCurrentlyReadBooks.visibility = View.VISIBLE
            tvEmptyCurrentlyReadBooks.visibility = View.GONE
            adapterCurrentlyReadBooks.submitData(newData)
        }
    }

}
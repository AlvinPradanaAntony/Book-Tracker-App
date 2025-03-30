package com.my.booktrackerapp.ui.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book

class BookStatusItemAdapter(
    private val items: List<Book>,
    private val onItemClickListener: (Book) -> Unit
) : RecyclerView.Adapter<BookStatusItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookStatusItemAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_book_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookStatusItemAdapter.ViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvBookTitle = itemView.findViewById<TextView>(R.id.tv_bookTitle)
        private val tvBookGenre = itemView.findViewById<TextView>(R.id.tv_bookGenre)
        private val tvBookAuthor = itemView.findViewById<TextView>(R.id.tv_bookAuthor)
        private val tvBookTotalPages = itemView.findViewById<TextView>(R.id.tv_bookTotalPages)

        fun bind(book: Book, clickListener: (Book) -> Unit) {

        }
    }
}
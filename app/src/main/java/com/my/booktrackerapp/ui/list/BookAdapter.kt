package com.my.booktrackerapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book

class BookAdapter(private val clickListener: (Book) -> Unit) :
    PagingDataAdapter<Book, BookAdapter.BookViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_book_item, parent, false))
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it, clickListener)
        }
    }

    inner class BookViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private lateinit var book: Book
        private val tvBookTitle = itemView.findViewById<TextView>(R.id.tv_bookTitle)
        private val tvBookGenre = itemView.findViewById<TextView>(R.id.tv_bookGenre)
        private val tvBookAuthor = itemView.findViewById<TextView>(R.id.tv_bookAuthor)
        private val tvBookTotalPages = itemView.findViewById<TextView>(R.id.tv_bookTotalPages)

        fun bind(book: Book, clickListener: (Book) -> Unit) {
            this.book = book

            book.apply {
                tvBookTitle.text = title
                tvBookGenre.text = genre
                tvBookAuthor.text = author
                tvBookTotalPages.text = itemView.context.getString(R.string.total_pages, totalPage)
            }

            itemView.setOnClickListener {
                clickListener(book)
            }
        }

        fun getBook(): Book = book
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }
        }
    }
}
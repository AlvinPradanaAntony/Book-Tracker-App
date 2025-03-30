package com.my.booktrackerapp.customview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import kotlin.math.floor

class CurrentlyReadBookAdapter :
    ListAdapter<Book, CurrentlyReadBookAdapter.ViewHolder>(DIFF_CALLBACK) {

    private var listBook = ArrayList<Book>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val tvBookTitle: TextView = itemView.findViewById(R.id.tv_bookTitle)
        val tvBookGenre: TextView = itemView.findViewById(R.id.tv_bookGenre)
        val tvReadingProgressValue: TextView = itemView.findViewById(R.id.tv_readingProgressValue)
        val pbReadingProgress: ProgressBar = itemView.findViewById(R.id.pb_readingProgress)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrentlyReadBookAdapter.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currently_read_book_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrentlyReadBookAdapter.ViewHolder, position: Int) {
        val book = listBook[position]
        holder.tvBookTitle.text = book.title
        holder.tvBookGenre.text = book.genre

        val readingProgressInPercentage =
            floor((book.readingProgress.toDouble() / book.totalPage.toDouble()) * 100).toInt()

        holder.tvReadingProgressValue.text = holder.itemView.context.resources.getString(
            R.string.reading_progress_value,
            readingProgressInPercentage
        )
        holder.pbReadingProgress.progress = readingProgressInPercentage

        holder.itemView.setOnClickListener{
            onItemClickCallback.onItemClicked(book)
        }
    }

    override fun getItemCount(): Int = listBook.size

    fun submitData(newData: List<Book>) {
        listBook.clear()
        listBook.addAll(newData)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(book: Book)
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
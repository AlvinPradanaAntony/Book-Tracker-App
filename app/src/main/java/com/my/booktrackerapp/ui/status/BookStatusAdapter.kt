package com.my.booktrackerapp.ui.status

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.booktrackerapp.R
import com.my.booktrackerapp.data.Book
import com.my.booktrackerapp.util.BookStatusType

class BookStatusAdapter(private val onItemClickListener: (Book) -> Unit) :
    RecyclerView.Adapter<BookStatusAdapter.PagerViewHolder>() {

    private val bookMap = LinkedHashMap<BookStatusType, List<Book>>()

    fun submitData(key: BookStatusType, books: List<Book>) {
        bookMap[key] = books
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PagerViewHolder {
        return PagerViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.status_pager_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        val key = when (position) {
            0 -> BookStatusType.WANT_TO_READ
            1 -> BookStatusType.CURRENTLY_READING
            2 -> BookStatusType.FINISHED_READING
            else -> throw IndexOutOfBoundsException()
        }
        val pageData = bookMap[key] ?: return
        holder.bind(pageData, onItemClickListener)
    }

    override fun getItemCount() = bookMap.size

    inner class PagerViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.findViewById(R.id.rv_status_books)
        fun bind(pageData: List<Book>, onItemClickListener: (Book) -> Unit) {
            recyclerView.adapter = BookStatusItemAdapter(pageData, onItemClickListener)
        }

    }

}
package com.example.mybooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.R
import com.example.mybooks.model.Book
import kotlinx.android.synthetic.main.item_huidig_boek.view.*

class BookAdapter(private val books: List<Book>, val clickListener: (Book) -> Unit) :
    RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(books: Book, clickListener: (Book) -> Unit) {
            itemView.tvHuidigBoek.text = books.bookText
            itemView.setOnClickListener { var position = adapterPosition
                if (this@BookAdapter.clickListener != null && position != RecyclerView.NO_POSITION) {
                    (this@BookAdapter.clickListener)(books)
                }
            }

        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_huidig_boek, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return books.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(books[position],clickListener)

    }


}
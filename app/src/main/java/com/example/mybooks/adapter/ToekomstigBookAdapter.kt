package com.example.mybooks.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.R
import com.example.mybooks.model.ToekomstigBoek
import kotlinx.android.synthetic.main.item_toekomstig_book.view.*

class ToekomstigBookAdapter(private val books: List<ToekomstigBoek>) : RecyclerView.Adapter<ToekomstigBookAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(books: ToekomstigBoek) {
            itemView.tvToekomstigBook.text = books.bookText

        }
    }

    /**
     * Creates and returns a ViewHolder object, inflating a standard layout called simple_list_item_1.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_toekomstig_book, parent, false)
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
        holder.bind(books[position])
    }


}
package com.example.mybooks.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.adapter.BookAdapter
import com.example.mybooks.model.Book

import kotlinx.android.synthetic.main.activity_huidig_books.*
import androidx.lifecycle.Observer
import com.example.mybooks.*
import com.google.android.material.snackbar.Snackbar


const val ADD_Book_REQUEST_CODE = 100
const val TAG = "HuidigActivity"


class HuidigBooksActivity : AppCompatActivity() {

    private var books = arrayListOf<Book>()
    private lateinit var booksAdapter: BookAdapter


    // private lateinit var bookRepository: BookRepository
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huidig_books)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.rvHuidigBoeken)

        books = arrayListOf()

        booksAdapter = BookAdapter(books, { book: Book -> partItemClicked(book) })
//        booksAdapter.clickListener

        viewManager = LinearLayoutManager(this)
        createItemTouchHelper().attachToRecyclerView(recyclerView)
        observeViewModel()
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = booksAdapter
        }
        //bookRepository = BookRepository(this)

        //initViews()

        fab.setOnClickListener {
            startAddActivity()
        }

    }

    private fun observeViewModel() {
        viewModel.huidgBooks.observe(this, Observer { books ->
            this@HuidigBooksActivity.books.clear()
            this@HuidigBooksActivity.books.addAll(books)
            booksAdapter.notifyDataSetChanged()
        })

    }

    private fun partItemClicked(book1: Book) {
        val resultIntent = Intent(
            this,
            UbdateHuidigBook::class.java
        )
        resultIntent.putExtra(UPDATE_BOOK, book1.bookText)
        resultIntent.putExtra("id", book1.id)

        setResult(Activity.RESULT_OK, resultIntent)
        startActivity(resultIntent);


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        if (id == R.id.action_settings_toekomstig) {
            val resultIntent = Intent(
                this, ToekomstigBoekenActivity::class.java
            )
            startActivity(resultIntent)
        }
        if (id == R.id.action_delete_Books_list) {
            deletAllHuidigBooks()
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startAddActivity() {
        val intent = Intent(this, BoekenToevogenActivity::class.java)
        startActivityForResult(
            intent,
            ADD_Book_REQUEST_CODE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_Book_REQUEST_CODE -> {
                    data?.let { safeData ->
                        val book = safeData.getParcelableExtra<Book>(NEW_BOOK)
                        book?.let { safeBook ->
                            viewModel.insertHuidgBook(safeBook)
                        } ?: run {
                            Log.e(TAG, "book is null")
                        }
                    } ?: run {
                        Log.e(TAG, "empty intent data received")
                    }
                }
            }
        }
    }

    private fun deletAllHuidigBooks() {
        val booksToDelete = ArrayList<Book>()
        booksToDelete.addAll(books)
        viewModel.deleteAllHuidgBooks()
        Snackbar.make(
                findViewById(R.id.rvHuidigBoeken),
                "Alle boeken zijn verwijderd!",
                Snackbar.LENGTH_LONG
            ).setActionTextColor(Color.RED)
            .setAction("UNDO") {
                booksToDelete.forEach {
                    viewModel.insertHuidgBook(it)
                }
            }.show()
    }

    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val bookToDelete = books[position]
                if (direction == ItemTouchHelper.LEFT) {

                    viewModel.deleteHuidgBook(bookToDelete)
                    Snackbar
                        .make(viewHolder.itemView, "Het boek is verwijderd", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .setAction("UNDO") {
                            viewModel.insertHuidgBook(bookToDelete)
                        }
                        .show()
                }
                booksAdapter.notifyDataSetChanged()

            }
        }
        return ItemTouchHelper(callback)
    }


}

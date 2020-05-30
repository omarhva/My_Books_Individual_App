package com.example.mybooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_huidig_books.*
import kotlinx.android.synthetic.main.content_huidig_books.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ADD_Book_REQUEST_CODE = 100

class HuidigBooksActivity : AppCompatActivity() {

    private val books = arrayListOf<Book>()
    private val booksAdapter = BookAdapter(books)
    private lateinit var bookRepository: BookRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huidig_books)
        setSupportActionBar(toolbar)

        bookRepository = BookRepository(this)

        initViews()
        fab.setOnClickListener {
            startAddActivity()
        }
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
//        return when (item.itemId) {
//            R.id.action_settings -> true
//
//            else -> super.onOptionsItemSelected(item)
//        }
        val id = item.itemId
        if (id== R.id.action_settings_toekomstig){
            val resultIntent = Intent(this, ToekomstigBoekenActivity::class.java
            )
            startActivity(resultIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startAddActivity() {
        val intent = Intent(this, BoekenToevogenActivity::class.java)
        startActivityForResult(intent, ADD_Book_REQUEST_CODE)
    }


    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvHuidigBoeken.layoutManager =
            LinearLayoutManager(this@HuidigBooksActivity, RecyclerView.VERTICAL, false)
        rvHuidigBoeken.adapter = booksAdapter
        rvHuidigBoeken.addItemDecoration(
            DividerItemDecoration(
                this@HuidigBooksActivity,
                DividerItemDecoration.VERTICAL
            )

        )
        createItemTouchHelper().attachToRecyclerView(rvHuidigBoeken)
        getBooksFromDatabase()

    }

    private fun getBooksFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val books = withContext(Dispatchers.IO) {
                bookRepository.getAllBooks()
            }
            this@HuidigBooksActivity.books.clear()
            this@HuidigBooksActivity.books.addAll(books)
            booksAdapter.notifyDataSetChanged()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_Book_REQUEST_CODE -> {
                    val book = data!!.getParcelableExtra<Book>(NEW_BOOK)
//                    books.add(book)
//                    booksAdapter.notifyDataSetChanged()
                    //we gebruiker  Coroutine omdat supsend mthodes worden alleen gecalld via een Coroutine
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            bookRepository.insertBook(book)
                        }
                        getBooksFromDatabase()
                    }


                }
            }
        }
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
//                books.removeAt(position)
//                booksAdapter.notifyDataSetChanged()
                val bookToDelete = books[position]
                CoroutineScope(Dispatchers.Main).launch {
                    withContext(Dispatchers.IO) {

                        bookRepository.deleteBook(bookToDelete)
                    }
                    getBooksFromDatabase()
                }
            }
        }
        return ItemTouchHelper(callback)
    }


}

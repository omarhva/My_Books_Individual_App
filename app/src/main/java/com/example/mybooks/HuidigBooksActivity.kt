package com.example.mybooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mybooks.adapter.BookAdapter
import com.example.mybooks.model.Book
import com.example.mybooks.repsitories.BookRepository

import kotlinx.android.synthetic.main.activity_huidig_books.*
import kotlinx.android.synthetic.main.content_huidig_books.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.Observer


const val ADD_Book_REQUEST_CODE = 100
const val TAG = "HuidigActivity"


class HuidigBooksActivity : AppCompatActivity() {

    private var books = arrayListOf<Book>()
    private var booksAdapter = BookAdapter(books)
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

        booksAdapter = BookAdapter(books)

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


//    private fun initViews() {
//        // Initialize the recycler view with a linear layout manager, adapter
//        rvHuidigBoeken.layoutManager =
//            LinearLayoutManager(this@HuidigBooksActivity, RecyclerView.VERTICAL, false)
//        rvHuidigBoeken.adapter = booksAdapter
//        rvHuidigBoeken.addItemDecoration(
//            DividerItemDecoration(
//                this@HuidigBooksActivity,
//                DividerItemDecoration.VERTICAL
//            )
//
//        )
//        createItemTouchHelper().attachToRecyclerView(rvHuidigBoeken)
//        getBooksFromDatabase()
//
//    }
//
//    private fun getBooksFromDatabase() {
//        CoroutineScope(Dispatchers.Main).launch {
//            val books = withContext(Dispatchers.IO) {
//                bookRepository.getAllBooks()
//            }
//            this@HuidigBooksActivity.books.clear()
//            this@HuidigBooksActivity.books.addAll(books)
//            booksAdapter.notifyDataSetChanged()
//        }
//    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_Book_REQUEST_CODE -> {
                    data?.let {safeData ->
                    val book = safeData.getParcelableExtra<Book>(NEW_BOOK)
                        book?.let { safeBook ->
                            viewModel.insertHuidgBook(safeBook)
                        } ?: run {
                            Log.e(TAG, "reminder is null")
                        }
                    } ?: run {
                        Log.e(TAG, "empty intent data received")
                    }
//                    books.add(book)
//                    booksAdapter.notifyDataSetChanged()
                    //we gebruiker  Coroutine omdat supsend mthodes worden alleen gecalld via een Coroutine
//                    CoroutineScope(Dispatchers.Main).launch {
//                        withContext(Dispatchers.IO) {
//                            bookRepository.insertBook(book)
//                        }
//                        getBooksFromDatabase()
//                    }


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
//                CoroutineScope(Dispatchers.Main).launch {
//                    withContext(Dispatchers.IO) {
//
//                        bookRepository.deleteBook(bookToDelete)
//                    }
//                    getBooksFromDatabase()
//                }
                viewModel.deleteHuidgBook(bookToDelete)

            }
        }
        return ItemTouchHelper(callback)
    }


}

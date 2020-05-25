package com.example.mybooks

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_toekomstig_boeken.*
import kotlinx.android.synthetic.main.content_huidig_books.*
import kotlinx.android.synthetic.main.content_toekomstig_boeken.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val ADD_TOEKOMSTIG_Book_REQUEST_CODE = 101

class ToekomstigBoekenActivity : AppCompatActivity() {

    private val toekomsitgbooks = arrayListOf<ToekomstigBoek>()
    private val toekomstigbooksAdapter = ToekomstigBookAdapter(toekomsitgbooks)
    private lateinit var bookRepository: BookRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toekomstig_boeken)
        setSupportActionBar(toolbar)

        bookRepository = BookRepository(this)
        initViews()
        fab.setOnClickListener {  startAddActivity()
        }
    }
    private fun startAddActivity() {
        val intent = Intent(this, BoekenToevogenActivity::class.java)
        startActivityForResult(intent, ADD_TOEKOMSTIG_Book_REQUEST_CODE)
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvToekomstigBoeken.layoutManager =
            LinearLayoutManager(this@ToekomstigBoekenActivity, RecyclerView.VERTICAL, false)
        rvToekomstigBoeken.adapter = toekomstigbooksAdapter
        rvToekomstigBoeken.addItemDecoration(
            DividerItemDecoration(
                this@ToekomstigBoekenActivity,
                DividerItemDecoration.VERTICAL
            )

        )
       // createItemTouchHelper().attachToRecyclerView(rvHuidigBoeken)
        getBooksFromDatabase()

    }

    private fun getBooksFromDatabase() {
        CoroutineScope(Dispatchers.Main).launch {
            val books = withContext(Dispatchers.IO) {
                bookRepository.getAllToeKomstigBooks()
            }
            this@ToekomstigBoekenActivity.toekomsitgbooks.clear()
            this@ToekomstigBoekenActivity.toekomsitgbooks.addAll(books)
            toekomstigbooksAdapter.notifyDataSetChanged()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ADD_TOEKOMSTIG_Book_REQUEST_CODE -> {
                    val book = data!!.getParcelableExtra<ToekomstigBoek>(NEW_ToekomstigBook_BOOK)
//                    books.add(book)
//                    booksAdapter.notifyDataSetChanged()
                    //we gebruiker  Coroutine omdat supsend mthodes worden alleen gecalld via een Coroutine
                    CoroutineScope(Dispatchers.Main).launch {
                        withContext(Dispatchers.IO) {
                            bookRepository.insertToeKomstigBook(book)
                        }
                        getBooksFromDatabase()
                    }


                }
            }
        }
    }

}

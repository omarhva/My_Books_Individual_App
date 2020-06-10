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
import com.example.mybooks.adapter.ToekomstigBookAdapter
import com.example.mybooks.model.ToekomstigBoek

import kotlinx.android.synthetic.main.activity_toekomstig_boeken.*
import androidx.lifecycle.Observer
import com.example.mybooks.MainActivityViewModel
import com.example.mybooks.R
import com.google.android.material.snackbar.Snackbar


const val ADD_TOEKOMSTIG_Book_REQUEST_CODE = 101
const val TAG1 = "ToekomstigActivity"


class ToekomstigBoekenActivity : AppCompatActivity() {

    private var toekomsitgbooks = arrayListOf<ToekomstigBoek>()
    private lateinit var toekomstigbooksAdapter: ToekomstigBookAdapter
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toekomstig_boeken)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "My Books"

        // bookRepository = BookRepository(this)
        recyclerView = findViewById(R.id.rvToekomstigBoeken)

        toekomsitgbooks = arrayListOf()

        toekomstigbooksAdapter = ToekomstigBookAdapter(toekomsitgbooks,
            { book: ToekomstigBoek -> partItemClicked(book) })

        viewManager = LinearLayoutManager(this)
        createItemTouchHelper().attachToRecyclerView(recyclerView)
        observeViewModel()
        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = toekomstigbooksAdapter
        }
        //bookRepository = BookRepository(this)
        //initViews()
        fab.setOnClickListener {
            startAddActivity()
        }

    }

    private fun observeViewModel() {
        viewModel.toekomstigBooks.observe(this, Observer { toekomstigBoeks ->
            this@ToekomstigBoekenActivity.toekomsitgbooks.clear()
            this@ToekomstigBoekenActivity.toekomsitgbooks.addAll(toekomstigBoeks)
            toekomstigbooksAdapter.notifyDataSetChanged()
        })
    }

    private fun partItemClicked(book: ToekomstigBoek) {
        val resultIntent = Intent(
            this,
            UpdateToekomstigActivity::class.java
        )
        resultIntent.putExtra(UPDATE_TOEKOMSTIG_BOOK, book.bookText)
        resultIntent.putExtra("id", book.id)

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
        if (id == R.id.action_settings_Huidig) {
            val resultIntent = Intent(
                this, HuidigBooksActivity::class.java
            )
            startActivity(resultIntent)
        }
        if (id == R.id.action_delete_Books_list) {
            deletAllToekomstigBooks()
            true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startAddActivity() {
        val intent = Intent(this, BoekenToevoegenToekomstig::class.java)
        startActivityForResult(
            intent,
            ADD_TOEKOMSTIG_Book_REQUEST_CODE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == ADD_TOEKOMSTIG_Book_REQUEST_CODE) {
            when (requestCode) {
                ADD_TOEKOMSTIG_Book_REQUEST_CODE -> {
                    data?.let { safeData ->
                        val book =
                            safeData.getParcelableExtra<ToekomstigBoek>(NEW_ToekomstigBook_BOOK)
                        book?.let { safeToekomstigBoek ->
                            viewModel.insertToekomstigBook(safeToekomstigBoek)
                        } ?: run {
                            Log.e(TAG1, "reminder is null")
                        }
                    } ?: run {
                        Log.e(TAG1, "empty intent data received")
                    }
                }

            }
        }
    }

    private fun deletAllToekomstigBooks() {
        val boeksToDelete = ArrayList<ToekomstigBoek>()
        boeksToDelete.addAll(toekomsitgbooks)
        viewModel.deleteAllToekomstigBooks()
        Snackbar.make(
                findViewById(R.id.rvToekomstigBoeken),
                "Alle boeken zijn verwijderd!",
                Snackbar.LENGTH_LONG
            ).setActionTextColor(Color.RED)
            .setAction("UNDO") {
                boeksToDelete.forEach {
                    viewModel.insertToekomstigBook(it)
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
                val bookToDelete = toekomsitgbooks[position]
                if (direction == ItemTouchHelper.LEFT) {

                    viewModel.deleteToekomstigBook(bookToDelete)
                    Snackbar
                        .make(viewHolder.itemView, "Het boek is verwijderd", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .setAction("UNDO") {
                            viewModel.insertToekomstigBook(bookToDelete)
                        }
                        .show()
                }
                toekomstigbooksAdapter.notifyDataSetChanged()

            }
        }
        return ItemTouchHelper(callback)
    }

}
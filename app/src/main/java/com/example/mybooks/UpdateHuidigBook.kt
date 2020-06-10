package com.example.mybooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mybooks.model.Book
import kotlinx.android.synthetic.main.activity_ubdate_huidig_book.*

const val UPDATE_BOOK = "UPDATE_BOOK"

class UbdateHuidigBook : AppCompatActivity() {


    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ubdate_huidig_book)

        getData()


    }

    private fun getData() {
        var bundle: Bundle? = intent.extras
        var bookText = bundle?.getString(UPDATE_BOOK) // 1
        var id = bundle?.getLong("id") // 1

        etUpdateBoek.setText(bookText)



        btupdateHuidig.setOnClickListener {


            var book = Book(etUpdateBoek.text.toString(), id)
            viewModel.updateHuidgBook(book)

            startAddActivity()
            Toast.makeText(this, "Het boek is upgedate", Toast.LENGTH_LONG).show()

        }

    }

    private fun startAddActivity() {
        val intent = Intent(this, HuidigBooksActivity::class.java)
        startActivity(intent)
    }


}

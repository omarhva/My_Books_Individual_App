package com.example.mybooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.mybooks.model.ToekomstigBoek
import kotlinx.android.synthetic.main.activity_update_toekomstig.*

const val UPDATE_TOEKOMSTIG_BOOK = "UPDATE_TOEKOMSTIG_BOOK"

class UpdateToekomstigActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_toekomstig)
        getData()
    }

    private fun getData() {
        var bundle: Bundle? = intent.extras
        var bookText = bundle?.getString(UPDATE_TOEKOMSTIG_BOOK) // 1
        var id = bundle?.getLong("id") // 1

        etUpdateToekomstigBook.setText(bookText)



        btUpdateToekomstig.setOnClickListener {


            var book = ToekomstigBoek(etUpdateToekomstigBook.text.toString(), id)
            viewModel.updateToekomstigBook(book)

            startAddActivity()
            Toast.makeText(this, "Het boek is upgedate", Toast.LENGTH_LONG).show()

        }

    }

    private fun startAddActivity() {
        val intent = Intent(this, ToekomstigBoekenActivity::class.java)
        startActivity(intent)
    }
}

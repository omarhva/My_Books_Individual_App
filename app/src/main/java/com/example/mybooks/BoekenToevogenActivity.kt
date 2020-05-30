package com.example.mybooks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_boeken_toevogen.*

const val NEW_BOOK = "NEW_BOOK"
//const val NEW_ToekomstigBook_BOOK = "NEW_TOEKOMSTIG_BOOK"

class BoekenToevogenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boeken_toevogen)
        initViews()
    }

    private fun initViews() {
        btHuidigBoeken.setOnClickListener { onSaveClick() }
        //btToekoemstigeBoeken.setOnClickListener { (onSaveClickToekomstigBoek()) }
    }

    private fun onSaveClick() {
        if (etAddBoek.text.toString().isNotBlank()) {
            val book = Book(etAddBoek.text.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(NEW_BOOK, book)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(
                this, "Je hebt niks toegevoegd"
                , Toast.LENGTH_SHORT
            ).show()
//            //Go back to HuidigBoeken
            val resultIntent = Intent(
                this,
                HuidigBooksActivity::class.java
            )
            startActivity(resultIntent);
        }
    }

//    private fun onSaveClickToekomstigBoek() {
//        if (etAddBoek.text.toString().isNotBlank()) {
//            val book = Book(etAddBoek.text.toString())
//            val resultIntent = Intent()
//            resultIntent.putExtra(NEW_ToekomstigBook_BOOK, book)
//            setResult(Activity.RESULT_OK, resultIntent)
//            finish()
//        } else {
//            Toast.makeText(
//                this, "Je hebt niks toegevoegd"
//                , Toast.LENGTH_SHORT
//            ).show()
////            //Go back to ToekomstigBoeken
//            val resultIntent = Intent(
//                this,
//                ToekomstigBoekenActivity::class.java
//            )
//            startActivity(resultIntent);
//        }
//    }
}

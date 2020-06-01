package com.example.mybooks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mybooks.model.ToekomstigBoek
import kotlinx.android.synthetic.main.activity_boeken_toevoegen_toekomstig.*

const val NEW_ToekomstigBook_BOOK = "NEW_TOEKOMSTIG_BOOK"
class BoekenToevoegenToekomstig : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boeken_toevoegen_toekomstig)
        initViews()
    }
    private fun initViews() {
        btToekomstig.setOnClickListener { (onSaveClickToekomstigBoek()) }
    }

        private fun onSaveClickToekomstigBoek() {
        if (etAddToekomstigBoek.text.toString().isNotBlank()) {
            val book =
                ToekomstigBoek(etAddToekomstigBoek.text.toString())
            val resultIntent = Intent()
            resultIntent.putExtra(NEW_ToekomstigBook_BOOK, book)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(
                this, "Je hebt niks toegevoegd"
                , Toast.LENGTH_SHORT
            ).show()
//            //Go back to ToekomstigBoeken
            val resultIntent = Intent(
                this,
                ToekomstigBoekenActivity::class.java
            )
            startActivity(resultIntent);
        }
    }
}

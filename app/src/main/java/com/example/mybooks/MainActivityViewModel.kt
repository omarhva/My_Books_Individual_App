package com.example.mybooks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mybooks.model.Book
import com.example.mybooks.model.ToekomstigBoek
import com.example.mybooks.repsitories.BookRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val bookRepository: BookRepository = BookRepository(application.applicationContext)

    val huidgBooks: LiveData<List<Book>> = bookRepository.getAllBooks()
    val toekomstigBooks: LiveData<List<ToekomstigBoek>> = bookRepository.getAllToeKomstigBooks()

    /**
     * Huidig Books methodes
     */
    fun insertHuidgBook(boek: Book) {
        ioScope.launch {
            bookRepository.insertBook(boek)
        }
    }

    fun deleteHuidgBook(boek: Book) {
        ioScope.launch {
            bookRepository.deleteBook(boek)
        }
    }

    fun deleteAllHuidgBooks() {
        ioScope.launch {
            bookRepository.deleteAllHuidigBooks()
        }
    }

    fun updateHuidgBook(boek: Book) {
        ioScope.launch {
            bookRepository.updateBook(boek)
        }
    }


    /**
     * Toekomstig Books methodes
     */

    fun insertToekomstigBook(boek: ToekomstigBoek) {
        ioScope.launch {
            bookRepository.insertToeKomstigBook(boek)
        }
    }

    fun deleteToekomstigBook(boek: ToekomstigBoek) {
        ioScope.launch {
            bookRepository.deleteToeKomstigBook(boek)
        }
    }

    fun deleteAllToekomstigBooks() {
        ioScope.launch {
            bookRepository.deleteAllToekomstigBooks()
        }
    }

    fun updateToekomstigBook(boek: ToekomstigBoek) {
        ioScope.launch {
            bookRepository.updateToekomstigBook(boek)
        }
    }


}

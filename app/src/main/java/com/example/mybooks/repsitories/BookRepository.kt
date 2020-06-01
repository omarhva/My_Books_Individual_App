package com.example.mybooks.repsitories

import android.content.Context
import com.example.mybooks.dao.BookDAO
import com.example.mybooks.BookRoomDatabase
import com.example.mybooks.model.Book
import com.example.mybooks.model.ToekomstigBoek

public class BookRepository(context: Context) {
    private var bookDao: BookDAO
    // Context object because we need this to access the database.
    init {
        val bookRoomDatabase =
            BookRoomDatabase.getDatabase(context)
        bookDao = bookRoomDatabase!!.bookDao()
    }


    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

    suspend fun insertBook(book: Book) {
       // bookDao.insertBook(Book("BOOKFAKE"))

        bookDao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }


    //toekomstig
    suspend fun getAllToeKomstigBooks(): List<ToekomstigBoek> {
        return bookDao.getAllToeKomstigBooks()
    }

    suspend fun insertToeKomstigBook(book: ToekomstigBoek) {
//        bookDao.insertToeKomstigBook(ToekomstigBoek("fake book"))
        bookDao.insertToeKomstigBook(book)
    }
        suspend fun deleteToeKomstigBook(book: ToekomstigBoek) {
        bookDao.deleteToekomstigBook(book)
    }


}

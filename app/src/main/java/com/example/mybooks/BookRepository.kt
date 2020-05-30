package com.example.mybooks

import android.content.Context

public class BookRepository(context: Context) {
    private var bookDao: BookDAO
    // Context object because we need this to access the database.
    init {
        val bookRoomDatabase = BookRoomDatabase.getDatabase(context)
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

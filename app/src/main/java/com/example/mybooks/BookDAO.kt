package com.example.mybooks

import androidx.room.*

@Dao
interface BookDAO{
    @Query("SELECT * FROM booktable")
    fun getAllBooks(): List<Book>

    @Insert
    fun insertBook(book: Book)

    @Delete
    fun deleteBook(book: Book)

    @Update
    fun updateBook(book: Book)


//toekomstigBooks
    @Query("SELECT * FROM ToekmstigBoekTable")
    fun getAllToeKomstigBooks(): List<ToekomstigBoek>

    @Insert
    fun insertToeKomstigBook(book: ToekomstigBoek)

    @Delete
    fun deleteToekomstigBook(book: ToekomstigBoek)
}
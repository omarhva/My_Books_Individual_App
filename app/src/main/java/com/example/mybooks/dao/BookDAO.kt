package com.example.mybooks.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mybooks.model.Book
import com.example.mybooks.model.ToekomstigBoek

@Dao
interface BookDAO {
    @Query("SELECT * FROM booktable")
    fun getAllBooks(): LiveData<List<Book>>

    @Insert
    fun insertBook(book: Book)


    @Update
    fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Query("DELETE  FROM BookTable")
    suspend fun deleteAllHuidigBooks()


    //toekomstigBooks
    @Query("SELECT * FROM ToekmstigBoekTable")
    fun getAllToeKomstigBooks(): LiveData<List<ToekomstigBoek>>

    @Insert
    fun insertToeKomstigBook(book: ToekomstigBoek)

    @Update
    fun updateToekomstigBook(book: ToekomstigBoek)


    @Delete
    suspend fun deleteToekomstigBook(book: ToekomstigBoek)

    @Query("DELETE  FROM ToekmstigBoekTable")
    suspend fun deleteAllToekomstigBooks()


}
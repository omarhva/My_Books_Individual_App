package com.example.mybooks.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mybooks.dao.BookDAO
import com.example.mybooks.model.Book
import com.example.mybooks.model.ToekomstigBoek

//@Database(entities = [Book::class], version = 1, exportSchema = false)
@Database(entities = arrayOf(Book::class, ToekomstigBoek::class), version =4 , exportSchema = false)
abstract class BookRoomDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDAO

    companion object {
        private const val DATABASE_NAME = "BOOK_DATABASE1"

        @Volatile
        private var bookRoomDatabaseInstance: BookRoomDatabase? = null

        fun getDatabase(context: Context): BookRoomDatabase? {
            if (bookRoomDatabaseInstance == null) {
                synchronized(BookRoomDatabase::class.java) {
                    if (bookRoomDatabaseInstance == null) {
                        bookRoomDatabaseInstance = Room.databaseBuilder(
                                context.applicationContext,
                                BookRoomDatabase::class.java,
                                DATABASE_NAME
                            )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return bookRoomDatabaseInstance
        }
    }

}
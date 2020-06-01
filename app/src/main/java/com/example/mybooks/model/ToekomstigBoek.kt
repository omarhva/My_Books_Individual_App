package com.example.mybooks.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "ToekmstigBoekTable")
data class ToekomstigBoek(
    @ColumnInfo(name = "toekmstigBoek")
    var bookText: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long? = null
) : Parcelable
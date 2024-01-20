package com.alishoghi97.notesapp.models

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("ParcelCreator")
@Entity
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String ,
    var searchQuery: String = "",
    val date  : LocalDateTime = LocalDateTime.now()
) : Parcelable {
    val createDateformated : String
        get() = date.format(DateTimeFormatter.ofPattern("yyyy//MM/dd | HH:mm"))

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        TODO("Not yet implemented")
    }
}
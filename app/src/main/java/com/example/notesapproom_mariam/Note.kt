package com.example.notesapproom_mariam

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table name
@Entity(tableName = "NotesTableMariam") // @Entity >> androidx.room
    data class Note(
        @PrimaryKey(autoGenerate = true) // // What I mean here is that he increases..
        val id: Int,
        val noteText: String)

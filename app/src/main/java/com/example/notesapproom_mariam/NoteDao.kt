package com.example.notesapproom_mariam

import androidx.room.*


@Dao //DAO (Database Access Object):
//كائن الوصول إلى قاعدة البيانات
interface NoteDao {
// onConflictstrattegy (replace)اذا كان موجود قبل راح يسوي >>
    @Insert(onConflict = OnConflictStrategy.REPLACE)//IGNORE
    suspend fun addNote(note: Note) // sunpend ..I can make a stop or work..
//صيغة ثابتة ..// Table name
    @Query("SELECT * FROM NotesTableMariam ORDER BY id ASC")
// بترجع البيانات List
    fun getNotes(): List<Note>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}
//The Room library consists of 3 major components:(@Entity, @Dao, @Database)
// Entity represents a table within the database and has to be annotated with @Enity.
// Each Entity consist of a minimum of one field has to define a primary ke
// مجموعة من fun يتعامل مع بعض ..coroutin
// فيه ثلاث اشياء مهمه ..suspend
// Dispatchers and control
//A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously.
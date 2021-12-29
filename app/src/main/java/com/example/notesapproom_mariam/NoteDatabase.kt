package com.example.notesapproom_mariam

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// اسم الكلاس note
//exportSchema..tueاقدر اخليها >> flase>
@Database(entities = [Note::class], version = 1, exportSchema = false)
//database holder an is the main accespoint to your relational data.
abstract class NoteDatabase: RoomDatabase() { //RommDatabase>> abstract
// noteDoa>> الوسيط
    abstract fun noteDao(): NoteDao

    companion object{ //I understand, but I don't know how to explain it:)!
        @Volatile  // writes to this field are immediately visible to other threads
        private var INSTANCE: NoteDatabase? = null // null

        fun getDatabase(context: Context): NoteDatabase{ // عشان ترجع instance
            val Mariam1= INSTANCE //tempInstance
            if(Mariam1 != null){
                return Mariam1
            }
            synchronized(this){  // protection from concurrent execution on multiple threads
                val instance = Room.databaseBuilder( // basebuilder
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "noteDatabase"
                ).fallbackToDestructiveMigration()  // Destroys old database on version change
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}

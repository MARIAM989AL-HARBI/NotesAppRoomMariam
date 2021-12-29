package com.example.notesapproom_mariam

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val noteDao by lazy { NoteDatabase.getDatabase(this).noteDao() }
    private lateinit var notes: List<Note>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notes = listOf()
        note_btn.setOnClickListener {
            addNote(note_et.text.toString())
            updateRV()
        }
        getItemsList()

        updateRV()
    }

    private fun updateRV(){
        note_recyclerView.adapter = NoteAdapter(this, notes)
        note_recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList(){
        CoroutineScope(IO).launch {
            val data = async {
                NoteRepository(noteDao).getNotes
            }.await()
            if(data.isNotEmpty()){
                notes = data
                Log.e("MainActivity", "$notes")
                Log.e("MainActivity", "$data")
                updateRV()
            }else{
                Log.e("MainActivity", "Unable to get data", )
            }
        }
    }


    private fun addNote(noteText: String){
        if(note_et.text.isEmpty()){
            Toast.makeText(this, "Error note is empty!!", Toast.LENGTH_LONG).show()
        }else{
            CoroutineScope(IO).launch {
                NoteRepository(noteDao).addNote(Note(0, noteText))
            }
            note_et.text.clear()
            Toast.makeText(this, "Note Added successfully!!", Toast.LENGTH_LONG).show()

        }

    }
    private fun editNote(noteID: Int, noteText: String){
        CoroutineScope(IO).launch {
            NoteRepository(noteDao).updateNote(Note(noteID,noteText))
        }
    }

    fun deleteNote(noteID: Int){
        CoroutineScope(IO).launch {
            NoteRepository(noteDao).deleteNote(Note(noteID,""))
        }
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {

                    _, _ ->
                run {
                    editNote(id, updatedNote.text.toString())
                    updateRV()
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }
}

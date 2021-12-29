package com.example.notesapproom_mariam

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_row.view.*

class NoteAdapter
    (
    private val activity: MainActivity,
    private val items: List<Note>): RecyclerView.Adapter<NoteAdapter.ItemViewHolder>() {

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteAdapter.ItemViewHolder, position: Int) {
        val item = items[position]

        holder.itemView.apply {
            note_tv.text = item.noteText
            update.setOnClickListener {
                activity.raiseDialog(item.id)
            }
            del.setOnClickListener {
                activity.deleteNote(item.id)
            }
        }
    }

    override fun getItemCount() = items.size
}

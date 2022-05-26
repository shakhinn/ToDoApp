package com.example.todoapp

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.backend.Note

open class NotesAdapter(context: Context, resource: Int, listOfNotes: MutableList<Note>) : ArrayAdapter<Note>(context, resource, listOfNotes) {

    var resource: Int
    var listOfNotes: MutableList<Note>
    var inflater: LayoutInflater


    init {
        this.resource = resource
        this.listOfNotes = listOfNotes
        this.inflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var holder: ViewHolder
        var convertView1: View

        if(convertView == null){
            convertView1 = inflater.inflate(this.resource, parent, false)
            holder = ViewHolder(convertView1)

            convertView1.setTag(holder)
        }

        else{
            holder = convertView.tag as ViewHolder
            convertView1 = convertView
        }

        val note : Note = listOfNotes[position]

        holder.noteText.text = note.text
        holder.date.text = note.datestamp
        holder.checkBox.isChecked = note.status
        if(holder.checkBox.isChecked){
            holder.noteText.paintFlags = holder.noteText.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG) // тут побитовые операции вкл зачеркнутый
            holder.date.paintFlags = holder.date.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG)
        }
        else{
            holder.noteText.paintFlags = holder.noteText.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG.inv()) // тут побитовые операции выкл зачеркнутый
            holder.date.paintFlags = holder.date.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }

        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                holder.noteText.paintFlags = holder.noteText.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG) // тут побитовые операции вкл зачеркнутый
                holder.date.paintFlags = holder.date.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG)
                listOfNotes[position].status = true  //вот это очень плохо и так нельзя залазить и это ещё и не раотает. Сук. прям походу команды придется писать
            } else {
                holder.noteText.paintFlags = holder.noteText.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG.inv()) // тут побитовые операции выкл зачеркнутый
                holder.date.paintFlags = holder.date.paintFlags.and(Paint.STRIKE_THRU_TEXT_FLAG.inv())
                listOfNotes[position].status = false //алогично
            }
        }

        return convertView1
    }

    private class ViewHolder(view: View) {
        var checkBox: CheckBox
        var noteText: TextView
        var date : TextView
        init {
            checkBox = view.findViewById(R.id.isComplete)
            noteText = view.findViewById(R.id.note_text)
            date = view.findViewById(R.id.date)
        }
    }
}
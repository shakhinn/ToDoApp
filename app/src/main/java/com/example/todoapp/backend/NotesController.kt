package com.example.todoapp.backend

import android.content.Context
import android.util.Log
import org.json.*
import java.io.*
import java.io.BufferedWriter

class NotesController(context: Context) {
    val context = context
    var listOfNotes: MutableList<Note> = mutableListOf<Note>()

    init {
        try{
            val file = File("data/data/com.example.todoapp/files/database.json")
            val response = file.readText()
            if(response != null) {
                if(response.isNotEmpty()) {
                    val jsonObject = JSONTokener(response).nextValue() as JSONObject
                    val jsonArray = jsonObject.getJSONArray("note")

                    for (i in 0 until jsonArray.length()) {
                        val uid = jsonArray.getJSONObject(i).getInt("uid")

                        val datestamp = jsonArray.getJSONObject(i).getString("datestamp")

                        var text = jsonArray.getJSONObject(i).getString("text")

                        var status = jsonArray.getJSONObject(i).getBoolean("status")

                        listOfNotes.add(Note(uid, datestamp, text, status))
                    }
                }
            }

        } catch (e: FileNotFoundException){
            Log.e(this.javaClass.name,"Database not found")
        }
    }

    fun addNote(date: String, text: String) {
        Log.d("DebugTag", "Note added")
        val uid: Int = if (listOfNotes.isEmpty()) {
            0
        } else {
            listOfNotes.last().uid + 1
        }
        listOfNotes.add(Note(uid, date, text, false))
        save()
    }

    fun deleteNote(id: Int) {
        Log.d("DebugTag", "Note deleted")
        if (listOfNotes.isNotEmpty()) {
            for (note in listOfNotes) {
                if (note.uid == id) {
                    listOfNotes.remove(note)
                    break
                }
            }
            save()
        }
    }

    fun edit(id: Int, newText: String, newDate: String){
        Log.d("DebugTag", "Note $id edited")
        if (listOfNotes.isNotEmpty()) {
            for (note in listOfNotes) {
                if (note.uid == id) {
                    note.text = newText
                    note.datestamp = newDate
                    break
                }
            }
            save()
        }
    }

    fun save() {
        try {
            val fos = BufferedWriter(OutputStreamWriter(context.openFileOutput("database.json", Context.MODE_PRIVATE)))
            fos.write(this.toString())
            fos.close()
            Log.d("DebugTag", "Data saved")
        }
        catch (e:FileNotFoundException){
            Log.d("DebugTag", "Data not saved")
        }
        catch (e: IOException){
            Log.d("DebugTag", "Data not saved")
        }
    }

    override fun toString(): String {
        return "{\"note\": $listOfNotes}"
    }
}
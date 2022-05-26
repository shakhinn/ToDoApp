package com.example.todoapp.backend

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import com.example.todoapp.MainActivity
import org.json.*
import java.io.*
import java.io.BufferedReader;
import java.io.BufferedWriter
import java.util.*

class NotesController(context: Context) {
    val context = context
    var listOfNotes: MutableList<Note> = mutableListOf<Note>()

    init {
        try{
            val file = File("data/data/com.example.todoapp/files/database.json")
            val response = file.readText()
            Log.d("TAG", response)
            if(response != null) {
                if(response.length != 0) {
                    val jsonObject = JSONTokener(response).nextValue() as JSONObject
                    val jsonArray = jsonObject.getJSONArray("note")

                    for (i in 0 until jsonArray.length()) {
                        // кушаем uid
                        val uid = jsonArray.getJSONObject(i).getInt("uid")

                        // кушаем datestamp
                        val datestamp = jsonArray.getJSONObject(i).getString("datestamp")

                        // кушаем text
                        var text = jsonArray.getJSONObject(i).getString("text")

                        // кушаем status
                        var status = jsonArray.getJSONObject(i).getBoolean("status")

                        listOfNotes.add(Note(uid, datestamp, text, status))
                    }
                }
            }

        } catch (e: FileNotFoundException){
            println("Database not found\nEmpty array")
        }
    }

    fun addNote(date: String, text: String) {
        val uid: Int = if (listOfNotes.isEmpty()) {
            0
        } else {
            listOfNotes.last().uid + 1
        }
        listOfNotes.add(Note(uid, date, text, false))
        save()
    }

    fun deleteNote(id: Int) {
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

    fun editText(id: Int, newText: String) {
        if (listOfNotes.isNotEmpty()) {
            for (note in listOfNotes) {
                if (note.uid == id) {
                    note.text = newText
                    break
                }
            }
            save()
        }
    }

    fun editStatus(id: Int, newStatus: Boolean) {
        if (listOfNotes.isNotEmpty()) {
            for (note in listOfNotes) {
                if (note.uid == id) {
                    note.status = newStatus
                    break
                }
            }
            save()
        }
    }

    fun edit(id: Int, newText: String, newDate: String){
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
            Log.d("TAG", "записал")
        }
        catch (e:FileNotFoundException){
            Log.d("TAG", "не записал")
        }
        catch (e: IOException){
            Log.d("TAG", "не записал")
        }
    }

    override fun toString(): String {
        return "{\"note\": $listOfNotes}"
    }
}
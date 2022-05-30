package com.example.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import android.content.Intent

import com.example.todoapp.backend.*

class MainActivity : AppCompatActivity() {
    private val notesController = NotesController(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = NotesAdapter(this, R.layout.list_item, notesController.listOfNotes)

        val listView: ListView = findViewById(R.id.listView)

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            Log.d("TAG", "edit button is clicked")
            val editIntent = Intent(this, EditActivity::class.java)
            editIntent.putExtra("id", notesController.listOfNotes[position].uid)
            editIntent.putExtra("date", notesController.listOfNotes[position].datestamp)
            editIntent.putExtra("text", notesController.listOfNotes[position].text)
            startActivityForResult(editIntent, 2)

        }


        listView.setOnItemLongClickListener { parent, view, position, id ->
            Log.d("TAG", "delete button is clicked")
            val deleteIntent = Intent(this, DeleteActivity::class.java)
            deleteIntent.putExtra("id", notesController.listOfNotes[position].uid)
            startActivityForResult(deleteIntent, 3)
            true
        }

        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener{
            Log.d("TAG", "add button is clicked")
            val addIntent = Intent(this, AddActivity::class.java)
            startActivityForResult(addIntent, 1)

        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            val listView: ListView = findViewById(R.id.listView)
            val adapter = NotesAdapter(this, R.layout.list_item, notesController.listOfNotes)
            listView.adapter = adapter
            when (requestCode) {
                1 ->{ notesController.addNote(
                    data.getStringExtra("datestamp").toString(),
                    data.getStringExtra("text").toString()
                )
                    adapter.notifyDataSetChanged()
                }
                2->{
                    data.extras?.let { data.extras!!.getString("text")?.let { it1 ->
                        data.extras!!.getString("date")?.let { it2 ->
                            notesController.edit(it.getInt("uid"),
                                it1, it2
                            )
                        }
                    } }
                    adapter.notifyDataSetChanged()
                }
                3->{
                    if(data.getStringExtra("delete").toString() == "true"){
                        data.extras?.let { notesController.deleteNote(it.getInt("uid")) }
                        adapter.notifyDataSetChanged()
                    }
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        notesController.save()
    }

}
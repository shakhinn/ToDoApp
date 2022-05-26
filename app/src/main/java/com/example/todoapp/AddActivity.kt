package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val addButton: Button = findViewById(R.id.buttonAdd)
        val exitButton: Button = findViewById(R.id.buttonBack)
        val editTextNote: EditText = findViewById(R.id.editTextNote)
        val editTextDate: EditText = findViewById(R.id.editTextDate)

        addButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("text", editTextNote.text.toString())
            intent.putExtra("datesamp", editTextDate.text.toString())
            setResult(1, intent)
            finish()
        }
        exitButton.setOnClickListener{
            finish()
        }
    }
}
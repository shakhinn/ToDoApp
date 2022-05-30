package com.example.todoapp

import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val addButton: Button = findViewById(R.id.buttonAdd)
        val exitButton: Button = findViewById(R.id.buttonBack)
        val editTextNote: EditText = findViewById(R.id.editTextNote)
        val datePicker: DatePicker = findViewById(R.id.datePicker)

        val today = Calendar.getInstance()
        val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        var dateStr: String = inputSDF.format(today.time)
        datePicker.minDate = today.timeInMillis
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            today.set(year, monthOfYear, dayOfMonth)
            dateStr = inputSDF.format(today.time)
        }

        addButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("text", editTextNote.text.toString())
            intent.putExtra("datestamp", dateStr)
            setResult(1, intent)
            finish()
        }
        exitButton.setOnClickListener{
            finish()
        }
    }
}
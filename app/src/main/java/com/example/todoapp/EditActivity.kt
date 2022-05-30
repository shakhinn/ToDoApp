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

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        var editT: EditText = findViewById(R.id.edittext)
        val datePicker: DatePicker = findViewById(R.id.datePicker)
        val ok: Button = findViewById(R.id.ok)
        val neok: Button = findViewById(R.id.neok)
        var dateStr: String = ""

        var args = intent.extras
        if (args != null) {
            editT.setText(args.getString("text"))
            val today = Calendar.getInstance()
            val inputSDF = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            today.time = inputSDF.parse(args.getString("date").toString())
            dateStr = args.getString("date").toString()
            datePicker.minDate = today.timeInMillis
            datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)
            ) { view, year, monthOfYear, dayOfMonth ->
                today.set(year, monthOfYear, dayOfMonth)
                dateStr = inputSDF.format(today.time)
            }
        }

        ok.setOnClickListener{
            val intent = Intent()
            intent.putExtra("text", editT.text.toString())
            intent.putExtra("date", dateStr)
            if (args != null) {
                intent.putExtra("uid", args.getInt("id"))
            }
            setResult(2, intent)
            finish()
        }

        neok.setOnClickListener{
            finish()
        }

    }
}